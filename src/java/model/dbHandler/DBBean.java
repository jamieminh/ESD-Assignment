package model.dbHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jamie
 */
public class DBBean {
    private Connection con;
    private Statement stm;
    private ResultSet rs;
    
    public void connect(Connection c) {
        con = c;
    }
    
    // get all records from a table
    public String[][] getAllRecords(String table) {
        return select(table, "");
    }
    
    // get records with conditions, the query is passed in
    public String[][] getRecords(String query) {
        return select("", query);
    }
    
    public boolean insertUser(String[] values) {
        // [username, password, role, authorized, token]
        String query = String.format("INSERT INTO app.users VALUES ('%s', '%s', '%s', '%s', '%s')",
                values[0], values[1], values[2], values[3], values[4]);
        return executeUpdate(query);
    }
    
    public boolean deleteUser(String uname) {
        String query = "DELETE FROM APP.USERS WHERE uname='" + uname + "'";
        return executeUpdate(query);
    }
    public boolean deleteClient(int id) {
        String query = "DELETE FROM APP.CLIENTS WHERE CID="+id+"";;
        return executeUpdate(query);
    }
    
    // html date and time pickers should be used
    public boolean insertSchedule(String[] values) {
        // date: yyyy-mm-dd is the default format of the HTML date picker
        // time: hh:mm is the default format of the HTML time picker
        // [employeeName, clientName, type, date, time, slot]
        // sql formats [year: yyyy, month: m OR mm, day: d OR dd, hour: h OR hh, minute: mm]
        if (values.length != 6) 
            return false;
        
        String[][] find = getRecords("SELECT eid FROM app.employees WHERE ename='" + values[0] + "'");
        String eid = find[0][0];
        find = getRecords("SELECT cid FROM app.clients WHERE cname='" + values[1] + "'");
        String cid = find[0][0];
        String slot = values[2].equals("surgery") ? "0" : values[5];
        
        String query = String.format("INSERT INTO app.schedule(eid, cid, stype, sdate, stime, nslot) "
                + "VALUES (%s, %s, '%s','%s', '%s', %s)", eid, cid, values[2], values[3], values[4], slot);
        return executeUpdate(query);
    }
    
    // html date and time pickers should be used
    public boolean insertPrescription(String[] values) {
        // date: yyyy-mm-dd is the default format of the HTML date picker
        // [employeeName, clientName, date]
        // sql formats [year: yyyy, month: m OR mm, day: d OR dd, hour: h OR hh, minute: mm]
        if (values.length != 6) 
            return false;
        
        String[][] find = getRecords("SELECT eid FROM app.employees WHERE ename='" + values[0] + "'");
        String eid = find[0][0];
        find = getRecords("SELECT cid FROM app.clients WHERE cname='" + values[1] + "'");
        String cid = find[0][0];
        
        String query = String.format("INSERT INTO app.prescription(eid, cid, pdate) "
                + "VALUES (%s, %s, '%s')", eid, cid, values[2]);
        return executeUpdate(query);
    }
    
    public boolean insertBilling(String[] values) {
        // [employeeName, date, time, charge]
        if (values.length != 4) 
            return false;
        
        String eid = getRecords("SELECT eid FROM app.employees WHERE ename='" + values[0] +"'")[0][0];
        String charge = values[3];
        String[][] schedule = getRecords(String.format("SELECT sid,stype,nslot FROM app.schedule "
                            + "WHERE eid=%s AND sdate='%s' AND stime='%s'", eid, values[1], values[2]));
        String type = schedule[0][1];
        
        if (type.equals("appointment")) {
            int nslot = Integer.parseInt(schedule[0][2]);   // get number of slot
            float rate = Float.parseFloat(getRecords("SELECT erate FROM app.employees WHERE eid=" + eid )[0][0]);
            charge = String.valueOf(nslot * rate);          // calculate the charge
        }
        
        String query = String.format("INSERT INTO app.billing(sid, charge) VALUES (%s, %s)", schedule[0][0], charge);
        return executeUpdate(query);
    }

    public boolean cancelSchedule(String sid ) {
        String query = "UPDATE APP.SCHEDULE SET cancelled='true' WHERE sid=" + sid;
        return executeUpdate(query);
    }
    
    public boolean aprovePrescription(String pid ) {
        String query = "UPDATE APP.Prscription SET aproved='true' WHERE sid=" + pid;
        return executeUpdate(query);
    }
    
    public boolean authorizeUser(String username) {
        String query = "UPDATE APP.USERS SET authorized='true' WHERE uname='" + username + "' ";
        return executeUpdate(query);
    }
    
    public boolean changePrice(String username, String rate) {
        String query = String.format("UPDATE APP.EMPLOYEES SET erate=%s WHERE uname='%s'", rate, username);
        return executeUpdate(query);
    }
    
    // THIS METHOD IS LIMITED TO STRING VALUES
    public boolean updateTableData(String table, String[] updateAtts, String[] updateVals,
            String[] whereAtts, String[] whereVals) {
        // updateAtts: the attributes to be update
        // updateVals: the values to be update
        // whereAtts: the attributes for the 'WHERE' condition
        // whereVals: the values for the 'WHERE' condition
        // 
        if (updateAtts.length != updateVals.length)
            return false;
        if (whereAtts.length != whereVals.length)
            return false;
        
        // the SET clause in the sql query
        String sets = "UPDATE APP." + table + " SET ";
        String where = "WHERE ";
        
        for (int i=0; i<updateAtts.length; i++) {
            if (i == updateAtts.length-1)
                sets += updateAtts[i] + "='" + updateVals[i] + "' ";
            else 
                sets += updateAtts[i] + "='" + updateVals[i] + "', ";            
        }
        
        for (int i=0; i<whereAtts.length; i++) {
            if (i == whereAtts.length-1)
                where += whereAtts[i] + "='" + whereVals[i] + "' ";
            else 
                where += whereAtts[i] + "='" + whereVals[i] + "', ";            
        }
        
        return executeUpdate(sets + where);        
    }
    
    public boolean executeUpdate(String query) {
        boolean isSuccess = false;
        try {
            stm = con.createStatement();
            int res = stm.executeUpdate(query);
            isSuccess = (res == 1);
            stm.close();
        } 
        catch (SQLException e) {
            System.err.println("Error: " + e);
        }

        return isSuccess;
    }
    
    private String[][] select(String table, String query) {
        ArrayList<String[]> res = new ArrayList<>();
        int cols = 0;
        try {
            stm = con.createStatement();
            // if the query arg is an empty string, get all records from the table
            if (query.equals(""))
                rs = stm.executeQuery("SELECT * from APP." + table);
            else // if the query is not empty string, execute the query
                rs = stm.executeQuery(query);
            
            cols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String[] rec = new String[cols];
                for (int i=1; i<=cols; i++) {
                    rec[i-1] = rs.getString(i);
                }
                res.add(rec);
            }
            rs.close();
            stm.close();
        } 
        catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        // convert arrayList to array
        String[][] result = new String[res.size()][];
        for (int i=0; i<res.size(); i++) 
            result[i] = res.get(i);
        
        return result;
    }
}
