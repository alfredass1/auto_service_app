package com.auto.autoservice;

import java.io.Serializable;

public class Auto implements Serializable {
    private int id;
    private String MaintenanceType;
    private String city;
    private String makeModel;
    private String year;
    private String payment;

    public Auto(int id, String MaintenanceType, String city, String makeModel, String year, String payment) {
        this.id = id;
        this.MaintenanceType = MaintenanceType;
        this.city = city;
        this.makeModel = makeModel;
        this.year = year;
        this.payment = payment;
    }

    public Auto(String MaintenanceType, String city, String makeModel,String year, String payment) {
        this.MaintenanceType = MaintenanceType;
        this.city = city;
        this.makeModel = makeModel;
        this.year = year;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public String getMaintenanceType() {
        return MaintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.MaintenanceType = maintenanceType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}















