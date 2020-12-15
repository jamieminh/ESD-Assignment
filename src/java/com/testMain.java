/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;
import com.User;
import com.Person;

/**
 *
 * @author WIN 10
 */
public class testMain {
    public static void main(String args[]) {
        User user1 = new User("jamie", "nguyen", "doctor", true);
        user1.signup();
        
        Person p1 = new Person("bao", "bui", "client", true, "Bao Thien", "Phu Nhuan");
        p1.signup();
    }
    
}
