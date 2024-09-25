package com.bharath.sapient.meal_recommendation.controller;

import com.bharath.sapient.meal_recommendation.model.Recommendation;
import com.bharath.sapient.meal_recommendation.service.api.RecommendationService;
import com.bharath.sapient.meal_recommendation.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController()
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    @GetMapping("/api/recommendations")
    public CollectionModel<Recommendation> getRecommendations(@RequestParam String cusineType,
                                                              @RequestParam String city,
                                                              @RequestParam String page,
                                                              @RequestParam String limit) {

        if (cusineType == null || city == null || cusineType.isEmpty() || city.isEmpty()) {
            System.err.format("cuisine = %s or city = %s is empty or null\n", cusineType, city);
            return CollectionModel.empty();
        }

        Pageable pageable = Utils.getPageable(page, limit);
        List<Recommendation> recommendations =
                recommendationService.getRecommendationsFor(cusineType, city,pageable);
        Link self = linkTo(methodOn(RecommendationController.class)
                .getRecommendations(cusineType, city, page, limit))
                .withSelfRel();
        Link allRecipes =
                linkTo(methodOn(RecipeController.class)
                        .getAllRecipes(cusineType,page,limit))
                        .withRel("allRecipes");
        Link allRestaurants =
                linkTo(methodOn(RestaurantController.class)
                        .getAllRestaurants(city,page,limit))
                        .withRel("allRestaurants");
        return CollectionModel.of(recommendations, self, allRecipes, allRestaurants);
    }

}
