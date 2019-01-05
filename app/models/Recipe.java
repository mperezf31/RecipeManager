package models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Finder;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends ModelBase {

    private static final Finder<Long, Recipe> find = new Finder<>(Recipe.class);

    @Required(message = "El campo 'title' es requerido")
    private String title;

    @Required(message = "El campo 'description' es requerido")
    private String description;

    @JsonManagedReference
    @Required(message = "El campo 'ingredients' es requerido")
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    //  @Required(message = "El campo 'steps' es requerido")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Step> steps;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private NutritionalData nutritionalData;

    Recipe(String title, String description) {
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

    public static List<Recipe> all() {
        return find.query().findList();
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.getRecipes().add(this);
    }
}
