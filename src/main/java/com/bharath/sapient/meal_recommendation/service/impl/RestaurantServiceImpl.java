package com.bharath.sapient.meal_recommendation.service.impl;

import com.bharath.sapient.meal_recommendation.model.Restaurant;
import com.bharath.sapient.meal_recommendation.repository.RestaurantRepository;
import com.bharath.sapient.meal_recommendation.service.api.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static Logger LOG = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    @Autowired
    RestaurantRepository restaurantRepository;


    public Page<Restaurant> getRestaurantsFromLocalAPI(String city, Pageable pageable) {
        LOG.info("Calling getRestaurantsFromLocalAPI city:{} pageable:{}", city, pageable);
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", "Bu7/qMpXXTWx4TouUQrCdQ==eL0biqONZMZ8b4uY");

            String url =
                    "http://localhost:8080/api/restaurants?city={city}&page={page}&limit={limit}";

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<EntityModel<Page<Restaurant>>> response = restTemplate.exchange(
                    url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
                    },
                    Map.of("city", city, "page", pageable.getPageNumber(), "limit", pageable.getPageSize()));
            EntityModel<Page<Restaurant>> restaurantsModels = response.getBody();
            Page<Restaurant> restaurants = restaurantsModels == null || restaurantsModels.getContent() == null ?
                    Page.empty() : restaurantsModels.getContent();
            LOG.info("Calling getRestaurantsFromLocalAPI Restaurants size: " + restaurants.getTotalElements());
            return restaurants;
        } catch (Exception e) {
            LOG.error("Error occurred while retrieving the restaurants ", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public Page<Restaurant> getRestaurants(String city, String cuisineType, Pageable pageable) {
        LOG.info("Calling getRestaurantsForCityCuisine city:{} cuisine:{} pageable:{}",
                city, cuisineType, pageable);
        try {
            Page<com.bharath.sapient.meal_recommendation.entity.Restaurant>
                    restaurantPage = restaurantRepository
                          .findByCityContainsAndMenuContains(city, cuisineType, pageable);
            List<Restaurant> restaurants =
                    restaurantPage
                            .stream()
                            .map(this::toDto)
                            .collect(Collectors.toList());
            LOG.info("Calling getRestaurantsForCityCuisine Restaurants size: " + restaurants.size());
            return new PageImpl<>(restaurants,
                    restaurantPage.getPageable(),
                    restaurantPage.getTotalElements());
        } catch (Exception e) {
            LOG.error("Error occurred while retrieving the getRestaurantsForCityCuisine ", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public Page<Restaurant> getRestaurants(String city, Pageable pageable) {
        LOG.info("Calling getRestaurantsFor city:{} pageable:{}", city, pageable);
        try {
            Page<com.bharath.sapient.meal_recommendation.entity.Restaurant>
                    restaurantPage = restaurantRepository.findByCityContains(city, pageable);
            List<Restaurant> restaurants =
                    restaurantPage
                            .stream()
                            .map(this::toDto)
                            .collect(Collectors.toList());
            LOG.info("Calling getRestaurantsFor Restaurants size: " + restaurants.size());
            return new PageImpl<>(restaurants,
                    restaurantPage.getPageable(),
                    restaurantPage.getTotalElements());
        } catch (Exception e) {
            LOG.error("Error occurred while retrieving the restaurants ", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    private Restaurant toDto(com.bharath.sapient.meal_recommendation.entity.Restaurant restaurant) {
        Restaurant restaurantDto = new Restaurant();
        restaurantDto.setMenu(restaurant.getMenu());
        restaurantDto.setCity(restaurant.getCity());
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setId(restaurant.getId());
        return restaurantDto;
    }
}
