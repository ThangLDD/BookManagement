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

public class EditBookActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextAuthor;
    private Spinner spinner;
    private Button buttonEdit, buttonBackMenu;

    private static final String INTENT_TITLE = "intentTitle";
    private static final String INTENT_AUTHOR = "intentAuthor";
    private static final String INTENT_GENRE = "intentGenre";

    private final String[] arrGenre = {"Văn học", "Công nghệ thông tin", "Kinh tế"};
    private String genreSpinner = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        initView();
        getData();
        listenUser();
    }

    private void listenUser() {
        buttonEdit.setOnClickListener(view -> editBook());
        buttonBackMenu.setOnClickListener(view -> backMenuActivity());
        spinner.setOnItemSelectedListener(new ItemSelectedListener());
    }

    private void backMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void editBook() {
        String title = editTextTitle.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();
        if (Objects.equals(genreSpinner, "")) {
            Toast.makeText(this, "Hãy chọn thể loại sách", Toast.LENGTH_SHORT).show();
        } else {
            String genre = genreSpinner;

            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.editBook(new Book(title, author, genre));
            Toast.makeText(this, "Sửa sách thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void getData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(INTENT_TITLE);
        String author = intent.getStringExtra(INTENT_AUTHOR);
        genreSpinner = intent.getStringExtra(INTENT_GENRE);

        editTextTitle.setText(title);
        editTextTitle.setEnabled(false);
        editTextAuthor.setText(author);
    }

    private void initView() {
        editTextTitle = findViewById(R.id.edit_text_title_edit);
        editTextAuthor = findViewById(R.id.edit_text_author_edit);
        spinner = findViewById(R.id.spinner_genre_edit);
        buttonEdit = findViewById(R.id.button_edit_book_edit);
        buttonBackMenu = findViewById(R.id.button_back_from_edit);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrGenre);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(arrayAdapter);
    }

    private class ItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            genreSpinner = arrGenre[i].trim();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
