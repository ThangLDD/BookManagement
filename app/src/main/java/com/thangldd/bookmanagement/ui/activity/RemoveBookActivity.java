package com.thangldd.bookmanagement.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.thangldd.bookmanagement.R;
import com.thangldd.bookmanagement.data.database.DatabaseHelper;
import com.thangldd.bookmanagement.data.model.Book;

import java.util.ArrayList;

public class RemoveBookActivity extends AppCompatActivity {

    private EditText editText;
    private ListView listView;
    private Button buttonFind, buttonRemove, buttonBackMenu;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_book);
        matchLayout();
        listenUser();
    }

    private void listenUser() {
        buttonFind.setOnClickListener(view -> showData());
        buttonRemove.setOnClickListener(view -> removeBook());
        buttonBackMenu.setOnClickListener(view -> startMenuActivity());
        listView.setOnItemClickListener(new ItemClickListener());
    }

    private void showData() {
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getData()));
    }

    private ArrayList<Book> getData() {
        ArrayList<Book> books;
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        String keyTitle = editText.getText().toString().trim();
        books = databaseHelper.getBooksByTitle(keyTitle);
        return books;
    }

    private void startMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void removeBook() {
        Book rBook = getData().get(position);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.removeBook(rBook);
        Toast.makeText(this, "Xóa sách thành công", Toast.LENGTH_SHORT).show();
        listView.setVisibility(View.INVISIBLE);
    }

    private void matchLayout() {
        editText = findViewById(R.id.edit_text_input_remove);
        listView = findViewById(R.id.list_view_find_remove);
        buttonFind = findViewById(R.id.button_find_remove);
        buttonRemove = findViewById(R.id.button_remove_book_remove);
        buttonBackMenu = findViewById(R.id.button_back_from_remove);
    }

    private class ItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            position = i;
            Toast.makeText(RemoveBookActivity.this, "Bạn chọn " + getData().get(i).getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
