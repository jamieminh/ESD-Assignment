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
        String eid = "";

        if (role.equals("client")) {
            name = db.getRecords("SELECT cname FROM APP.clients WHERE uname='" + uname + "'")[0][0];
        } else if (!role.equals("admin")) {
            String[] res = db.getRecords("SELECT ename, eid FROM APP.employees WHERE uname='" + uname + "'")[0];
            name = res[0];
            eid = res[1];
        }

        return new String[]{role, name, eid};
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
        String eid = "";

        // login with unauthorized credentials --> error 
        if (authorized.equals("false")) {
            return new String[]{ "Unauthorized User"};
        } 

        // login with authorized credentials --> login
        if (role.equals("client"))     // client
            name = db.getRecords("SELECT cname FROM APP.clients WHERE uname='" + user.getUsername() + "'")[0][0];
        else if (!role.equals("admin")) {  // employee 
            String[] res = db.getRecords("SELECT ename, eid FROM APP.employees WHERE uname='" + user.getUsername() + "'")[0];
            name = res[0];
            eid = res[1];
        }
        
        // if "Remember Me" box is checked
        if (isRemember) 
            token = db.getRecords("SELECT token FROM APP.users WHERE uname='" + user.getUsername() + "'")[0][0];
        
        return new String[] {role, name, token, eid};
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
            String query = String.format("INSERT INTO app.clients(uname, cname, cdob, caddress, ctype) VALUES ('%s', '%s', '%s', '%s', '%s')",
                    client.getUsername(), client.getFullName(), client.getDob(), client.getAddress(), client.getType());
            System.out.println("query=" + query);
            insertRole = db.executeUpdate(query);
        }

        // delete user from db if user cannot be inserted into 'clients' table
        if (!insertRole) 
            db.deleteUser(client.getUsername());

        return insertRole;
    }
    
    // signup staff
    public boolean signUpStaff(Employee employee) {
        String token = new UserToken().generateToken();     // user token
        String hashedPW = new HashPassword().hashPassword(employee.getPassword()); // hash password

        // insert new user to 'users' table
        boolean inserted = db.insertUser(new String[]{employee.getUsername(), 
            hashedPW, employee.getRole().toLowerCase(), "false", token});        
        
        boolean insertRole = false;

        // insert user info to 'employees' table
        if (inserted) {
            String query = String.format("INSERT INTO app.employees(uname, ename, edob, eaddress) VALUES ('%s', '%s', '%s', '%s')",
                    employee.getUsername(), employee.getFullName(), employee.getDob(), employee.getAddress());
            insertRole = db.executeUpdate(query);
        }
        
        // delete user from db if user cannot be inserted into 'employees' table
        if (!insertRole) 
            db.deleteUser(employee.getUsername());

        return insertRole;
    }

    public boolean unameTaken(String uname) {
        String findUsername = "SELECT uname FROM APP.USERS WHERE uname='" + uname + "'";
        String[][] res = db.getRecords(findUsername);
        return (res.length != 0);        // true if taken, false otherwise
    }

}
