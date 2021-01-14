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

    public DAO(Connection con) {
        db = new DBBean();
        db.connect(con);
    }

    public DBBean getDb() {
        return db;
    }
    
    
}
