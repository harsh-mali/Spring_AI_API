package com.AI.AiProject.repository;

import com.AI.AiProject.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
}