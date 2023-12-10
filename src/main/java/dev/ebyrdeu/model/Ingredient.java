package dev.ebyrdeu.model;

import java.sql.Timestamp;

public record Ingredient(
        int ingredient_id,
        int recipe_id,
        String name,
        int quantity,
        String unit,
        Timestamp created_at,
        Timestamp updated_at

) {
}
