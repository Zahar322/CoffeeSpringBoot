package com.controller.entity;

import javax.persistence.*;

@Entity
@Table(name="coffee")
public class Coffee {

    @Id
    @GeneratedValue
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="price")
    private int price;

    @Column(name="title_ru")
    private String titleRu;

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
}
