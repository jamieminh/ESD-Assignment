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
    
    public ArrayList<Client> getAllClients() {
        ArrayList<Client> clients = new ArrayList<Client>();
        String[][] results = db.getAllRecords("clients");
        
        for (String[] res : results) {
            Client client = new Client();

            client.setId(Integer.parseInt(res[0]));
            client.setFullName(res[1]);
            
            clients.add(client);
        }

        return clients;
    }

}
