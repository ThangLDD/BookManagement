package com.thangldd.bookmanagement.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.thangldd.bookmanagement.R;
import com.thangldd.bookmanagement.data.database.DatabaseHelper;
import com.thangldd.bookmanagement.data.model.Book;

import java.util.ArrayList;

public class FindBookActivity extends AppCompatActivity {

    private EditText editText;
    private Button buttonFind, buttonBackToMenu;
    private ListView listView;

    private static final String INTENT_TITLE = "intentTitle";
    private static final String INTENT_AUTHOR = "intentAuthor";
    private static final String INTENT_GENRE = "intentGenre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book);
        initView();
        listenUser();
    }

    private void listenUser() {
        buttonFind.setOnClickListener(view -> findBooks());
        buttonBackToMenu.setOnClickListener(view -> backToMenuActivity());
        listView.setOnItemClickListener((adapterView, view, i, l) -> startEditBookActivity(i));
    }

    private void startEditBookActivity(int i) {
        Intent intent = new Intent(this, EditBookActivity.class);
        Book book = getData().get(i);
        intent.putExtra(INTENT_TITLE, book.getTitle());
        intent.putExtra(INTENT_AUTHOR, book.getAuthor());
        intent.putExtra(INTENT_GENRE, book.getGenre());
        startActivity(intent);
    }

    private void backToMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void findBooks() {
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getData()));
    }

    private void initView() {
        editText = findViewById(R.id.edit_text_input_find);
        buttonFind = findViewById(R.id.button_find);
        buttonBackToMenu = findViewById(R.id.button_back_from_find);

        listView = findViewById(R.id.list_view_find_result);
    }

    private ArrayList<Book> getData() {
        String key = editText.getText().toString().trim();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<Book> booksByTitle = databaseHelper.getBooksByTitle(key);
        ArrayList<Book> books = databaseHelper.getBooksByGenre(key);
        books.addAll(booksByTitle);
        return books;
    }
}
