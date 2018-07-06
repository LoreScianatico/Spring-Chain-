package com.lorescianatico.spring.chain.controller;

import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public String saveRecipe(@ModelAttribute RecipeDto recipeDto){return null;}

    @GetMapping
    @RequestMapping("recipe/{id}")
    public String getRecipeById(@PathVariable Long id, Model model){
        return null;
    }

    @PatchMapping
    public String patchRecipe(@ModelAttribute RecipeDto recipeDto){
        return null;
    }

    @GetMapping
    @RequestMapping("recipe")
    public String getByName(@RequestParam String name, Model model){
        return null;
    }
}
