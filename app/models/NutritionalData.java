package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Entity
public class NutritionalData extends ModelBase {

    private String calories;
    private String protein;
    private String carbohydrates;
    private String fat;

    @JsonBackReference
    @OneToOne(mappedBy = "nutritionalData")
    private Recipe recipe;

    NutritionalData(String calories, String protein, String fat, String carbohydrates) {
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
    }

    @JsonIgnore
    @Override
    public Long getId() {
        return super.getId();
    }

    @JsonIgnore
    @Override
    public Timestamp getWhenCreated() {
        return super.getWhenCreated();
    }

    public String getCalories() {
        return calories;
    }

    public String getProtein() {
        return protein;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public String getFat() {
        return fat;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }
}
