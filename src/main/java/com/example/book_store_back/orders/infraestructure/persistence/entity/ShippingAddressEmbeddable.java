package com.example.book_store_back.orders.infraestructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddressEmbeddable {

    @Column(name = "shipping_province")
    private String province;

    @Column(name = "shipping_city")
    private String city;

    @Column(name = "shipping_sector")
    private String sector;

    @Column(name = "shipping_place")
    private String place; // Casa, Trabajo, Oficina, etc.

    @Column(name = "shipping_phone")
    private String phone;

    @Column(name = "shipping_recipient")
    private String recipient;

    @Column(name = "shipping_main_street")
    private String mainStreet;

    @Column(name = "shipping_number")
    private String number;

    @Column(name = "shipping_cross_street")
    private String crossStreet;

    @Column(name = "shipping_neighborhood")
    private String neighborhood;

    @Column(name = "shipping_reference")
    private String reference;

    @Column(name = "shipping_zip_code")
    private String zipCode;

    @Column(name = "shipping_country")
    private String country;

}
