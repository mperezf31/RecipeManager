package controllers;

import models.Recipe;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class RecipeController extends Controller {

    @Inject
    private
    FormFactory formFactory;

    public Result index() {
        return Results.redirect("/recipes");
    }

    /**
     * Create new recipe
     */
    @Transactional
    public Result createRecipe() {
        Form<Recipe> recipe = formFactory.form(Recipe.class).bindFromRequest();

        if (recipe.hasErrors()) {
            return Results.badRequest(Json.toJson(recipe.errorsAsJson()));
        }

        recipe.get().save();

        return Results.ok(Json.toJson(recipe.get()));
    }

    /**
     * Retrieve a recipe by id
     */
    public Result retrieveRecipe(Integer recipeId) {
        Recipe recipe = Recipe.findById(recipeId.longValue());
        if (recipe == null) {
            return Results.notFound();
        }

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
