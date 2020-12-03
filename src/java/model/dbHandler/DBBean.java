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
        // [username, password, role]
        if (values.length != 3) 
            return false;
        String query = String.format("INSERT INTO app.users VALUES ('%s', '%s', '%s')",
                values[0], values[1], values[2]);
        return executeUpdate(query);
    }
    
    public boolean insertEmployee(String[] values) {
        // [username, employeeName, employeeAddress, rate]
        if (values.length != 4) 
            return false;
        
        String query = String.format("INSERT INTO app.employees(uname, ename, eaddress, erate) "
                + "VALUES ('%s', '%s', '%s', %s)", values[0], values[1], values[2], values[3]);
        return executeUpdate(query);
    }
    
    public boolean insertClient(String[] values) {
        // [username, clientName, clientAddress, clientType]
        if (values.length != 4) 
            return false;
        
        String query = String.format("INSERT INTO app.clients(uname, cname, caddress, ctype) "
                + "VALUES ('%s', '%s', '%s', '%s')", values[0], values[1], values[2], values[3]);
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

//    public boolean cancelSchedule(String eName, String ) {
//        
//    }
    
    private boolean executeUpdate(String query) {
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
