/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import model.dbHandler.DBBean;

/**
 *
 * @author WIN 10
 */
public class UserDao extends DAO {
    public UserDao(Connection con) {
        super(con);
    }

    private DBBean db = this.getDb();
    
    public boolean deleteUser(String uname) {
        String query = "DELETE FROM APP.USERS WHERE uname='" + uname + "'";
        return db.executeUpdate(query);
    }
}
