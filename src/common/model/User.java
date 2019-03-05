package common.model;

import java.io.Serializable;

/**
 * Created by Norm on 2/25/2019.
 */
public class User implements Serializable {

    private String username = "";
    private String password = "";
    private String name = "";
    private String dob = "";
    private String gender = "";
    private String address = "";
    private String nic = "";
    private String contact = "";

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String name, String gender, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.address = address;
    }

    public User(String username, String password, String name, String dob, String gender, String address, String nic, String contact) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.nic = nic;
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String toString() {
        return "[" + getUsername() + "," + getPassword() + "," + getName() + "," + getAddress() + "]";
    }

}
