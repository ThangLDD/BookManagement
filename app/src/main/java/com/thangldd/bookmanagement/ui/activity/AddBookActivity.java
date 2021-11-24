package com.thangldd.bookmanagement.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.thangldd.bookmanagement.R;
import com.thangldd.bookmanagement.data.database.DatabaseHelper;
import com.thangldd.bookmanagement.data.model.Book;

import java.util.Objects;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextAuthor;
    private Spinner spinnerGenre;
    private Button buttonAdd, buttonBackMenu;

    private final String[] arrGenre = {"Văn học", "Công nghệ thông tin", "Kinh tế"};
    private String genreSpinner = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initView();
        listenUser();
    }

    private void initView() {
        editTextTitle = findViewById(R.id.edit_text_title_add);
        editTextAuthor = findViewById(R.id.edit_text_author_add);
        spinnerGenre = findViewById(R.id.spinner_genre_add);
        buttonAdd = findViewById(R.id.button_add_book_add);
        buttonBackMenu = findViewById(R.id.button_back_from_add);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrGenre);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerGenre.setAdapter(arrayAdapter);
    }

    private void listenUser() {
        spinnerGenre.setOnItemSelectedListener(new ItemSelectedListener());
        buttonAdd.setOnClickListener(view -> addBook());
        buttonBackMenu.setOnClickListener(view -> backMenuActivity());
    }

    private void backMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void addBook() {
        String title = editTextTitle.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();
        if (Objects.equals(genreSpinner, "")) {
            Toast.makeText(this, "Hãy chọn thể loại sách", Toast.LENGTH_SHORT).show();
        } else {
            String genre = genreSpinner;

            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.addBook(new Book(title, author, genre));
            Toast.makeText(this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private class ItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            genreSpinner = arrGenre[i].trim();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            genreSpinner = "";
        }
    }
}
