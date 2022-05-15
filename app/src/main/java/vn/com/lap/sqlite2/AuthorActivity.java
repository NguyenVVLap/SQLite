package vn.com.lap.sqlite2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AuthorActivity extends AppCompatActivity {
    EditText et_Id, et_Name, et_Address, et_Email;
    Button btn_Save, btn_Select, btn_Update, btn_Delete;
    ListView listView;

    MyArrayAdapter myArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        DBHelper db = new DBHelper(this);

        et_Id = findViewById(R.id.editText_Id);
        et_Name = findViewById(R.id.editText_Name);
        et_Address = findViewById(R.id.editText_Address);
        et_Email = findViewById(R.id.editText_Email);

        listView = findViewById(R.id.listView);

        btn_Select = findViewById(R.id.button_Select);
        btn_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_Id.getText().toString();
                if (id.equals("")) {
                    ArrayList<Author> authors = db.getAllAuthors();
                    if (!authors.isEmpty()) {
                        myArrayAdapter = new MyArrayAdapter(AuthorActivity.this, R.layout.my_item_layout, authors);
                        listView.setAdapter(myArrayAdapter);
                    }
                } else {
                    int id_author = Integer.parseInt(id);
                    Author author = db.getAuthorById(id_author);
                    ArrayList<Author> arrayList = new ArrayList<>();
                    arrayList.add(author);
                    myArrayAdapter = new MyArrayAdapter(AuthorActivity.this, R.layout.my_item_layout, arrayList);
                    listView.setAdapter(myArrayAdapter);
                }
            }
        });

        btn_Save = findViewById(R.id.button_Save);
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(et_Id.getText().toString());
                String name = et_Name.getText().toString();
                String address = et_Address.getText().toString();
                String email = et_Email.getText().toString();

                if (db.insertAuthor(new Author(id, name, address, email)) > 0) {
                    Toast.makeText(AuthorActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AuthorActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Update = findViewById(R.id.button_Update);
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(et_Id.getText().toString());
                String name = et_Name.getText().toString();
                String address = et_Address.getText().toString();
                String email = et_Email.getText().toString();
                if (db.updateAuthor(new Author(id, name, address, email)) > 0) {
                    Author author = new Author(id, name, address, email);
                    ArrayList<Author> arrayList = new ArrayList<>();
                    arrayList.add(author);
                    myArrayAdapter = new MyArrayAdapter(AuthorActivity.this, R.layout.my_item_layout, arrayList);
                    listView.setAdapter(myArrayAdapter);
                    Toast.makeText(AuthorActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AuthorActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Delete = findViewById(R.id.button_Delete);
        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_Id.getText().toString();
                if (id.equals("")) {
                    Toast.makeText(AuthorActivity.this, "Bạn chưa nhập id", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.deleteAuthor(id) > 0) {
                        ArrayList<Author> authors = db.getAllAuthors();
                        if (!authors.isEmpty()) {
                            myArrayAdapter = new MyArrayAdapter(AuthorActivity.this, R.layout.my_item_layout, authors);
                            listView.setAdapter(myArrayAdapter);
                        }
                        Toast.makeText(AuthorActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AuthorActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<Author> authors = myArrayAdapter.getArrayList();
                Author author = authors.get(i);
                et_Id.setText(author.getId() + "");
                et_Name.setText(author.getName());
                et_Address.setText(author.getAddress());
                et_Email.setText(author.getEmail());
            }
        });
    }
}