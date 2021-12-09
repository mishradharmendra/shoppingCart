package com.cg.shopping.profileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private int houseNumber;
    private String streetName;
    private String colonyName;
    private String city;
    private String state;
    private int pincode;
}
