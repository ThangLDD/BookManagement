package com.thangldd.bookmanagement.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.thangldd.bookmanagement.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonAddBook, buttonEditBook, buttonRemoveBook, buttonListBook, buttonFindBook, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        matchLayout();
        listenUser();
    }

    private void listenUser() {
        buttonAddBook.setOnClickListener(this);
        buttonEditBook.setOnClickListener(this);
        buttonRemoveBook.setOnClickListener(this);
        buttonListBook.setOnClickListener(this);
        buttonFindBook.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
    }

    private void matchLayout() {
        buttonAddBook = findViewById(R.id.button_add_book);
        buttonEditBook = findViewById(R.id.button_edit_book);
        buttonRemoveBook = findViewById(R.id.button_remove_book);
        buttonFindBook = findViewById(R.id.button_find_book);
        buttonLogout = findViewById(R.id.button_logout);
        buttonListBook = findViewById(R.id.button_list_book);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.button_add_book:
                intent = new Intent(this, AddBookActivity.class);
                startActivity(intent);
                break;
            case R.id.button_edit_book:
                intent = new Intent(this, EditBookActivity.class);
                startActivity(intent);
                break;
            case R.id.button_remove_book:
                intent = new Intent(this, RemoveBookActivity.class);
                startActivity(intent);
                break;
            case R.id.button_list_book:
                intent = new Intent(this, ListBookActivity.class);
                startActivity(intent);
                break;
            case R.id.button_find_book:
                intent = new Intent(this, FindBookActivity.class);
                startActivity(intent);
                break;
            case R.id.button_logout:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
