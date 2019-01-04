package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Step extends ModelBase {

    private String description;

    @ManyToOne
    public Recipe recipe;

    Step(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
