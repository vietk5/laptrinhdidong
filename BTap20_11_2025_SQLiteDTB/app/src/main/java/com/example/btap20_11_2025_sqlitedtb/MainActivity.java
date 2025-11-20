package com.example.btap20_11_2025_sqlitedtb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btap20_11_2025_sqlitedtb.DTBHandler.DatabaseHandler;
import com.example.btap20_11_2025_sqlitedtb.adapter.NotesAdapter;
import com.example.btap20_11_2025_sqlitedtb.models.NotesModel;

import java.time.ZoneId;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // khai bao
    DatabaseHandler databaseHandler;
    ListView listView;
    NotesAdapter adapter;
    ArrayList<NotesModel> arrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // anh xa id
        listView = (ListView) findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        adapter = new  NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);

        InitDatabaseSQLite();
//        clearAllNotes();
        databaseSQLite();
//        createDatabaseSQLite();
    }
    private void createDatabaseSQLite(){
        // them du lieu vao bang
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Note 1 content')");
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Note 2 content')");
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Note 3 content')");
    }
    private void InitDatabaseSQLite(){
        // khoi tao dtb
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
        // tao bang Note
        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }
    private void databaseSQLite() {
        arrayList.clear();
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new NotesModel(id, name));
        }
        cursor.close();      // đóng cursor cho sạch
        adapter.notifyDataSetChanged();
    }
    // tao menu trong app
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //bắt sự kiện cho menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAddNotes){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }
    private void clearAllNotes() {
        databaseHandler.QueryData("DELETE FROM Notes"); // xóa hết dữ liệu bảng
        arrayList.clear();
        adapter.notifyDataSetChanged();
    }
    private void DialogThem() {
        Dialog dialog = new Dialog( this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_note);

        // Ánh xạ trong dialog
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonAdd = dialog.findViewById(R.id.buttonEdit);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        // Bắt sự kiện cho nút thêm và hủy
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                if (name.equals("")) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên Notes", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHandler.QueryData(
                    "INSERT INTO Notes VALUES(null, '"+ name +"')" );
                    Toast.makeText( MainActivity.this,
                             "Đã thêm Notes",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    databaseSQLite();   // gọi hàm load lại dữ liệu
                }
            }
        });
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    // Hàm dialog cập nhật Notes
    public void DialogCapNhatNotes(String name, int id) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_notes);
        // Ánh xạ
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonEdit = dialog.findViewById(R.id.buttonEdit);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);
        editText.setText(name);
        // Bắt sự kiện
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                databaseHandler.QueryData("UPDATE Notes SET NameNotes = '" + name + "' WHERE Id = '" + id + "'"
            );
                Toast.makeText( MainActivity.this, "Đã cập nhật Notes thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                databaseSQLite(); // gọi hàm load lại dữ liệu
            }
        });
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    // hàm dialog xóa
    public void DialogXoaNotes(String name, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa Notes \"" + name + "\" này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHandler.QueryData(
                        "DELETE FROM Notes WHERE Id = '" + id + "'"
                );
                Toast.makeText(MainActivity.this,
                        "Đã xóa Notes \"" + name + "\" thành công",
                        Toast.LENGTH_SHORT).show();
                databaseSQLite();   // gọi hàm load lại dữ liệu
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();   // không xóa, đóng dialog
            }
        });
        builder.show();
    }
}
