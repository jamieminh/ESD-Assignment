/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
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

        for (Employee emp : staffs) {
            // [uname, address, rate, authorized]
            String[] mainInfo = new String[]{emp.getUsername(), emp.getAddress(),
                String.valueOf(emp.getRate()), String.valueOf(emp.isAuthorized())};
            changes.add(mainInfo);
        }
        return changes;
    }
    
    //get data of 1 employee using fullName
    public Employee getEmployeeData(String fullName) {
        String query = "SELECT * FROM APP.EMPLOYEES WHERE ename='" + fullName + "'";
        String[] data = db.getRecords(query)[0];

        Employee employee = new Employee();


        employee.setId(Integer.parseInt(data[0]));
        employee.setFullName(fullName);
        employee.setDob(data[2]);
        employee.setAddress(data[3]);
        if(data[4]==null){
            employee.setRate(0);
        }
        else{
            employee.setRate(Float.parseFloat(data[4]));
        }
        employee.setUsername(data[5]);

        return employee;
    }

    public Employee getEmpNameById(int id) {
        String query = "SELECT ename FROM APP.EMPLOYEES WHERE eid=" + id + "";
        String[] data = db.getRecords(query)[0];

        Employee emp = new Employee();
        emp.setFullName(data[0]);

        return emp;
    }

    public boolean updateAddrRateAuth(String uname, String address, String rate, String auth) {
        String addrQuery = String.format("UPDATE APP.EMPLOYEES SET eaddress='%s' WHERE uname='%s'", address, uname);
        String authQuery = String.format("UPDATE APP.USERS SET authorized='%s' WHERE uname='%s'", auth, uname);
        String rateQuery = String.format("UPDATE APP.EMPLOYEES SET erate=%s WHERE uname='%s'", rate, uname);
        boolean res = db.executeUpdate(addrQuery);
        boolean res2 = db.executeUpdate(authQuery);
        boolean res3 = db.executeUpdate(rateQuery);

        return (res && res2 && res3);

    }

}
