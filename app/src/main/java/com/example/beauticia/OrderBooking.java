package com.example.beauticia;

public class OrderBooking {
    String booking_id,booking_date,service_name,service_fee,user_name,user_phone;

    public OrderBooking(String booking_id, String booking_date, String service_name, String service_fee, String user_name, String user_phone) {
        this.booking_id = booking_id;
        this.booking_date = booking_date;
        this.service_name = service_name;
        this.service_fee = service_fee;
        this.user_name = user_name;
        this.user_phone = user_phone;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_fee() {
        return service_fee;
    }

    public void setService_fee(String service_fee) {
        this.service_fee = service_fee;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
}
