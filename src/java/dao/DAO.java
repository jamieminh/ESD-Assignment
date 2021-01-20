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
 * @author Jamie
 */
public class DAO {
    private DBBean db;
    private Connection con;

    public DAO(Connection con) {
        db = new DBBean();
        this.con = con;
        db.connect(con);
    }

    public DBBean getDb() {
        return db;
    }
    
    public Connection getCon() {
        return con;
    }
    
    
}
