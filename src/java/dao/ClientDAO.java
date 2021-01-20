/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.pojo.Client;
import model.dbHandler.DBBean;
import java.sql.Connection;
import java.util.ArrayList;


/**
 *
 * @author Jamie
 */
public class ClientDAO extends DAO {

    public ClientDAO(Connection con) {
        super(con);
    }

    private DBBean db = this.getDb();
    
     public ArrayList<Client> getAllEmployees() {
        String[][] records = db.getRecords("SELECT * FROM APP.CLIENTS");
        ArrayList<Client> clients = new ArrayList<Client>();

        for (String[] rec : records) {  //         
            // [cid, cname, address, type, uname]
            Client client = new Client();
            client.setId(Integer.parseInt(rec[0]));
            client.setUsername(rec[4]);
            client.setFullName(rec[1]);
            client.setAddress(rec[2]);
            client.setType(rec[3]);
            clients.add(client);
        }
        return clients;
    }

    public Client getClientData(String fullName) {
        String query = "SELECT * FROM APP.CLIENTS WHERE cname='" + fullName + "'";
        String[] data = db.getRecords(query)[0];

        Client client = new Client();
        
        if (data[2] == null)
            data[2] = "null";

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
