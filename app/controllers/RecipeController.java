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

    public Result index() {
        return Results.redirect("/recipes");
    }

    /**
     * Create new recipe
     */
    @Transactional
    public Result createRecipe() {
        Form<Recipe> recipeForm = formFactory.form(Recipe.class).bindFromRequest();

        if (recipeForm.hasErrors()) {
            return Results.badRequest(Json.toJson(recipeForm.errorsAsJson()));
        }

        Recipe recipe = recipeForm.get();

        List<Ingredient> ingredientsToCreate = recipe.getIngredients();
        recipe.setIngredients(new ArrayList<>());

        for (Ingredient ingredientToCreate : ingredientsToCreate) {
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

    private Result contentNegotiationRecipe(Recipe recipe) {
        if (request().accepts("application/json")) {
            return Results.ok(Json.toJson(recipe));
        } else if (request().accepts("application/xml")) {
            return Results.ok(Json.toJson(views.xml.recipe.render(recipe)));
        } else {
            return Results.notAcceptable();
        }
    }

    /**
     * Remove recipe
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
