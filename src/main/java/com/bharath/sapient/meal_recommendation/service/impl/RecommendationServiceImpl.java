package com.bharath.sapient.meal_recommendation.service.impl;

import com.bharath.sapient.meal_recommendation.model.Recipe;
import com.bharath.sapient.meal_recommendation.model.Recommendation;
import com.bharath.sapient.meal_recommendation.model.Restaurant;
import com.bharath.sapient.meal_recommendation.service.api.RecipeService;
import com.bharath.sapient.meal_recommendation.service.api.RecommendationService;
import com.bharath.sapient.meal_recommendation.service.api.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private static Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);
    @Autowired
    RecipeService recipeService;

    @Autowired
    RestaurantService restaurantService;

    public List<Recommendation> getRecommendationsFor(String cusineType, String city, Pageable pageable) {
        LOG.info("Calling getRecommendationsFor cusine:{} city:{} pageable:{} ", cusineType, city, pageable);
        Page<Recipe> recipes = recipeService.getRecipes(cusineType, pageable);
        Page<Restaurant> restaurants = restaurantService.getRestaurants(city,cusineType,pageable);
        Recommendation recommendation = new Recommendation();
        recommendation.setRestaurants(restaurants.stream().collect(Collectors.toList()));
        recommendation.setRecipes(recipes.stream().collect(Collectors.toList()));
        recommendation.setSourceApi("[localhost:8080/api/recipes,localhost:8080/api/restaurants]");
        recommendation.setKeyword(String.format("cuisine = %s, city=%s", cusineType, city));
        recommendation.setResults(1);
        recommendation.setPrevPage(pageable.getPageNumber());
        recommendation.setNextPage(pageable.getPageNumber());
        if (recipes.hasPrevious() && restaurants.hasPrevious()) {
            recommendation.setPrevPage(pageable.getPageNumber() - 1);
        }
        if (recipes.hasNext() && restaurants.hasNext()) {
            recommendation.setNextPage(pageable.getPageNumber() + 1);
        }
        return List.of(recommendation);
    }

}
