package dev.ebyrdeu.model;

import java.sql.Timestamp;

public record Recipe(int id,
                     String name,
                     String description,
                     int preparation_time_minutes,
                     int cooking_time_minutes,
                     int servings,
                     int favorite,
                     Timestamp created_at,
                     Timestamp updated_at
) {

}
