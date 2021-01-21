/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
//        String[][] result = db.getAllRecords("schedule");
        String query = "SELECT * FROM APP.SCHEDULE WHERE cid IS NOT NULL";
        String[][] result = db.getRecords(query);
        ArrayList<Operation> schedule = new ArrayList<Operation>();

        EmployeeDao employeeDao = new EmployeeDao(con);
        ClientDAO clientDao = new ClientDAO(con);

        for (String[] res : result) {
            Operation op = new Operation();
            Employee emp = employeeDao.getEmpById(Integer.parseInt(res[1]));

            Client client = clientDao.getClientById(Integer.parseInt(res[2]));

            op.setId(Integer.parseInt(res[0]));
            op.setEmployee(emp);
            op.setClient(client);
            op.setType(res[3]);
            op.setnSlot(Integer.parseInt(res[4]));
            op.setDate(res[5]);
            op.setTime(res[6]);
            op.setIsCancelled(Boolean.parseBoolean(res[7]));
            op.setDescription(res[8]);

            schedule.add(op);
        }

        return schedule;
    }

    // add new schedule
    public boolean addAppointment(Operation operation) {
        boolean res = false;

        // insert schedule info to 'schedule' table
        String query = String.format("INSERT INTO app.schedule(EID, CID, STYPE, NSLOT, SDATE, STIME, CANCELLED, DESCRIPTION) "
                + "VALUES (%s, %s, '%s', %s, '%s', '%s', '%s', '%s')", operation.getEmployee().getId(), operation.getClient().getId(),
                operation.getType(), operation.getnSlot(), operation.getDate(), operation.getTime(), operation.isIsCancelled(), operation.getDescription());
        res = db.executeUpdate(query);
        if (res) {
            double charge = operation.getnSlot() * operation.getEmployee().getRate();

            String query2 = String.format("SELECT sid FROM APP.SCHEDULE WHERE"
                    + " eid=%s AND cid=%s AND sdate='%s' AND stime='%s'",
                    operation.getEmployee().getId(), operation.getClient().getId(), operation.getDate(), operation.getTime());
            String sid = db.getRecords(query2)[0][0];

            BillingDao billingDao = new BillingDao(con);
            res = billingDao.insertBilling(Integer.parseInt(sid), (float) Math.round((charge * 100.0) / 100.0));
        }
        return res;
    }

    public boolean addIssuePrescription(int empId, int patId, String IP, String date, String time) {
        String query = String.format("INSERT INTO APP.SCHEDULE (eid, cid, stype, nslot, sdate, stime, cancelled) "
                + " VALUES(%s, %s,'%s', 0,'%s', '%s', false)", empId, patId, IP, date, time);

        boolean res = db.executeUpdate(query);

        if (res) {
            double charge = 1000 + Math.random() * (5000 - 1000);

            String query2 = String.format("SELECT sid FROM APP.SCHEDULE WHERE"
                    + " eid=%s AND cid=%s AND stype = '%s' AND sdate='%s' AND stime='%s'", empId, patId, IP, date, time);
            String sid = db.getRecords(query2)[0][0];
            BillingDao billingDao = new BillingDao(con);
            res = billingDao.insertBilling(Integer.parseInt(sid), (float) Math.round(charge * 100.0 / 100.0));
        }
        return res;
    }

    public boolean addForwardPatient(int empId, int patId, String date, String time) {
        String query = String.format("INSERT INTO APP.SCHEDULE (eid, cid, stype, nslot, sdate, stime, cancelled) "
                + " VALUES(%s, %s, 'surgery', 0, '%s', '%s', false)", empId, patId, date, time);

        boolean res = db.executeUpdate(query);

        if (res) {
            double charge = 1000 + Math.random() * (5000 - 1000);

            String query2 = String.format("SELECT sid FROM APP.SCHEDULE WHERE"
                    + " eid=%s AND cid=%s AND sdate='%s' AND stime='%s'", empId, patId, date, time);
            String sid = db.getRecords(query2)[0][0];

            BillingDao billingDao = new BillingDao(con);
            res = billingDao.insertBilling(Integer.parseInt(sid), (float) Math.round(charge * 100.0 / 100.0));
        }

        return res;

    }

    // get operations that have passed and not cancelled
    public ArrayList<Operation> getAllSchedulePassedNotCancelled() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String today = simpleDateFormat.format(new Date());  // today

        String query = "SELECT * FROM APP.SCHEDULE WHERE sdate<='" + today + "' AND cancelled=false";
        String[][] results = db.getRecords(query);

        ArrayList<Operation> schedule = new ArrayList<Operation>();

        EmployeeDao employeeDao = new EmployeeDao(con);
        ClientDAO clientDao = new ClientDAO(con);

        for (String[] res : results) {
            Operation op = new Operation();
            Employee emp = employeeDao.getEmpById(Integer.parseInt(res[1]));
            Client client;
            if (res[2] != null) {   // if client not deleted
                client = clientDao.getClientById(Integer.parseInt(res[2]));
            } else {  // if client deleted
                client = new Client();
                client.setFullName("N/A");
                client.setId(0);
            }

            op.setId(Integer.parseInt(res[0]));
            op.setEmployee(emp);
            op.setClient(client);
            op.setType(res[3]);
            op.setnSlot(Integer.parseInt(res[4]));
            op.setDate(res[5]);
            op.setTime(res[6]);
            op.setIsCancelled(Boolean.parseBoolean(res[7]));
            op.setDescription(res[8]);

            schedule.add(op);
        }

        return schedule;
    }

    public ArrayList<Operation> getAllScheduleFromTo(String datefrom, String dateto, String timefrom, int cid) {

        String query = "SELECT * FROM APP.SCHEDULE WHERE SDATE >='" + datefrom + "' AND SDATE <='" + dateto + "' AND cid IS NOT NULL";
        if (!timefrom.equals("") && cid != 0) {
            query = "SELECT * FROM APP.SCHEDULE WHERE SDATE >='" + datefrom
                    + "' AND SDATE <='" + dateto + "' AND STIME >= '" + timefrom + "' AND cid=" + cid;
        }
        String[][] result = db.getRecords(query);
        ArrayList<Operation> schedule = new ArrayList<Operation>();

        EmployeeDao employeeDao = new EmployeeDao(con);
        ClientDAO clientDao = new ClientDAO(con);

        for (String[] res : result) {
            Operation op = new Operation();
            Employee emp = employeeDao.getEmpById(Integer.parseInt(res[1]));
            Client client = clientDao.getClientById(Integer.parseInt(res[2]));

            op.setId(Integer.parseInt(res[0]));
            op.setEmployee(emp);
            op.setClient(client);
            op.setType(res[3]);
            op.setnSlot(Integer.parseInt(res[4]));
            op.setDate(res[5]);
            op.setTime(res[6]);
            op.setIsCancelled(Boolean.parseBoolean(res[7]));
            op.setDescription(res[8]);

            schedule.add(op);
        }

        return schedule;

    }

    public ArrayList<Operation> getScheduleByEmpId(int empId) {
        ArrayList<Operation> schedule = new ArrayList<Operation>();

        String query = "SELECT * FROM APP.SCHEDULE WHERE eid=" + empId + " AND cid IS NOT NULL ";
        String[][] result = db.getRecords(query);

        EmployeeDao employeeDao = new EmployeeDao(con);
        ClientDAO clientDao = new ClientDAO(con);

        for (String[] res : result) {
            Operation op = new Operation();
            Employee emp = employeeDao.getEmpById(Integer.parseInt(res[1]));
            Client client = clientDao.getClientById(Integer.parseInt(res[2]));

            op.setId(Integer.parseInt(res[0]));
            op.setEmployee(emp);
            op.setClient(client);
            op.setType(res[3]);
            op.setnSlot(Integer.parseInt(res[4]));
            op.setDate(res[5]);
            op.setTime(res[6]);
            op.setIsCancelled(Boolean.parseBoolean(res[7]));
            op.setDescription(res[8]);

            schedule.add(op);
        }

        return schedule;
    }

    public Operation getScheduleById(int scheId) {
        String query = "SELECT * FROM APP.SCHEDULE WHERE sid=" + scheId;
        String[] res = db.getRecords(query)[0];

        EmployeeDao employeeDao = new EmployeeDao(con);
        ClientDAO clientDao = new ClientDAO(con);

        Operation op = new Operation();
        Employee emp = employeeDao.getEmpById(Integer.parseInt(res[1]));
        Client client = clientDao.getClientById(Integer.parseInt(res[2]));

        op.setId(Integer.parseInt(res[0]));
        op.setEmployee(emp);    // all emp data is avaiable
        op.setClient(client);   // all client data is avaiable
        op.setType(res[3]);
        op.setnSlot(Integer.parseInt(res[4]));
        op.setDate(res[5]);
        op.setTime(res[6]);
        op.setIsCancelled(Boolean.parseBoolean(res[7]));
        op.setDescription(res[8]);

        return op;
    }

    public ArrayList<Operation> getScheduleByCliId(int cliId) {
        ArrayList<Operation> schedule = new ArrayList<Operation>();

        String query = "SELECT * FROM APP.SCHEDULE WHERE cid=" + cliId + " AND cid IS NOT NULL";
        String[][] result = db.getRecords(query);

        EmployeeDao employeeDao = new EmployeeDao(con);

        for (String[] res : result) {
            Operation op = new Operation();
            Employee emp = employeeDao.getEmpById(Integer.parseInt(res[1]));

            op.setId(Integer.parseInt(res[0]));
            op.setEmployee(emp);
            op.setType(res[3]);
            op.setnSlot(Integer.parseInt(res[4]));
            op.setDate(res[5]);
            op.setTime(res[6]);
            op.setIsCancelled(Boolean.parseBoolean(res[7]));
            op.setDescription(res[8]);

            schedule.add(op);
        }

        return schedule;
    }

    public boolean addSurgery(int empId, int patId, String date, String time, String description) {
        String query = String.format("INSERT INTO APP.SCHEDULE (eid, cid, stype, nslot, sdate, stime, cancelled, description) "
                + " VALUES(%s, %s, 'surgery', 0, '%s', '%s', false, '%s')", empId, patId, date, time, description);

        boolean res = db.executeUpdate(query);

        if (res) {
            double charge = 1000 + Math.random() * (5000 - 1000);

            String query2 = String.format("SELECT sid FROM APP.SCHEDULE WHERE"
                    + " eid=%s AND cid=%s AND sdate='%s' AND stime='%s'", empId, patId, date, time);
            String sid = db.getRecords(query2)[0][0];

            BillingDao billingDao = new BillingDao(con);
            res = billingDao.insertBilling(Integer.parseInt(sid), (float) Math.round(charge * 100.0 / 100.0));
        }

        return res;

    }

    public boolean cancelSchedule(String sid) {
        String query = "UPDATE APP.SCHEDULE SET cancelled='true' WHERE sid=" + sid;
        return db.executeUpdate(query);
    }

}
