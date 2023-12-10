package dev.ebyrdeu.dao;

import dev.ebyrdeu.database.Connector;
import dev.ebyrdeu.database.SqlBuilder;
import dev.ebyrdeu.lib.utils.Utils;
import dev.ebyrdeu.model.Ingredient;

import java.sql.*;

public class IngredientDao implements repository<Ingredient> {
    private static void formatToTable(ResultSet rs) throws SQLException {
        System.out.printf("%-15s | %-15s | %-20s | %-10s | %-10s | %-25s | %-25s%n", "Ingredient ID", "Recipe ID", "Ingredient Name", "Quantity", "Unit", "Created At", "Updated At");
        while (rs.next()) {
            System.out.printf("%-15s | %-15s | %-20s | %-10s | %-10s | %-25s | %-25s%n", rs.getInt("ingredient_id"), rs.getInt("recipe_id"), rs.getString("ingredient_name"), rs.getInt("quantity"), rs.getString("unit"), rs.getTimestamp("created_at"), rs.getTimestamp("updated_at"));
        }
    }

    @Override
    public void create(Ingredient entity) {
        var sql = new SqlBuilder()
                .insert("ingredient", "recipe_id", "ingredient_name", "quantity", "unit")
                .execute();

        try (Connection connection = Connector.connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, entity.recipe_id());
            pstmt.setString(2, entity.name());
            pstmt.setInt(3, entity.quantity());
            pstmt.setString(4, entity.unit());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Creating recipe failed, no rows affected");
            Utils.log("Successfully created ingredient for recipe with id" + entity.recipe_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findUnique(Ingredient entity) {
        var sql = new SqlBuilder().select().from("ingredient").where("ingredient_id").execute();

        try (Connection connection = Connector.connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, entity.ingredient_id());
            ResultSet rs = pstmt.executeQuery();
            formatToTable(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findMany() {
        var sql = new SqlBuilder().select().from("ingredient").execute();

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
    public void update(Ingredient entity) {
        var sql = new SqlBuilder()
                .update("ingredient", "ingredient_name", "quantity", "unit", "updated_at")
                .where("ingredient_id")
                .execute();

        try (Connection connection = Connector.connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.name());
            pstmt.setInt(2, entity.quantity());
            pstmt.setString(3, entity.unit());
            pstmt.setString(4, Utils.getCurrentTime());
            pstmt.setInt(5, entity.ingredient_id());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0)
                throw new SQLException("ingredient with id " + entity.ingredient_id() + " - failed, no rows affected.");

            Utils.log("ingredient with id " + entity.ingredient_id() + " - updated successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Ingredient entity) {
        var sql = new SqlBuilder().delete("ingredient").where("ingredient_id").execute();

        try (Connection connection = Connector.connect(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, entity.ingredient_id());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Deleting ingredient failed, no rows affected.");
            Utils.log("Successfully deleted ingredient");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
