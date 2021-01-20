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
import model.pojo.Operation;

/**
 *
 * @author WIN 10
 */
public class OperationDao extends DAO {

    public OperationDao(Connection con) {
        super(con);
    }

    private DBBean db = this.getDb();
    private Connection con = this.getCon();

    public ArrayList<Operation> getAllSchedule() {
        String[][] result = db.getAllRecords("schedule");
        ArrayList<Operation> schedule = new ArrayList<Operation>();

        EmployeeDao employeeDao = new EmployeeDao(con);
        ClientDAO clientDao = new ClientDAO(con);

        for (String[] res : result) {
            Operation op = new Operation();
            Employee emp = employeeDao.getEmpNameById(Integer.parseInt(res[1]));
            Client client = clientDao.getClientNameById(Integer.parseInt(res[2]));

            op.setId(Integer.parseInt(res[0]));
            op.setEmployee(emp);
            op.setClient(client);
            op.setType(res[3]);
            op.setnSlot(Integer.parseInt(res[4]));
            op.setDate(res[5]);
            op.setTime(res[6]);
            op.setIsCancelled(Boolean.parseBoolean(res[7]));

            schedule.add(op);
        }

        return schedule;

    }
    
    // add new schedule
    public boolean addSchedule(Operation operation) {
        boolean insertSchedule = false;

        // insert schedule info to 'schedule' table
            String query = String.format("INSERT INTO app.schedule(EID, CID, STYPE, NSLOT, SDATE, STIME, CANCELLED) VALUES (%s, %s, '%s', %s, '%s', '%s', '%s')",
                    operation.getEmployee().getId(), operation.getClient().getId(), operation.getType(), operation.getnSlot(), operation.getDate(), operation.getTime(), operation.isIsCancelled());
            insertSchedule = db.executeUpdate(query);

        return insertSchedule;
    }

    public ArrayList<Operation> getScheduleByEmpId(int empId) {
        ArrayList<Operation> schedule = new ArrayList<Operation>();
        
        String query = "SELECT * FROM APP.SCHEDULE WHERE eid=" + empId;
        String[][] result = db.getRecords(query);

        EmployeeDao employeeDao = new EmployeeDao(con);
        ClientDAO clientDao = new ClientDAO(con);

        for (String[] res : result) {
            Operation op = new Operation();
            Employee emp = employeeDao.getEmpNameById(Integer.parseInt(res[1]));
            Client client = clientDao.getClientNameById(Integer.parseInt(res[2]));

            op.setId(Integer.parseInt(res[0]));
            op.setEmployee(emp);
            op.setClient(client);
            op.setType(res[3]);
            op.setnSlot(Integer.parseInt(res[4]));
            op.setDate(res[5]);
            op.setTime(res[6]);
            op.setIsCancelled(Boolean.parseBoolean(res[7]));

            schedule.add(op);
        }

        return schedule;
    }
    
    public boolean cancelSchedule(String sid ) {
        String query = "UPDATE APP.SCHEDULE SET cancelled='true' WHERE sid=" + sid;
        return db.executeUpdate(query);
    }

}
