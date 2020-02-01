package com.lorescianatico.spring.chain.controller;

import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void saveRecipe() {
    }

    @Test
    public void getRecipeById() {
    }

    @Test
    public void patchRecipe() {
    }

    @Test
    public void getByName() {
    }


    @Test
    public void getAllRecipes() throws Exception {

        List<RecipeDto> recipes = new ArrayList<>();
        recipes.add(new RecipeDto());

        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);

        recipes.add(recipe);

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        //when
        mockMvc.perform(get("/recipe/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("recipes"))
                .andExpect(model().attribute("recipes", recipes));

        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    public void getEmptyRecipe() throws Exception {

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

    }
}