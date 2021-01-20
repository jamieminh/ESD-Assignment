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
    
    public boolean insertBilling(int sId, float charge) {
        String query = String.format("INSERT INTO APP.BILLING (sid, charge) VALUES(%s, %s)", sId, charge);
        return db.executeUpdate(query);
    }
    
}
