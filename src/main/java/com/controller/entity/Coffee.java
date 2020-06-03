package com.controller.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;

@Entity
@Table(name="coffee")
public class Coffee implements AbstractImage {

    @Id
    @GeneratedValue
    private int id;

    @Column(name="title")
    private String title;


    @Column(name="price")
    private int price;

    @Column(name="title_ru")
    private String titleRu;


    @Column(name="about")
    private String about;

    @Column(name = "image")
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", titleRu='" + titleRu + '\'' +
                ", about='" + about + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
