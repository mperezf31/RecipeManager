package models;

import io.ebean.Finder;

import javax.persistence.Entity;

@Entity
public class Recipe extends ModelBase {

    public static final Finder<Long,Recipe> find=new Finder<>(Recipe.class);

    private String title;
    private String description;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Recipe findById(Long id) {
        return find.query().where().eq("id", id).findOne();
    }

}
