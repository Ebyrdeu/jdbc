package dev.ebyrdeu.dao;

import dev.ebyrdeu.database.Connector;
import dev.ebyrdeu.database.SqlBuilder;
import dev.ebyrdeu.lib.utils.Utils;
import dev.ebyrdeu.model.Recipe;

import java.sql.*;

public class RecipeDao implements repository<Recipe> {


    private static void formatToTable(ResultSet rs) throws SQLException {
        System.out.printf("%-5s | %-20s | %-30s | %-25s | %-25s | %-10s | %-10s | %-20s | %-20s%n", "ID", "Recipe Name", "Description", "Preparation Time", "Cooking Time", "Servings", "Favorite", "Created At", "Updated At");
        while (rs.next()) {
            System.out.printf("%-5d | %-20s | %-30s | %-25d | %-25d | %-10d | %-10s | %-20s | %-20s%n", rs.getInt("recipe_id"), rs.getString("recipe_name"), rs.getString("description"), rs.getInt("preparation_time_minutes"), rs.getInt("cooking_time_minutes"), rs.getInt("servings"), rs.getInt("favorite"), rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"));
        }
    }

    public void updateToFavorite(Recipe entity) {
        var sql = new SqlBuilder().update("recipe", "favorite").where("recipe_id").execute();

        try (Connection connection = Connector.connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, entity.favorite());
            pstmt.setInt(2, entity.id());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) throw new SQLException("Updating recipe failed, no rows affected.");
            Utils.log("Successfully updated recipe");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findManyFavorite() {
        var sql = new SqlBuilder().select().from("recipe").where("favorite", 1).execute();

        try (Connection connection = Connector.connect(); Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            formatToTable(rs);
            System.out.println();
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findManyCount() {
        var sql = new SqlBuilder().count("recipe", "recipe_name", true).execute();

        try (Connection connection = Connector.connect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            Utils.log(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Recipe entity) {
        var sql = new SqlBuilder().insert("recipe", "recipe_name", "description", "preparation_time_minutes", "cooking_time_minutes", "servings").execute();

        try (Connection connection = Connector.connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.name());
            pstmt.setString(2, entity.description());
            pstmt.setInt(3, entity.preparation_time_minutes());
            pstmt.setInt(4, entity.cooking_time_minutes());
            pstmt.setInt(5, entity.servings());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) throw new SQLException("Creating recipe failed, no rows affected.");
            Utils.log("Successfully created recipe");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findMany() {
        var sql = new SqlBuilder().select().from("recipe").execute();

        try (Connection connection = Connector.connect(); Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            formatToTable(rs);
            System.out.println();
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findUnique(Recipe entity) {
        var sql = new SqlBuilder().select().from("recipe").where("recipe_id").execute();

        try (Connection connection = Connector.connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, entity.id());
            ResultSet rs = pstmt.executeQuery();
            formatToTable(rs);
            System.out.println();
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Recipe entity) {
        var sql = new SqlBuilder().update("recipe", "recipe_name", "description", "preparation_time_minutes", "cooking_time_minutes", "servings", "updated_at").where("recipe_id").execute();

        try (Connection connection = Connector.connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {


            pstmt.setString(1, entity.name());
            pstmt.setString(2, entity.description());
            pstmt.setInt(3, entity.preparation_time_minutes());
            pstmt.setInt(4, entity.cooking_time_minutes());
            pstmt.setInt(5, entity.servings());
            pstmt.setString(6, Utils.getCurrentTime());
            pstmt.setInt(7, entity.id());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0)
                throw new SQLException("recipe with id " + entity.id() + " - failed, no rows affected.");

            Utils.log("recipe with id " + entity.id() + " - updated successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Recipe entity) {
        var sql = new SqlBuilder().delete("recipe").where("recipe_id").execute();

        try (Connection connection = Connector.connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, entity.id());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Deleting recipe failed, no rows affected.");
            Utils.log("Successfully deleted recipe");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findUniqueWithIngredients(Recipe entity) {
        var sql = new SqlBuilder().join("recipe", "ingredient", "recipe_id").where("ingredient.recipe_id").execute();
        
        try (Connection connection = Connector.connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, entity.id());
            ResultSet rs = pstmt.executeQuery();
            System.out.printf("%-15s | %-20s | %-20s | %-25s | %-25s | %-15s | %-25s | %-25s | %-15s | %-20s | %-15s | %-10s%n", "Recipe ID", "Recipe Name", "Description", "Preparation Time (min)", "Cooking Time (min)", "Servings", "Created At", "Updated At", "Ingredient ID", "Ingredient Name", "Quantity", "Unit");
            while (rs.next()) {
                System.out.printf("%-15d | %-20s | %-20s | %-25d | %-25d | %-15d | %-25s | %-25s | %-15d | %-20s | %-15d | %-10s%n", rs.getInt("recipe_id"), rs.getString("recipe_name"), rs.getString("description"), rs.getInt("preparation_time_minutes"), rs.getInt("cooking_time_minutes"), rs.getInt("servings"), rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"), rs.getInt("ingredient_id"), rs.getString("ingredient_name"), rs.getInt("quantity"), rs.getString("unit"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
