package com.example.btap10

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btap10.model.User

class LoginActivity : AppCompatActivity() {

    lateinit var dbHandler: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Đảm bảo trùng tên với file XML giao diện

        // 1. Khởi tạo Database
        dbHandler = DatabaseHelper(this)

        // 2. Ánh xạ View từ XML
        val edtUser = findViewById<EditText>(R.id.edtUser)
        val edtPass = findViewById<EditText>(R.id.edtPass)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // --- XỬ LÝ SỰ KIỆN ĐĂNG KÝ ---
        btnRegister.setOnClickListener {
            val userText = edtUser.text.toString()
            val passText = edtPass.text.toString()

            // Kiểm tra rỗng (Validation)
            if (userText.isEmpty() || passText.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            } else {
                // Tạo đối tượng User mới (Dùng data class User đã tạo)
                val newUser = User(username = userText, password = passText)

                // Gọi hàm thêm vào SQLite
                val result = dbHandler.registerUser(newUser)

                if (result > -1) {
                    Toast.makeText(this, "Đăng ký thành công! Hãy đăng nhập.", Toast.LENGTH_LONG).show()
                    // Xóa trắng ô nhập liệu để người dùng nhập lại đăng nhập
                    edtUser.setText("")
                    edtPass.setText("")
                } else {
                    Toast.makeText(this, "Đăng ký thất bại (Tên có thể đã tồn tại)", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // --- XỬ LÝ SỰ KIỆN ĐĂNG NHẬP ---
        btnLogin.setOnClickListener {
            val userText = edtUser.text.toString()
            val passText = edtPass.text.toString()

            if (userText.isEmpty() || passText.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên và mật khẩu!", Toast.LENGTH_SHORT).show()
            } else {
                // Kiểm tra trong Database
                val userId = dbHandler.checkLogin(userText, passText)

                if (userId != -1) {
                    // Đăng nhập đúng: Chuyển sang MainActivity
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    // Gửi ID người dùng sang MainActivity để load đúng công việc của người đó
                    intent.putExtra("USER_ID", userId)
                    startActivity(intent)
                    finish() // Đóng LoginActivity để người dùng không back lại được
                } else {
                    // Đăng nhập sai
                    Toast.makeText(this, "Sai tên tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}