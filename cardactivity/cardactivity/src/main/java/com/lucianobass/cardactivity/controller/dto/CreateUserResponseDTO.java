package com.lucianobass.cardactivity.controller.dto;

public class CreateUserResponseDTO {

    private String name;
    private int age;
    private String genere;

    public CreateUserResponseDTO() {
    }

    public CreateUserResponseDTO(String name, int age, String genere) {
        this.name = name;
        this.age = age;
        this.genere = genere;
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
