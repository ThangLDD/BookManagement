package com.thangldd.bookmanagement.data.model;

import androidx.annotation.NonNull;

public class Book {
    private final String title;
    private final String author;
    private final String genre;

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    @NonNull
    @Override
    public String toString() {
        return title + " - " + author;
    }
}
