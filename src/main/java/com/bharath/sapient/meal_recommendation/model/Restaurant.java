package com.bharath.sapient.meal_recommendation.model;

import org.springframework.hateoas.RepresentationModel;

public class Restaurant extends RepresentationModel<Restaurant> {

    private Integer id;
    private String name;
    private String city;
    private String menu;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
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


}
