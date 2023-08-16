package com.example.beauticia;

public class Service {
    String service_id,service_name,service_fee,saloon_id;


    public Service(String service_id, String service_name, String service_fee, String saloon_id) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_fee = service_fee;
        this.saloon_id = saloon_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public Service(String service_name, String service_fee, String saloon_id) {
        this.service_name = service_name;
        this.service_fee = service_fee;
        this.saloon_id = saloon_id;
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

    public String getSaloon_id() {
        return saloon_id;
    }

    public void setSaloon_id(String saloon_id) {
        this.saloon_id = saloon_id;
    }
}
