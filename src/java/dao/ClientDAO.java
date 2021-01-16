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
        client.setAddress(data[2]);
        client.setType(data[3]);
        client.setUsername(data[4]);

        return client;
    }

    public boolean updateClient(String username, String fullname, String type, String address) {
        String query = String.format("UPDATE APP.CLIENTS SET cname='%s', ctype='%s', caddress='%s' WHERE uname='%s'", fullname, type, address, username);
        boolean res = db.executeUpdate(query);
        
        return res;
    }

}
