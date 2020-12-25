/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.HashPassword;
import com.UserToken;
import java.sql.Connection;
import model.pojo.Client;
import model.pojo.User;
import model.dbHandler.DBBean;
import model.pojo.Employee;

/**
 *
 * @author Jamie
 */
public class EntryDao extends DAO {

    public EntryDao(Connection con) {
        super(con);
    }
    
    private DBBean db = this.getDb();
    

    public String[] cookieLogin(String token) {
        String[][] record = db.getRecords("SELECT uname, role FROM APP.USERS WHERE token='" + token + "' ");
        String uname = record[0][0];
        String role = record[0][1];
        String name = "";

        if (role.equals("client")) {
            name = db.getRecords("SELECT cname FROM APP.clients WHERE uname='" + uname + "'")[0][0];
        } else if (!role.equals("admin")) {
            name = db.getRecords("SELECT ename FROM APP.employees WHERE uname='" + uname + "'")[0][0];
        }

        return new String[]{role, name};
    }

    public String[] formLogin(User user, boolean isRemember) {
        String hasedPw = new HashPassword().hashPassword(user.getPassword());    // hash password
        String[][] record = db.getRecords(String.format("SELECT role, authorized FROM APP.USERS WHERE uname='%s' AND passwd='%s'", user.getUsername(), hasedPw));

        if (record.length == 0) {
            return new String[]{"Incorrect Username or Password"};
        }

        String authorized = record[0][1];
        String role = record[0][0];
        String name = "Admin";
        String token = null;

        // login with unauthorized credentials --> error 
        if (authorized.equals("false")) {
            return new String[]{ "Unauthorized User"};
        } 

        // login with authorized credentials --> login
        if (role.equals("client"))     // client
            name = db.getRecords("SELECT cname FROM APP.clients WHERE uname='" + user.getUsername() + "'")[0][0];
        else if (!role.equals("admin"))  // employee
            name = db.getRecords("SELECT ename FROM APP.employees WHERE uname='" + user.getUsername() + "'")[0][0];
        
        // if "Remember Me" box is checked
        if (isRemember) 
            token = db.getRecords("SELECT token FROM APP.users WHERE uname='" + user.getUsername() + "'")[0][0];
        
        return new String[] {role, name, token};
    }

    // signup client
    public boolean signUpClient(Client client) {
        String token = new UserToken().generateToken();     // user token
        String hashedPW = new HashPassword().hashPassword(client.getPassword()); // hash password

        // insert new user to 'users' table
        boolean inserted = db.insertUser(new String[]{client.getUsername(), hashedPW, "client", "true", token});
        boolean insertRole = false;

        // insert user info to 'clients' table
        if (inserted) {
            insertRole = db.insertClient(new String[]{client.getUsername(), client.getFullName(),
                client.getAddress(), client.getType()});
        }

        return insertRole;
    }
    
    // signup staff
    public boolean signUpStaff(Employee employee) {
        String token = new UserToken().generateToken();     // user token
        String hashedPW = new HashPassword().hashPassword(employee.getPassword()); // hash password

        // insert new user to 'users' table
        boolean inserted = db.insertUser(new String[]{employee.getUsername(), hashedPW, 
                                        employee.getRole().toLowerCase(), "false", token});
        boolean insertRole = false;

        // insert user info to 'employees' table
        if (inserted) {
            insertRole = db.insertEmployee(new String[]{employee.getUsername(), employee.getFullName(),
                                        employee.getAddress(), String.valueOf(employee.getRate())});
        }

        return insertRole;
    }

    public boolean unameTaken(String uname) {
        String findUsername = "SELECT uname FROM APP.USERS WHERE uname='" + uname + "'";
        String[][] res = db.getRecords(findUsername);
        return (res.length != 0);        // true if taken, false otherwise
    }

}