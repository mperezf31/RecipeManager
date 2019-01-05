package models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Finder;
import play.data.validation.Constraints.Required;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Ingredient extends ModelBase {

    private static final Finder<Long, Ingredient> find = new Finder<>(Ingredient.class);

    @Required(message = "El campo 'name' es requerido")
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "ingredients")
    private List<Recipe> recipes;

    Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Ingredient findById(Long id) {
        return find.query().where().eq("id", id).findOne();
    }

    public static Ingredient findIngredientByName(String name) {
        return find.query().where().eq("name", name).findOne();
    }

}
