package com.AI.AiProject.service;

import com.AI.AiProject.model.Recipe;
import com.AI.AiProject.model.User;
import com.AI.AiProject.repository.RecipeRepository;
import com.AI.AiProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Recipe saveRecipe(Recipe recipe, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        recipe.setUser(user);
        return recipeRepository.save(recipe);
    }

    @Transactional(readOnly = true)
    public List<Recipe> getRecipesForUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        return user.getRecipes();
    }

    // --- ADD THIS NEW METHOD ---
    @Transactional
    public void deleteRecipe(Integer recipeId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + recipeId));

        // Security Check: Ensure the recipe belongs to the user trying to delete it
        if (!recipe.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this recipe.");
        }

        recipeRepository.delete(recipe);
    }
    // -------------------------
}
