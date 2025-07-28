package com.AI.AiProject.controller;

import com.AI.AiProject.model.Recipe;
import com.AI.AiProject.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@CrossOrigin // Allow requests from our frontend
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/save")
    public ResponseEntity<Recipe> saveRecipe(@RequestBody Recipe recipe, Principal principal) {
        String userEmail = principal.getName();
        Recipe savedRecipe = recipeService.saveRecipe(recipe, userEmail);
        return ResponseEntity.ok(savedRecipe);
    }

    @GetMapping("/my-recipes")
    public ResponseEntity<List<Recipe>> getMyRecipes(Principal principal) {
        String userEmail = principal.getName();
        List<Recipe> recipes = recipeService.getRecipesForUser(userEmail);
        return ResponseEntity.ok(recipes);
    }

    // --- ADD THIS NEW ENDPOINT ---
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Integer recipeId, Principal principal) {
        String userEmail = principal.getName();
        recipeService.deleteRecipe(recipeId, userEmail);
        return ResponseEntity.noContent().build(); // Standard response for successful deletion
    }
    // --------------------------
}