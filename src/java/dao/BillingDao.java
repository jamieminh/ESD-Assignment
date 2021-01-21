/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import model.dbHandler.DBBean;
import model.pojo.Billing;
import model.pojo.Operation;

/**
 *
 * @author WIN 10
 */
public class BillingDao extends DAO {

    public BillingDao(Connection con) {
        super(con);
    }

    private DBBean db = this.getDb();

    public ArrayList<Billing> getAllBillings() {
        ArrayList<Billing> billings = new ArrayList<Billing>();
        String[][] data = db.getAllRecords("billing");

        for (String[] row : data) {
            Billing billing = new Billing();
            Operation op = new Operation();

            op.setId(Integer.parseInt(row[1]));

            billing.setbId(Integer.parseInt(row[0]));
            billing.setOp(op);
            billing.setCharge(row[2].equals("null") ? 0 : Float.parseFloat(row[2]));
            billing.setIsPaid(Boolean.parseBoolean(row[3]));
        }

        return billings;
    }

    public Billing getBillingBySID(int SID) {
        String query = "SELECT * FROM APP.BILLING WHERE SID=" + SID + "";
        String[] data = db.getRecords(query)[0];
        Billing billing = new Billing();

        Operation op = new Operation();

        op.setId(SID);

        billing.setbId(Integer.parseInt(data[0]));
        billing.setOp(op);
        billing.setCharge(Float.parseFloat(data[2]));
        billing.setIsPaid(Boolean.parseBoolean(data[3]));
        return billing;
    }

    public Billing getPaidBillingBySID(int SID) {
        String query = "SELECT * FROM APP.BILLING WHERE SID=" + SID + " AND paid=true";
        Billing billing = new Billing();

        String[][] res = db.getRecords(query);
        if (res.length == 0) {  // not paid
            billing.setbId(0);
            System.out.println("[SID] = " + SID + " - NO PAYMENT");
            return billing;
        }
        System.out.println("[SID] = " + SID + " - HAS PAYMENT");

        Operation op = new Operation();
        op.setId(SID);

        String[] data = res[0];

        billing.setbId(Integer.parseInt(data[0]));
        billing.setOp(op);
        billing.setCharge(Float.parseFloat(data[2]));
        billing.setIsPaid(Boolean.parseBoolean(data[3]));

        return billing;
    }

    public ArrayList<Billing> getAllBillingByCliID(int cliId) {
        ArrayList<Billing> clientUnpaidBills = new ArrayList<Billing>();

        // get operation related to this client
        String query = "Select sid from app.schedule where cid=" + cliId;
        String[][] data = db.getRecords(query);
        for (int i = 0; i < data.length; i++) {
            int sId = Integer.parseInt(data[i][0]);
            query = "SELECT * FROM APP.BILLING WHERE sID=" + sId + "";
            System.out.println("SID=" + sId);
            
            String[][] bills = db.getRecords(query);
            if (bills.length == 0) 
                continue;            
            
            String[] aBill = bills[0];

            Billing billing = new Billing();
            Operation op = new Operation();

            op.setId(sId);
            billing.setbId(Integer.parseInt(aBill[0]));
            billing.setOp(op);
            billing.setCharge(Float.parseFloat(aBill[2]));
            billing.setIsPaid(Boolean.parseBoolean(aBill[3]));

            clientUnpaidBills.add(billing);
        }

        return clientUnpaidBills;
    }

    public boolean payBillBybId(int bId) {
        String query = "UPDATE app.billing SET paid=true WHERE bid=" + bId;
        return db.executeUpdate(query);
    }

    public boolean insertBilling(int sId, float charge) {
        String query = String.format("INSERT INTO APP.BILLING (sid, charge) VALUES(%s, %s)", sId, charge);
        return db.executeUpdate(query);
    }

    public boolean removeBilling(int sId) {
        String query = "DELETE FROM APP.BILLING WHERE sid=" + sId;
        return db.executeUpdate(query);
    }

}
