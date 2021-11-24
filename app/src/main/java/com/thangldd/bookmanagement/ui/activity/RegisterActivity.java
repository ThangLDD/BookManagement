package com.thangldd.bookmanagement.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.thangldd.bookmanagement.R;
import com.thangldd.bookmanagement.data.database.DatabaseHelper;
import com.thangldd.bookmanagement.data.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        matchLayout();
        listenUser();
    }

    private void listenUser() {
        buttonRegister.setOnClickListener(view -> doRegister());
    }

    private void doRegister() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.addUser(new User(username, password));
        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void matchLayout() {
        editTextUsername = findViewById(R.id.edit_text_username_register);
        editTextPassword = findViewById(R.id.edit_text_password_register);
        buttonRegister = findViewById(R.id.button_register);
    }
}
