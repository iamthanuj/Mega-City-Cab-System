/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Thanuja Fernando
 */
public class User {
    private int id;
    private String name;
    private String nic;
    private String email;
    private int phone;
    private String password;
    
    
    
    public User(String name, String nic, String email, int phone, String Password){
       this.name = name;
       this.nic = nic;
       this.email = email;
       this.phone = phone;
       this.password = Password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    @Override
    public String toString(){
          return "User [id=" + id + ", name=" + name + ", nic=" + nic +", email=" + email +", phone=" + phone + ", password=" + password + "]";
    }
    
}
