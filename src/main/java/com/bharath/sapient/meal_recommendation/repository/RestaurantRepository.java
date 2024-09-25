package com.bharath.sapient.meal_recommendation.repository;

import com.bharath.sapient.meal_recommendation.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant,Long> {
    Page<Restaurant> findByCityContains(String city, Pageable pageable);

    Page<Restaurant> findByCityContainsAndMenuContains(String city, String menu,Pageable pageable);
}
