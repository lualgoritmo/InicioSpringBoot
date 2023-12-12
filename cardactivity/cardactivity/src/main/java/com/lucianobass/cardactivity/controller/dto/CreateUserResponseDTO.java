package com.lucianobass.cardactivity.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CreateUserResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long idUserClient;
    private String name;
    private int age;
    private String genere;

    public CreateUserResponseDTO() {
    }

    public CreateUserResponseDTO(String name, int age, String genere) {
        this.idUserClient = idUserClient;
        this.name = name;
        this.age = age;
        this.genere = genere;
    }

    public Long getIdUserClient() {
        return idUserClient;
    }

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

}
