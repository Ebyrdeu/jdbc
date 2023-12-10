package dev.ebyrdeu.model;

import java.sql.Timestamp;

public class IngredientBuilder {
    private int ingredientId = 1;
    private int recipeId = 1;
    private String name = "";
    private int quantity = 0;
    private String unit = "";
    private Timestamp createdAt = null;
    private Timestamp updatedAt = null;

    public IngredientBuilder setIngredient_id(int ingredientId) {
        this.ingredientId = ingredientId;
        return this;
    }

    public IngredientBuilder setRecipe_id(int recipeId) {
        this.recipeId = recipeId;
        return this;
    }

    public IngredientBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public IngredientBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public IngredientBuilder setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public IngredientBuilder setCreated_at(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public IngredientBuilder setUpdated_at(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Ingredient createIngredient() {
        return new Ingredient(ingredientId, recipeId, name, quantity, unit, createdAt, updatedAt);
    }
}