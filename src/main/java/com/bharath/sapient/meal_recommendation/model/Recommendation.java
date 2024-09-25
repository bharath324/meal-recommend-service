package com.bharath.sapient.meal_recommendation.model;

import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

public class Recommendation extends RepresentationModel<Recommendation> {

    private String sourceApi;
    private long results;

    private String keyword;
    private int currentPage;
    private List<Recipe> recipes = new ArrayList<>();
    private List<Restaurant> restaurants = new ArrayList<>();

    private int prevPage;
    private int nextPage;

    public String getSourceApi() {
        return sourceApi;
    }

    public void setSourceApi(String sourceApi) {
        this.sourceApi = sourceApi;
    }

    public long getResults() {
        return results;
    }

    public void setResults(long results) {
        this.results = results;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
}
