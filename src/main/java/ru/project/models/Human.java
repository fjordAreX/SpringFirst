package ru.project.models;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Human {

    private int id;
    @NotEmpty(message = "Имя не должно быть пустым")
    private String name;

    @Min(value = 1890, message = "Год рождения должен быть больше, чем 1890")
    private int yearOfBirth;

    public Human(String name, int yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public Human() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
