package com.bharath.sapient.meal_recommendation.service.api;

import com.bharath.sapient.meal_recommendation.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecipeService {
    public Page<Recipe> getRecipes(String cusineType, Pageable pageable);
    public Page<Recipe> getRecipesFromLocalAPI(String cusineType,Pageable pageable);
}
