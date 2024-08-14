package com.example.demo1.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name="contact")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String title;
    private String phone;
    private String address;
    private String status;
    private String photoUrl;

    public Contact(Integer id, String name, String email, String title, String phone, String address, String status, String photoUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.title = title;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.photoUrl = photoUrl;
    }

    public Contact() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
