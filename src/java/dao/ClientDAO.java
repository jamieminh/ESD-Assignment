/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.pojo.Client;
import model.dbHandler.DBBean;
import java.sql.Connection;

/**
 *
 * @author Jamie
 */
public class ClientDAO extends DAO {

    public ClientDAO(Connection con) {
        super(con);
    }

    private DBBean db = this.getDb();

    public Client getClientData(String fullName) {
        String query = "SELECT * FROM APP.CLIENTS WHERE cname='" + fullName + "'";
        String[] data = db.getRecords(query)[0];

        Client client = new Client();


        client.setId(Integer.parseInt(data[0]));
        client.setDob(data[2]);
        client.setAddress(data[3]);
        client.setType(data[4]);
        client.setUsername(data[5]);

        return client;
    }
    
    public Client getClientNameById(int id) {
        String query = "SELECT cname FROM APP.CLIENTS WHERE cid=" + id + "";
        String[] data = db.getRecords(query)[0];

        Client client = new Client();

        client.setFullName(data[0]);

        return client;
    }

    public boolean updateClient(String username, String fullname, String dob, String type, String address) {
        String query = String.format("UPDATE APP.CLIENTS SET cname='%s', cdob='%s', ctype='%s', caddress='%s' WHERE uname='%s'", fullname, dob, type, address, username);
        boolean res = db.executeUpdate(query);
        
        return res;
    }

}
