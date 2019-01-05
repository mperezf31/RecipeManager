package controllers;

import models.Ingredient;
import models.Recipe;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class RecipeController extends Controller {

    @Inject
    private FormFactory formFactory;

    /**
     * Create new recipe
     */
    @Transactional
    public Result createRecipe() {
        Form<Recipe> recipeForm = formFactory.form(Recipe.class).bindFromRequest();

        // Check if the form contains errors
        if (recipeForm.hasErrors()) {
            return Results.badRequest(Json.toJson(recipeForm.errorsAsJson()));
        }

        Recipe recipe = recipeForm.get();

        // Check the ingredients of the new recipe, if one does not exist it is added to the database
        List<Ingredient> ingredientsToCreate = recipe.getIngredients();
        recipe.setIngredients(new ArrayList<>());

        for (Ingredient ingredientToCreate : ingredientsToCreate) {
            // Find the ingredient in the database by the name
            Ingredient ingredientInDB = Ingredient.findIngredientByName(ingredientToCreate.getName());
            if (ingredientInDB != null) {
                recipe.addIngredient(ingredientInDB);
            } else {
                ingredientToCreate.save();
                recipe.addIngredient(ingredientToCreate);
            }
        }

        recipe.save();

       return contentNegotiationRecipe(recipe);
    }

    /**
     * Retrieve a recipe by id
     */
    public Result retrieveRecipe(Integer recipeId) {
        Recipe recipe = Recipe.findById(recipeId.longValue());
        if (recipe == null) {
            return Results.notFound();
        }

        return contentNegotiationRecipe(recipe);
    }

    /**
     * Show the recipe result in json or xml format, it depends of the content-negotiation
     */
    private Result contentNegotiationRecipe(Recipe recipe) {
        if (request().accepts("application/json")) {
            return Results.ok(Json.toJson(recipe));
        } else if (request().accepts("application/xml")) {
            return Results.ok(views.xml.recipe.render(recipe));
        } else {
            return Results.notAcceptable();
        }
    }

    /**
     * Remove a recipe
     */
    public Result deleteRecipe(Integer recipeId) {
        Recipe recipe = Recipe.findById(recipeId.longValue());
        if (recipe != null && recipe.delete()) {
            return Results.ok();
        } else {
            return Results.notFound();
        }
    }

    /**
     * List all recipes
     */
    public Result listRecipes() {
        List<Recipe> recipes = Recipe.all();

        if (request().accepts("application/json")) {
            return Results.ok(Json.toJson(recipes));
        } else if (request().accepts("application/xml")) {
            return Results.ok(views.xml.recipes.render(recipes));
        } else {
            return Results.notAcceptable();
        }

    }


}
