package com;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.User;

/**
 *
 * @author zZMerciZz
 */
public class Person extends User {
//    private int id;
    private String name;
    private String address;
    
    public Person(String username, String password, String role, boolean authorized, 
            String name, String address) {
        super(username, password, role, authorized);
//        this.id = id;
        this.name = name;
        this.address = address;
    }
    
    @Override
    public void signup() {
        super.signup();
        System.out.println("[Person]");        
    }
    
}

//class Client extends Person {
//    private String type;
//
//    public Client(String type, int id, String name, String address) {
//        super(id, name, address);
//        this.type = type;
//    }
//    
//}
//
//class Employee extends Person   {
//    private float rate;
//
//    public Employee(float rate, int id, String name, String address) {
//        super(id, name, address);
//        this.rate = rate;
//    }
//    
//}
//
//class Doctor extends Employee   {
//
//    public Doctor(float rate, int id, String name, String address) {
//        super(rate, id, name, address);
//    }
//    
//}
//
//class Nurse extends Employee    {
//
//    public Nurse(float rate, int id, String name, String address) {
//        super(rate, id, name, address);
//    }
//    
//}
//
//class Admin extends Person  {
//
//    public Admin(int id, String name, String address) {
//        super(id, name, address);
//    }
//    
//    public void removeSurgery(){
//        
//    }
//    
//    public void addStaff(){
//        
//    }
//} 


