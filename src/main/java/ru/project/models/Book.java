package ru.project.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;



public class Book {

    private int id;
    @NotEmpty(message = "У книги должно быть название")
    private String title;
    @NotEmpty(message = "У книги должен быть автор")
    private String author;

    @Max(value = 2022, message = "Книга еще не издана")
    private int yearOfCreation;

    public Book(String title, String author, int yearOfCreation) {
        this.title = title;
        this.author = author;
        this.yearOfCreation = yearOfCreation;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getYearOfCreation() {
        return yearOfCreation;
    }
    public void setYearOfCreation(int yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }
}
