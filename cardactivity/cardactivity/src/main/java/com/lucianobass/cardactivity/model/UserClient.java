package com.lucianobass.cardactivity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_user")
public class UserClient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUser;
    private String name;
    private int age;
    private String genere;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Address> address;

    public UserClient() {
    }

    public UserClient(String name, int age, String genere) {
        this.name = name;
        this.age = age;
        this.genere = genere;
    }

    public Long getIdUser() {
        return idUser;
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

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserClient user = (UserClient) o;
        return age == user.age && idUser.equals(user.idUser) && Objects.equals(name, user.name) && Objects.equals(genere, user.genere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }
}
