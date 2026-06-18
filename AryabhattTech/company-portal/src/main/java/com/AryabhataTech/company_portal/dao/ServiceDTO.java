package com.AryabhataTech.company_portal.dao;

public class ServiceDTO {

    private int id;
    private String headline;
    private String category;
    private String description;
    private String link;

    public ServiceDTO(int id, String headline, String category, String description, String link) {
        this.id = id;
        this.headline = headline;
        this.category = category;
        this.description = description;
        this.link = link;
    }

    public ServiceDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
