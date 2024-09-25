package com.bharath.sapient.meal_recommendation.service.api;

import com.bharath.sapient.meal_recommendation.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface RestaurantService {

    Page<Restaurant> getRestaurants(String city, Pageable pageable);
    Page<Restaurant> getRestaurants(String city,String cuisineType, Pageable pageable);
    Page<Restaurant> getRestaurantsFromLocalAPI(String city,Pageable pageable);
}
