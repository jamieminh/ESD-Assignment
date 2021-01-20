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
        return billing;
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
