package models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Finder;
import play.data.validation.Constraints.Required;
import validators.UniqueRecipeTitle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends ModelBase {

    private static final Finder<Long, Recipe> find = new Finder<>(Recipe.class);

    @UniqueRecipeTitle(message = "unique-recipe-title")
    @Required(message = "title-is-required")
    private String title;

    @Required(message = "description-is-required")
    private String description;

    @JsonManagedReference
    @Required(message = "ingredients-is-required")
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    @JsonManagedReference
    @Required(message = "steps-is-required")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Step> steps;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private NutritionalData nutritionalData;

    public Recipe(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public NutritionalData getNutritionalData() {
        return nutritionalData;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void setNutritionalData(NutritionalData nutritionalData) {
        this.nutritionalData = nutritionalData;
    }

    public static Recipe findById(Long id) {
        return find.query().where().eq("id", id).findOne();
    }

    public static Recipe findByTitle(String title) {
        return find.query().where().eq("title", title).findOne();
    }

    public static List<Recipe> all() {
        return find.query().findList();
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.getRecipes().add(this);
    }
}
