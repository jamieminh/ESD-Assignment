/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import model.pojo.Employee;
import model.dbHandler.DBBean;

/**
 *
 * @author Jamie
 */
public class EmployeeDao extends DAO {

    public EmployeeDao(Connection con) {
        super(con);
    }
    
    private DBBean db = this.getDb();
    
    public ArrayList<Employee> getAllEmployees() {
        String[][] records = db.getRecords("SELECT uname, role, authorized FROM APP.USERS WHERE role='doctor' OR role='nurse'");
        ArrayList<Employee> staffs = new ArrayList<Employee>();

        for (String[] rec : records) {  // a staff's ename, role, authorized            
            String getEmployee = "SELECT eid, ename, eaddress, erate FROM APP.EMPLOYEES WHERE uname='" + rec[0] + "'";
            String[] empInfo = db.getRecords(getEmployee)[0];
            // [eid, uname, full name, address, role, rate, authorized]
            Employee emp = new Employee();
            emp.setId(Integer.valueOf(empInfo[0]));
            emp.setUsername(rec[0]);
            emp.setFullName(empInfo[1]);
            emp.setAddress(empInfo[2]);
            emp.setRate(Float.valueOf(empInfo[3] == null ? "0" : empInfo[3]));
            emp.setRole(rec[1]);
            emp.setAuthorized(Boolean.parseBoolean(rec[2]));
            staffs.add(emp);
        }
        return staffs;
    }
    
    // get username and attribute that can be changed (rate, authorized)
    public ArrayList<String[]> getFormChanges(ArrayList<Employee> staffs) {
        ArrayList<String[]> changes = new ArrayList<String[]>();
        
        for(Employee emp : staffs) {
            // [uname, rate, authorized]
            String[] mainInfo = new String[] {emp.getUsername(), 
                String.valueOf(emp.getRate()), String.valueOf(emp.isAuthorized())};
            changes.add(mainInfo);
        }
        return changes;
    }
    
    public boolean updateRateAuth(String uname, String rate, String auth) {
        String authQuery = String.format("UPDATE APP.USERS SET authorized='%s' WHERE uname='%s'", auth, uname);
        String rateQuery = String.format("UPDATE APP.EMPLOYEES SET erate=%s WHERE uname='%s'", rate, uname);
        boolean res = db.executeUpdate(authQuery);
        if (res)
            res = db.executeUpdate(rateQuery);
        
        return res;
        
    }
    
    
}
