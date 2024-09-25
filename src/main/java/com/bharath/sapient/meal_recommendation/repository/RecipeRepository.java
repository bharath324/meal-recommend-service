package com.bharath.sapient.meal_recommendation.repository;

import com.bharath.sapient.meal_recommendation.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe,Long> {
    List<Recipe> findByCuisineTypeContains(String cusineType, Pageable pageable);
    Page<Recipe> findByCuisineTypeContaining(String cusineType, Pageable pageable);
}
