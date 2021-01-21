/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import model.dbHandler.DBBean;
import model.pojo.Client;
import model.pojo.Employee;
import model.pojo.Prescription;

/**
 *
 * @author zZMerciZz
 */
public class PrescriptionDao extends DAO{
    public PrescriptionDao(Connection con) {
        super(con);
    }

    private DBBean db = this.getDb();
    private Connection con = this.getCon();
    
    
    public ArrayList<Prescription> getAllPrescription() {
        String query = "SELECT * FROM APP.PRESCRIPTION WHERE pid IS NOT NULL";
        String[][] result = db.getRecords(query);
        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();

        EmployeeDao employeeDao = new EmployeeDao(con);
        ClientDAO clientDao = new ClientDAO(con);

        for (String[] res : result) {
            Prescription pres = new Prescription();
            Employee emp = employeeDao.getEmpById(Integer.parseInt(res[1]));

            Client client = clientDao.getClientById(Integer.parseInt(res[2]));

            pres.setId(Integer.parseInt(res[0]));
            pres.setEmp(emp);
            pres.setClient(client);
            pres.setDate(res[3]);
            if(res[4]==null)
                pres.setnUse(0);
            else
            pres.setnUse(Integer.parseInt(res[4]));
            pres.setDescription(res[5]);

            prescriptions.add(pres);
        }

        return prescriptions;
    }
    
    public ArrayList<Prescription> getPrescriptionByCliId(int cliId) {
        String query = "SELECT * FROM APP.PRESCRIPTION WHERE cid="+ cliId +" AND cid IS NOT NULL";
        String[][] result = db.getRecords(query);
        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();

        EmployeeDao employeeDao = new EmployeeDao(con);
        ClientDAO client = new ClientDAO(con);

        for (String[] res : result) {
            Prescription pres = new Prescription();
            Employee emp = employeeDao.getEmpById(Integer.parseInt(res[1]));

            pres.setId(Integer.parseInt(res[0]));
            pres.setEmp(emp);
            pres.setClient(client.getClientById(cliId));
            pres.setDate(res[3]);
            if(res[4]==null)
                pres.setnUse(0);
            else
            pres.setnUse(Integer.parseInt(res[4]));
            pres.setDescription(res[5]);

            prescriptions.add(pres);
        }

        return prescriptions;
    }

}
