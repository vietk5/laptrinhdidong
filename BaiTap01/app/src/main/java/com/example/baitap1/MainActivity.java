package com.example.baitap1;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    // khai báo biến
    TextView txtKQ;
    EditText edtNhapMang, edtNhapChuoi;
    Button btnXuLiMang, btnXuLiChuoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ẩn thanh tiêu đề
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // ánh xạ id
        edtNhapMang = findViewById(R.id.edtNhapMang);
        edtNhapChuoi = findViewById(R.id.edtNhapChuoi);
        btnXuLiChuoi = findViewById(R.id.btnXuLiChuoi);
        btnXuLiMang = findViewById(R.id.btnXuLiMang);
        txtKQ = findViewById(R.id.txtKQ);

        // btnXuLiChuoi
        btnXuLiMang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy dữ liệu vào từ edtNhapMang
                String mangInput = edtNhapMang.getText().toString();
                XuLiMangVaInLog(mangInput);
            }
        });
        btnXuLiChuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chuoiInput = edtNhapChuoi.getText().toString();
                XuLiChuoi(chuoiInput);
            }
        });

    }
    private  void XuLiMangVaInLog(String mangInput){
        if (mangInput.isEmpty() || mangInput == null ){
            Toast.makeText(this, "Vui lòng nhập mảng số nguyên", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] mang = mangInput.split(" ");
        ArrayList<Integer> numberList = new ArrayList<>();
        HashSet<Integer> chanList = new HashSet<>();
        HashSet<Integer> leList = new HashSet<>();

        for (String s : mang ){
            try {
                int number = Integer.parseInt(s);
                numberList.add(number);
                if (number % 2 == 0 ) {
                    chanList.add(number);
                } else {
                    leList.add(number);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Lỗi nhập liệu: '" + s + "' không phải là số.");
                Toast.makeText(this, "'" + s + "' không phải là số!", Toast.LENGTH_SHORT).show();
                return;
                }
            }
        Log.d(TAG, "===== YÊU CẦU 4 =====");
        Log.d(TAG, "Mảng gốc: " + numberList.toString());
        Log.d(TAG, "Các số CHẴN: " + chanList.toString());
        Log.d(TAG, "Các số LẺ: " + leList.toString());
        Toast.makeText(this, "Đã xử lý mảng! Vui lòng kiểm tra Logcat.", Toast.LENGTH_LONG).show();
        }
        private  void XuLiChuoi(String chuoiInput){
            if (chuoiInput.isEmpty() || chuoiInput == null ){
                Toast.makeText(this, "Vui lòng nhập chuỗi", Toast.LENGTH_SHORT).show();
                return;
            }
            chuoiInput = chuoiInput.toUpperCase();
            String[] mangChuoi = chuoiInput.split("\\s+");
            ArrayList<String> wordList = new ArrayList<>(Arrays.asList(mangChuoi));

            Collections.reverse(wordList);

            StringBuilder reversedString = new StringBuilder();
            for (String word : wordList) {
                reversedString.append(word).append(" ");
            }

            String ketQua = reversedString.toString().trim();

            txtKQ.setText("Kết quả: " + ketQua);

            Toast.makeText(this, "Chuỗi đảo ngược: " + ketQua, Toast.LENGTH_LONG).show();

            Log.d(TAG, "===== YÊU CẦU 5 =====");
            Log.d(TAG, "Chuỗi gốc: " + chuoiInput);
            Log.d(TAG, "Kết quả: " + ketQua);
        }
    }
