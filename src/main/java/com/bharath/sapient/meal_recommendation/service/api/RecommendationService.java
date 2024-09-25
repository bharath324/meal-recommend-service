package com.bharath.sapient.meal_recommendation.service.api;

import com.bharath.sapient.meal_recommendation.model.Recipe;
import com.bharath.sapient.meal_recommendation.model.Recommendation;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public interface RecommendationService {

    public List<Recommendation> getRecommendationsFor(String cusineType, String city, Pageable pageable);

}
