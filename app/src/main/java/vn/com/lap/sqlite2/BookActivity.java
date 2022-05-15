package vn.com.lap.sqlite2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    EditText et_IdBook, et_Title;
    Button btn_Save, btn_Select, btn_Update, btn_Delete;
    GridView gridView;
    Spinner spinner;
    List<String> list_IdAuthor = new ArrayList<>();
    List<String> book_item = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        DBHelper db = new DBHelper(BookActivity.this);

        et_IdBook = findViewById(R.id.editText_IdBook);
        et_Title = findViewById(R.id.editText_Title);

        spinner = findViewById(R.id.spinner_IdAuthor);
        ArrayList<Author> tempAuthors = db.getAllAuthors();
        for (Author author : tempAuthors) {
            list_IdAuthor.add(author.getId() + "");
        }
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(BookActivity.this, android.R.layout.simple_list_item_1, list_IdAuthor);
        spinner.setAdapter(myArrayAdapter);

        gridView = findViewById(R.id.gridView);

        btn_Select = findViewById(R.id.button_Select);
        btn_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_IdBook.getText().toString();
                book_item = new ArrayList<>();
                if (id.equals("")) {
                    List<Book> books = db.getAllBook();
                    if (!books.isEmpty()) {
                        for (Book book: books) {
                            book_item.add(book.getId_book() + "");
                            book_item.add(book.getTitle());
                            book_item.add(book.getId_author() + "");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BookActivity.this
                            , android.R.layout.simple_list_item_1, book_item);
                        gridView.setAdapter(adapter);
                    }
                } else {
                    int id_book = Integer.parseInt(id);
                    Book book = db.getBookById(id_book);
                    if (book != null) {
                        book_item.add(book.getId_book() + "");
                        book_item.add(book.getTitle());
                        book_item.add(book.getId_author() + "");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BookActivity.this
                                , android.R.layout.simple_list_item_1, book_item);
                        gridView.setAdapter(adapter);
                    } else {
                        Toast.makeText(BookActivity.this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btn_Save = findViewById(R.id.button_Save);
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_book = Integer.parseInt(et_IdBook.getText().toString());
                String title = et_Title.getText().toString();
                int id_author = Integer.parseInt(spinner.getSelectedItem().toString());

                if (db.insertBook(new Book(id_book, title, id_author)) > 0) {
                    Toast.makeText(BookActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Update = findViewById(R.id.button_Update);
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_book = Integer.parseInt(et_IdBook.getText().toString());
                String title = et_Title.getText().toString();
                int id_author = Integer.parseInt(spinner.getSelectedItem().toString());

                Book book = new Book(id_book, title, id_author);

                if (db.updateBook(book) > 0) {
                    book_item = new ArrayList<>();
                    book_item.add(book.getId_book() + "");
                    book_item.add(book.getTitle());
                    book_item.add(book.getId_author() + "");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(BookActivity.this
                            , android.R.layout.simple_list_item_1, book_item);
                    gridView.setAdapter(adapter);
                    Toast.makeText(BookActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Delete = findViewById(R.id.button_Delete);
        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_IdBook.getText().toString();
                if (db.deleteBook(id) > 0) {
                    List<Book> books = db.getAllBook();
                    if (!books.isEmpty()) {
                        book_item = new ArrayList<>();
                        for (Book book: books) {
                            book_item.add(book.getId_book() + "");
                            book_item.add(book.getTitle());
                            book_item.add(book.getId_author() + "");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BookActivity.this
                                , android.R.layout.simple_list_item_1, book_item);
                        gridView.setAdapter(adapter);
                    }
                }
            }
        });
    }
}