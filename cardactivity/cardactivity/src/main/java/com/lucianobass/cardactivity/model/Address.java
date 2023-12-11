package com.lucianobass.cardactivity.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_address")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

    private Long idAddress;
    private int cep;
    private int numberHome;
    private String road;
    private String city;
    private String UF;
    private String neighborhood;

    public Address() {
    }

    public Address(int cep, int numberHome, String road, String city, String UF, String neighborhood) {
        this.cep = cep;
        this.numberHome = numberHome;
        this.road = road;
        this.city = city;
        this.UF = UF;
        this.neighborhood = neighborhood;
    }

    public Long getIdAddress() {
        return idAddress;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public int getNumberHome() {
        return numberHome;
    }

    public void setNumberHome(int numberHome) {
        this.numberHome = numberHome;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
}
