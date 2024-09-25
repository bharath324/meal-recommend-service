package com.bharath.sapient.meal_recommendation.util;

import com.bharath.sapient.meal_recommendation.model.Recipe;
import com.bharath.sapient.meal_recommendation.model.Recommendation;
import com.bharath.sapient.meal_recommendation.model.Restaurant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {


    private static final String CUSTOM_SOURCE = "custom source";
    public static List<Recommendation> generateRecommendationsFor(String cusineType, String city) {
        List<Recommendation> recommendations = new ArrayList<>();

        Recipe recipe = new Recipe();
        recipe.setIngredients("dummy ingredients");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(new Random().nextInt());
        restaurant.setCity("dummy address");
        restaurant.setName("dummy restaurant name");

        Recommendation recommendation = new Recommendation();
        recommendation.setCurrentPage(1);
        recommendation.setKeyword(cusineType);
        recommendation.setNextPage(0);
        recommendation.setPrevPage(0);
        recommendation.setResults(2);
        recommendation.setSourceApi(CUSTOM_SOURCE);
        recommendation.setRecipes(Arrays.asList(recipe));
        recommendation.setRestaurants(Arrays.asList(restaurant));

        recommendations.add(recommendation);
        return recommendations;
    }

    public static Pageable getPageable(String page, String limit) {
        try {
            return PageRequest.of(Integer.parseInt(page),Integer.parseInt(limit));
        } catch (Exception e) {
            throw new IllegalArgumentException("Illegal page or limit");
        }
    }
}
