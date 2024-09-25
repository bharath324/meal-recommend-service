package com.bharath.sapient.meal_recommendation.controller;

import com.bharath.sapient.meal_recommendation.model.Recipe;
import com.bharath.sapient.meal_recommendation.service.api.RecipeService;
import com.bharath.sapient.meal_recommendation.service.impl.RecipeServiceImpl;
import com.bharath.sapient.meal_recommendation.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/api/recipes")
    public EntityModel<Page<Recipe>> getAllRecipes(@RequestParam String cusineType,
                                                 @RequestParam String page,
                                                 @RequestParam String limit) {
        System.out.println("page,limit = "+page +" "+limit);
        Pageable pageable  = Utils.getPageable(page, limit);
        System.out.println("pageable = "+pageable);
        return EntityModel.of(recipeService.getRecipes(cusineType,pageable));
    }
}
