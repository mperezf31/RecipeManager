package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Step extends ModelBase {

    @Constraints.Required(message = "El campo 'description' es requerido")
    private String description;

    @JsonBackReference
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
