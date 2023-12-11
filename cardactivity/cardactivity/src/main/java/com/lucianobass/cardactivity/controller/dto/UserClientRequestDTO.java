package com.lucianobass.cardactivity.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucianobass.cardactivity.model.Address;

import java.io.Serializable;
import java.util.List;

public class UserClientRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idUser;
    private String name;
    private int age;
    private String genere;
    @JsonIgnore
    private List<Address> address;

    public UserClientRequestDTO() {
    }

    public UserClientRequestDTO(String name, int age, String genere) {
        //this.idUser = idUser;
        this.name = name;
        this.age = age;
        this.genere = genere;
    }

    public UserClientRequestDTO(List<Address> address) {
        this.address = address;
    }

//    //public Long getIdUser() {
//        return idUser;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
}
