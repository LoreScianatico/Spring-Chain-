package com.lorescianatico.spring.chain.repository;

import com.lorescianatico.spring.chain.model.Ingredient;
import com.lorescianatico.spring.chain.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        Recipe recipe = new Recipe("Sample recipe");
        Ingredient ingredient = new Ingredient("Sample ingredient");
        recipe.setIngredients(Collections.singleton(ingredient));
        recipeRepository.save(recipe);
    }

    @Test
    public void findByName() {

        List<Recipe> recipes = recipeRepository.findByName("Sample recipe");
        assertFalse(recipes.isEmpty());
        assertEquals(1, recipes.size());

    }

    @Test
    public void findByNameStartsWith() {

        List<Recipe> recipes = recipeRepository.findByNameStartsWith("Sample");
        assertFalse(recipes.isEmpty());
        assertEquals(1, recipes.size());

    }
}