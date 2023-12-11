package com.lucianobass.cardactivity.controller.dto;

import java.io.Serializable;

public class CreateAddressRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idAddress;
    private int cep;
    private int numberHome;
    private String road;
    private String city;
    private String UF;

    public CreateAddressRequestDTO() {
    }

    public CreateAddressRequestDTO(int cep, int numberHome, String road, String city, String UF) {
        this.cep = cep;
        this.numberHome = numberHome;
        this.road = road;
        this.city = city;
        this.UF = UF;
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
}
