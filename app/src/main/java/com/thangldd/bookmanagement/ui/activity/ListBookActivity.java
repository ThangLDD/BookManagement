package com.thangldd.bookmanagement.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.thangldd.bookmanagement.R;
import com.thangldd.bookmanagement.data.database.DatabaseHelper;
import com.thangldd.bookmanagement.data.model.Book;

import java.util.ArrayList;

public class ListBookActivity extends AppCompatActivity {

    private Button button;
    private Spinner spinner;
    private ListView listView;

    private final String[] arrGenre = {"Tất cả", "Văn học", "Công nghệ thông tin", "Kinh tế"};

    private static final String INTENT_TITLE = "intentTitle";
    private static final String INTENT_AUTHOR = "intentAuthor";
    private static final String INTENT_GENRE = "intentGenre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);
        initView();
        listenUser();
    }

    private void listenUser() {
        spinner.setOnItemSelectedListener(new ItemSelectedListener());
        button.setOnClickListener(view -> backToMenuActivity());
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

    private void initView() {
        spinner = findViewById(R.id.spinner_list_book);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrGenre));

        listView = findViewById(R.id.list_view_list_book);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getData()));

        button = findViewById(R.id.button_back_from_list);
    }

    private ArrayList<Book> getData() {
        ArrayList<Book> books;
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        books = databaseHelper.getAllBooks();
        return books;
    }

    private class ItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            getSelectedData(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private void getSelectedData(int i) {
        ArrayList<Book> books = new ArrayList<>();
        ArrayAdapter<Book> arrayAdapter;
        if (i == 0) {
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getData());
        } else {
            for (Book book : getData()) {
                if (book.getGenre().equals(arrGenre[i])) {
                    books.add(book);
                }
            }
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, books);
        }
        listView.setAdapter(arrayAdapter);
    }
}
