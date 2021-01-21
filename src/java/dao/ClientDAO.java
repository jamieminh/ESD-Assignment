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

    public ArrayList<Client> getAllClients() {
        String[][] records = db.getRecords("SELECT * FROM APP.CLIENTS");
        ArrayList<Client> clients = new ArrayList<Client>();

        for (String[] rec : records) {  //         
            // [cid, cname, dob, address, type, uname]
            Client client = new Client();
            client.setId(Integer.parseInt(rec[0]));
            client.setFullName(rec[1]);
            client.setDob(rec[2]);
            client.setAddress(rec[3]);
            client.setType(rec[4]);
            client.setUsername(rec[5]);
            clients.add(client);
        }
        return clients;
    }
    
    public ArrayList<Client> getAllPrivateClients() {
        String[][] records = db.getRecords("SELECT * FROM APP.CLIENTS WHERE ctype='private'");
        ArrayList<Client> clients = new ArrayList<Client>();

        for (String[] rec : records) {  //         
            // [cid, cname, dob, address, type, uname]
            Client client = new Client();
            client.setId(Integer.parseInt(rec[0]));
            client.setFullName(rec[1]);
            client.setDob(rec[2]);
            client.setAddress(rec[3]);
            client.setType(rec[4]);
            client.setUsername(rec[5]);
            clients.add(client);
        }
        return clients;
    }
    
    public ArrayList<Client> getAllNHSClients() {
        String[][] records = db.getRecords("SELECT * FROM APP.CLIENTS WHERE ctype='nhs'");
        ArrayList<Client> clients = new ArrayList<Client>();

        for (String[] rec : records) {  //         
            // [cid, cname, dob, address, type, uname]
            Client client = new Client();
            client.setId(Integer.parseInt(rec[0]));
            client.setFullName(rec[1]);
            client.setDob(rec[2]);
            client.setAddress(rec[3]);
            client.setType(rec[4]);
            client.setUsername(rec[5]);
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
    
    public Client getClientById(int id) {
        String query = "SELECT * FROM APP.CLIENTS WHERE cid=" + id + "";
        String[] data = db.getRecords(query)[0];

        Client client = new Client();

        client.setId(Integer.parseInt(data[0]));
        client.setFullName(data[1]);
        client.setDob(data[2]);
        client.setAddress(data[3]);
        client.setType(data[4]);
        client.setUsername(data[5]);

        return client;
    }

    public boolean updateClient(String username, String fullname, String dob, String type, String address) {
        String query = String.format("UPDATE APP.CLIENTS SET cname='%s', cdob='%s', ctype='%s', caddress='%s' WHERE uname='%s'", fullname, dob, type, address, username);
        boolean res = db.executeUpdate(query);
        
        return res;
    }
    
    public boolean deleteClient(int cliId) {
        String query = "DELETE FROM APP.CLIENTS WHERE cid=" + cliId;
        return db.executeUpdate(query);
    }
    
//    public ArrayList<Client> getAllClients() {
//        ArrayList<Client> clients = new ArrayList<Client>();
//        String[][] results = db.getAllRecords("clients");
//        
//        for (String[] res : results) {
//            Client client = new Client();
//
//            client.setId(Integer.parseInt(res[0]));
//            client.setFullName(res[1]);
//            
//            clients.add(client);
//        }
//
//        return clients;
//    }

}
