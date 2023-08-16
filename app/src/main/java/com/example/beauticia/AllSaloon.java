package com.example.beauticia;

public class AllSaloon {
    String user_id,user_name,user_email,user_address;

    public AllSaloon(String user_id, String user_name, String user_email, String user_address) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_address = user_address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }
}
