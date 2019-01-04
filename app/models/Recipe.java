package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends ModelBase {

    private static final Finder<Long, Recipe> find = new Finder<>(Recipe.class);

    private String title;
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Step> steps;

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

}
