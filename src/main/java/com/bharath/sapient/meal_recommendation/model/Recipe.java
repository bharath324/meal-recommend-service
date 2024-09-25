package com.bharath.sapient.meal_recommendation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe extends RepresentationModel<Recipe> {

    private String ingredients;
    private String title;
    private String cuisineType;
    private String instructions;

    public String getIngredients() {
        return ingredients;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
