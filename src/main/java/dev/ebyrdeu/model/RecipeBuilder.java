package dev.ebyrdeu.model;

import java.sql.Timestamp;

public class RecipeBuilder {
    private int id = 1;
    private String name = "";
    private String description = "";
    private int preparationTimeMinutes = 0;
    private int cookingTimeMinutes = 0;
    private int servings = 0;
    private int favorite = 0;
    private Timestamp createdAt = null;
    private Timestamp updatedAt = null;

    public RecipeBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public RecipeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RecipeBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public RecipeBuilder setPreparation_time_minutes(int preparationTimeMinutes) {
        this.preparationTimeMinutes = preparationTimeMinutes;
        return this;
    }

    public RecipeBuilder setCooking_time_minutes(int cookingTimeMinutes) {
        this.cookingTimeMinutes = cookingTimeMinutes;
        return this;
    }

    public RecipeBuilder setServings(int servings) {
        this.servings = servings;
        return this;
    }

    public RecipeBuilder setFavorite(int favorite) {
        this.favorite = favorite;
        return this;
    }

    public RecipeBuilder setCreated_at(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public RecipeBuilder setUpdated_at(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Recipe createRecipe() {
        return new Recipe(id, name, description, preparationTimeMinutes, cookingTimeMinutes, servings, favorite, createdAt, updatedAt);
    }
}