/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener.common;

/**
 *
 * @author zZMerciZz
 */
public class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
 
    public void login(String username, String password){
        
    }
    
    public void logout(){
    
    }
    
    public void signup(String username, String fullName, String password, String role, String address, String authorized) {
        String insertUSer = String.format("INSERT INTO USERS (UNAME, PASSWD, ROLE, AUTHORIZED) VALUES ('%s', '%s', '%s', '%s')",
                                        username, password, role, authorized);
        
    }
   
}
