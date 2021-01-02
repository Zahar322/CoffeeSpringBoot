package com.controller.entity;

import javax.persistence.*;

@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "name")
    private String name;
    @Column(name = "origanal_name")
    private String originalName;
    @Column(name = "content")
    private byte[] content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String imagePath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/image/")
                     .append(getOriginalName())
                     .append("/")
                     .append(getContentType().replaceFirst("/", "9"));

        return stringBuilder.toString();
    }
}
