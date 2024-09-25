package com.bharath.sapient.meal_recommendation.controller;

import com.bharath.sapient.meal_recommendation.model.Restaurant;
import com.bharath.sapient.meal_recommendation.service.api.RestaurantService;
import com.bharath.sapient.meal_recommendation.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/api/restaurants")
    public CollectionModel<Restaurant> getAllRestaurants(@RequestParam String city,
                                                         @RequestParam String page,
                                                         @RequestParam String limit) {
        Pageable pageable  = Utils.getPageable(page, limit);
        return CollectionModel.of(restaurantService.getRestaurants(city,pageable))
                .withFallbackType(Restaurant.class);
    }

    @GetMapping("/api/v2/restaurants")
    public CollectionModel<Restaurant> getAllRestaurants(@RequestParam String city,
                                                         @RequestParam String cuisineType,
                                                         @RequestParam String page,
                                                         @RequestParam String limit) {
        Pageable pageable  = Utils.getPageable(page, limit);
        return CollectionModel.of(restaurantService.getRestaurants(city,cuisineType,pageable))
                .withFallbackType(Restaurant.class);
    }

}
