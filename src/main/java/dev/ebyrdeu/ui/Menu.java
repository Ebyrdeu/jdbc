package dev.ebyrdeu.ui;

import dev.ebyrdeu.dao.IngredientDao;
import dev.ebyrdeu.dao.RecipeDao;
import dev.ebyrdeu.model.IngredientBuilder;
import dev.ebyrdeu.model.Recipe;
import dev.ebyrdeu.model.RecipeBuilder;
import dev.ebyrdeu.lib.utils.Utils;

import java.util.Scanner;

public class Menu {
    private static final String OPTIONS = """
                MENU
                ======== Recipes
                1 - show all recipes
                2 - show single recipe
                3 - update single recipe
                4 - delete single recipe
                5 - create single recipe
                ======== Ingredients
                6 - show all ingredients
                7 - show single ingredient
                8 - update single ingredient
                9 - delete single ingredient
                10 - create single ingredient
                ======== Special
                11 - show single recipe with ingredients
                12 - update single recipe favorite status
                13 - show all recipes with favorites status
                14 - show total amount of recipes
            """;
    private static final RecipeDao RECIPE_DAO = new RecipeDao();
    private static final IngredientDao INGREDIENT_DAO = new IngredientDao();

    public static void printMenu(Scanner scanner) {
        boolean continueMenu;
        do {
            Utils.log(OPTIONS);
            continueMenu = setOptions(scanner);
        } while (continueMenu);
    }

    private static boolean setOptions(Scanner scanner) {
        switch (scanner.nextLine()) {
            case "1" -> findManyRecipes();
            case "2" -> findUniqueRecipe(scanner);
            case "3" -> updateSingleRecipe(scanner);
            case "4" -> deleteSingleRecipe(scanner);
            case "5" -> createSingleRecipe(scanner);

            case "6" -> findManyIngredients();
            case "7" -> findUniqueIngredient(scanner);
            case "8" -> updateSingleIngredient(scanner);
            case "9" -> deleteSingleIngredient(scanner);
            case "10" -> createSingleIngredient(scanner);

            case "11" -> findUniqueRecipeWithIngredients(scanner);
            case "12" -> setRecipeToFavorite(scanner);
            case "13" -> findManyFavoriteRecipes();
            case "14" -> findManyAmountOfRecipes();
            case "e", "E" -> {
                return false;
            }
            default -> Utils.log("Wrong Key");
        }
        return true;
    }

    private static void findManyAmountOfRecipes() {
        RECIPE_DAO.findManyCount();
    }

    private static void findManyFavoriteRecipes() {
        RECIPE_DAO.findManyFavorite();
    }

    private static void setRecipeToFavorite(Scanner scanner) {
        Utils.log("Enter recipe id");
        int id = scanner.nextInt();

        Utils.log("Enter favorite status 0 = false / 1  = true");
        int favorite = scanner.nextInt();

        var recipe = new RecipeBuilder().setId(id).setFavorite(favorite).createRecipe();

        RECIPE_DAO.updateToFavorite(recipe);
    }

    private static void createSingleIngredient(Scanner scanner) {
        Utils.log("Enter desired recipe id to for this Ingredient");
        int id = scanner.nextInt();

        Utils.log("Enter ingredient name:");
        var name = scanner.next();

        Utils.log("Enter ingredient quantity:");
        int quantity = scanner.nextInt();

        Utils.log("Enter unit name:");
        var unit = scanner.next();

        var ingredient = new IngredientBuilder().setRecipe_id(id).setName(name).setQuantity(quantity).setUnit(unit).createIngredient();

        INGREDIENT_DAO.create(ingredient);
    }

    private static void deleteSingleIngredient(Scanner scanner) {
        Utils.log("Enter desired recipe id to delete a single recipe");
        var id = scanner.nextInt();
        var ingredient = new IngredientBuilder().setIngredient_id(id).createIngredient();
        INGREDIENT_DAO.delete(ingredient);
    }

    private static void updateSingleIngredient(Scanner scanner) {
        Utils.log("Enter ingredient id");
        int ingredientId = scanner.nextInt();

        Utils.log("Enter ingredient name:");
        var name = scanner.next();

        Utils.log("Enter ingredient quantity:");
        int quantity = scanner.nextInt();

        Utils.log("Enter unit name:");
        var unit = scanner.next();

        var ingredient = new IngredientBuilder().setIngredient_id(ingredientId).setName(name).setQuantity(quantity).setUnit(unit).createIngredient();
        INGREDIENT_DAO.update(ingredient);
    }

    private static void findUniqueIngredient(Scanner scanner) {
        Utils.log("Enter desired recipe id to show a single recipe");
        var id = scanner.nextInt();

        var ingredient = new IngredientBuilder().setIngredient_id(id).createIngredient();
        INGREDIENT_DAO.findUnique(ingredient);
    }

    private static void findManyIngredients() {
        INGREDIENT_DAO.findMany();
    }

    private static void findUniqueRecipeWithIngredients(Scanner scanner) {
        Utils.log("Enter recipe id");
        int id = scanner.nextInt();

        var recipe = new RecipeBuilder().setId(id).createRecipe();

        RECIPE_DAO.findUniqueWithIngredients(recipe);
    }


    private static void findManyRecipes() {
        RECIPE_DAO.findMany();
    }

    private static void deleteSingleRecipe(Scanner scanner) {
        Utils.log("Enter desired recipe id to delete a single recipe");
        var id = scanner.nextInt();
        Recipe recipe = new RecipeBuilder().setId(id).createRecipe();

        RECIPE_DAO.delete(recipe);
    }

    private static void updateSingleRecipe(Scanner scanner) {
        Utils.log("Enter desired recipe id to change change a single recipe");
        int id = scanner.nextInt();

        Utils.log("Enter recipe name:");
        var name = scanner.next();

        Utils.log("Enter recipe description:");
        var description = scanner.next();

        Utils.log("Enter preparation time (in minutes):");
        int preparationTime = scanner.nextInt();

        Utils.log("Enter cooking time (in minutes):");
        int cookingTime = scanner.nextInt();

        Utils.log("Enter number of servings:");
        int servings = scanner.nextInt();

        Recipe recipe = new RecipeBuilder().setId(id).setName(name).setDescription(description).setPreparation_time_minutes(preparationTime).setCooking_time_minutes(cookingTime).setServings(servings).createRecipe();

        RECIPE_DAO.update(recipe);
    }

    private static void findUniqueRecipe(Scanner scanner) {
        Utils.log("Enter desired recipe id to show a single recipe");
        var id = scanner.nextInt();

        Recipe recipe = new RecipeBuilder().setId(id).createRecipe();
        RECIPE_DAO.findUnique(recipe);
    }

    private static void createSingleRecipe(Scanner scanner) {
        Utils.log("Enter recipe name:");
        var name = scanner.nextLine();

        Utils.log("Enter recipe description:");
        var description = scanner.nextLine();

        Utils.log("Enter preparation time (in minutes):");
        int preparationTime = scanner.nextInt();

        Utils.log("Enter cooking time (in minutes):");
        int cookingTime = scanner.nextInt();

        Utils.log("Enter number of servings:");
        int servings = scanner.nextInt();

        Recipe recipe = new RecipeBuilder().setName(name).setDescription(description).setPreparation_time_minutes(preparationTime).setCooking_time_minutes(cookingTime).setServings(servings).createRecipe();

        RECIPE_DAO.create(recipe);
    }
}
