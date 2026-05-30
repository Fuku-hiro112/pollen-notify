package com.fuku.pollen_notify.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="Pollen")
public class ObservationPoint {
    @Id
    private Integer id;
    private String country;
    private String prefecture;
    private String city;


    public Integer GetId() {
        return id;
    }

    public void SetId(Integer id) {
        this.id = id;
    }

    public String GetCountry() {
        return country;
    }

    public void SetCountry(String country) {
        this.country = country;
    }

    public String GetPrefecture() {
        return prefecture;
    }

    public void SetPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String GetCity() {
        return city;
    }

    public void SetCity(String city) {
        this.city = city;
    }
}