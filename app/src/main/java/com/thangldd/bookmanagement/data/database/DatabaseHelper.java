package com.thangldd.bookmanagement.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.thangldd.bookmanagement.data.model.Book;
import com.thangldd.bookmanagement.data.model.User;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BookManagement.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "User";
    private static final String TABLE_BOOK = "Book";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String GENRE = "genre";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(queryCreateTableUser());
        sqLiteDatabase.execSQL(queryCreateTableBook());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(queryDropTable(TABLE_USER));
        sqLiteDatabase.execSQL(queryDropTable(TABLE_BOOK));
        onCreate(sqLiteDatabase);
    }

    private String queryCreateTableBook() {
        return "CREATE TABLE " + DatabaseHelper.TABLE_BOOK + " (" +
                TITLE + " VARCHAR (255) NOT NULL, " +
                AUTHOR + " VARCHAR (255) NOT NULL, " +
                GENRE + " VARCHAR (255) NOT NULL" +
                ")";
    }

    private String queryCreateTableUser() {
        return "CREATE TABLE " + DatabaseHelper.TABLE_USER + " (" +
                USERNAME + " VARCHAR (255) NOT NULL, " +
                PASSWORD + " VARCHAR (255) NOT NULL" +
                ")";
    }

    private String queryDropTable(String name) {
        return "DROP TABLE IF EXISTS " + name;
    }

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_BOOK;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String title = cursor.getString(0);
            String author = cursor.getString(1);
            String genre = cursor.getString(2);

            books.add(new Book(title, author, genre));
            cursor.moveToNext();
        }

        cursor.close();
        return books;
    }

    public ArrayList<Book> getBooksByTitle(String title) {
        ArrayList<Book> books = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] selectionArgs = new String[]{title};
        Cursor cursor = sqLiteDatabase.query(TABLE_BOOK, null, TITLE + " = ?", selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String mTitle = cursor.getString(0);
                String mAuthor = cursor.getString(1);
                String mGenre = cursor.getString(2);

                books.add(new Book(mTitle, mAuthor, mGenre));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return books;
    }

    public ArrayList<Book> getBooksByGenre(String genre) {
        ArrayList<Book> books = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] selectionArgs = new String[]{genre};
        Cursor cursor = sqLiteDatabase.query(TABLE_BOOK, null, GENRE + " = ?", selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String mTitle = cursor.getString(0);
                String mAuthor = cursor.getString(1);
                String mGenre = cursor.getString(2);

                books.add(new Book(mTitle, mAuthor, mGenre));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return books;
    }

    public void addBook(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, book.getTitle());
        contentValues.put(AUTHOR, book.getAuthor());
        contentValues.put(GENRE, book.getGenre());

        sqLiteDatabase.insert(TABLE_BOOK, null, contentValues);
        sqLiteDatabase.close();
    }

    public void editBook(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String[] selectionArgs = new String[]{book.getTitle()};

        contentValues.put(AUTHOR, book.getAuthor());
        contentValues.put(GENRE, book.getGenre());

        sqLiteDatabase.update(TABLE_BOOK, contentValues, TITLE + " = ?", selectionArgs);
        sqLiteDatabase.close();
    }

    public void removeBook(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String[] selectionArgs = new String[]{book.getTitle()};
        sqLiteDatabase.delete(TABLE_BOOK, TITLE + " = ?", selectionArgs);
        sqLiteDatabase.close();
    }

    public boolean validateUser(String username, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selection = USERNAME + " = ?" + " AND " + PASSWORD + " = ?";
        String[] selectionArgs = new String[]{username, password};
        Cursor cursor = sqLiteDatabase.query(TABLE_USER, null, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        return cursorCount > 0;
    }

    public void addUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USERNAME, user.getUsername());
        contentValues.put(PASSWORD, user.getPassword());

        sqLiteDatabase.insert(TABLE_USER, null, contentValues);
        sqLiteDatabase.close();
    }
}

