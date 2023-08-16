package com.example.beauticia;

public class OrderHistory {
    String booking_id,service_name,service_fee,booking_date;

    public OrderHistory(String booking_id, String service_name, String service_fee, String booking_date) {
        this.booking_id = booking_id;
        this.service_name = service_name;
        this.service_fee = service_fee;
        this.booking_date = booking_date;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
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

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }
}
