package vn.com.lap.sqlite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Authors(" +
                "id integer primary key" +
                ", name text" +
                ", address text" +
                ", email text)");
        sqLiteDatabase.execSQL("create table Books(" +
                "id_book integer primary key" +
                ", title text" +
                ", id_author integer not null " +
                "constraint id_author references Authors(id)" +
                " on delete cascade on update cascade)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Authors");
        sqLiteDatabase.execSQL("drop table if exists Books");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    public ArrayList<Author> getAllAuthors() {
        ArrayList<Author> authors = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Authors", null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                authors.add(new Author(cursor.getInt(0)
                , cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return authors;
    }

    public Author getAuthorById(int id) {
        Author author = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Authors where id = " + id, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                author = new Author(cursor.getInt(0)
                        , cursor.getString(1), cursor.getString(2), cursor.getString(3));
            }
        }
        cursor.close();
        db.close();
        return author;
    }

    public int insertAuthor(Author author) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", author.getId());
        contentValues.put("name", author.getName());
        contentValues.put("address", author.getAddress());
        contentValues.put("email", author.getEmail());

        int re = (int) db.insert("Authors", null, contentValues);
        return re;
    }

    public int updateAuthor(Author newAuthor) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", newAuthor.getId());
        contentValues.put("name", newAuthor.getName());
        contentValues.put("address", newAuthor.getAddress());
        contentValues.put("email", newAuthor.getEmail());

        int re = db.update("Authors", contentValues, "id=?", new String[]{newAuthor.getId() + ""});
        return re;
    }

    public int deleteAuthor(String id) {
        SQLiteDatabase db = getReadableDatabase();
        int re = db.delete("Authors", "id=?", new String[] {id});
        return re;
    }

    public List<Book> getAllBook() {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Books", null);
        if (cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                books.add(new Book(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return books;
    }

    public Book getBookById(int id) {
        Book book = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Books where id_book = " + id, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                book = new Book(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            }
        }
        cursor.close();
        db.close();
        return book;
    }

    public int insertBook(Book book) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_book", book.getId_book());
        contentValues.put("title", book.getTitle());
        contentValues.put("id_author", book.getId_author());

        int re = (int) db.insert("Books", null, contentValues);
        return re;
    }


    public int updateBook(Book newBook) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_book", newBook.getId_book());
        contentValues.put("title", newBook.getTitle());
        contentValues.put("id_author", newBook.getId_author());

        int re = db.update("Books", contentValues
                , "id_book=?", new String[] {newBook.getId_book() + ""});
        return re;
    }

    public int deleteBook(String id) {
        SQLiteDatabase db = getReadableDatabase();
        int re = db.delete("Books", "id_book=?", new String[]{id});
        return re;
    }
}
