package models;


import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Ingredient extends ModelBase {

    @ManyToMany(mappedBy = "ingredients")
    public Set<Recipe> recipes;

    private String name;

    Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
