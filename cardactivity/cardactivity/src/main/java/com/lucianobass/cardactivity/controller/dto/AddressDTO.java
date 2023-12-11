package com.lucianobass.cardactivity.controller.dto;

import java.io.Serializable;

public class AddressDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idAddress;
    private int cep;
    private int numberHome;
    private String road;
    private String city;
    private String UF;
}
