package com.bharath.sapient.meal_recommendation.service.impl;

import com.bharath.sapient.meal_recommendation.model.Recipe;
import com.bharath.sapient.meal_recommendation.repository.RecipeRepository;
import com.bharath.sapient.meal_recommendation.service.api.RecipeService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static Logger LOG = LoggerFactory.getLogger(RecipeServiceImpl.class);


    @Autowired
    RecipeRepository recipeRepository;

    public List<Recipe> getRecipesFor(String cusineType) {
        LOG.info("Calling getRecipesFor cusine:{}", cusineType);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", "Bu7/qMpXXTWx4TouUQrCdQ==eL0biqONZMZ8b4uY");

        String url =
                "https://api.api-ninjas.com/v1/recipe?query={query1}";

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Recipe>> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
                },
                Map.of("query1", cusineType));
        List<Recipe> recipes = response.getBody();
        return recipes;
    }

    public Page<Recipe> getRecipesFromLocalAPI(String cusineType, Pageable pageable) {
        LOG.info("Calling getRecipesFromLocalAPI cusine:{} pageable:{}", cusineType, pageable);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", "Bu7/qMpXXTWx4TouUQrCdQ==eL0biqONZMZ8b4uY");

        String url =
                "http://localhost:8080/api/recipes?cusineType={cusineType}&page={page}&limit={limit}";

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<EntityModel<Page<Recipe>>> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
                },
                Map.of("cusineType", cusineType,
                        "page", pageable.getPageNumber(),
                        "limit", pageable.getPageSize()));
        EntityModel<Page<Recipe>> recipeModels = response.getBody();

        Page<Recipe> recipes = recipeModels == null || recipeModels.getContent() == null
                ? Page.empty() : recipeModels.getContent();
        LOG.info("Calling getRecipesFromLocalAPI Restaurants size: " + recipes.getTotalElements());
        return recipes;
    }


    public Page<Recipe> getRecipes(String cusineType, Pageable pageable) {
        LOG.info("Calling getRecipes cuisine:{} pageable:{}", cusineType, pageable);
        Page<com.bharath.sapient.meal_recommendation.entity.Recipe> recipePage =
                recipeRepository.findByCuisineTypeContaining(cusineType, pageable);
        List<Recipe> recipes = recipePage.stream().map(this::toDto).collect(Collectors.toList());
        LOG.info("Calling getRecipes Recipes total size: " + recipePage.getTotalElements());
        return new PageImpl<>(recipes,
                recipePage.getPageable(),
                recipePage.getTotalElements());
    }

    private Recipe toDto(com.bharath.sapient.meal_recommendation.entity.Recipe recipe) {

        Recipe recipeDto = new Recipe();
        recipeDto.setIngredients(recipe.getIngredients());
        recipeDto.setInstructions(recipe.getInstructions());
        recipeDto.setTitle(recipe.getTitle());
        recipeDto.setCuisineType(recipe.getCuisineType());
        return recipeDto;
    }
}
