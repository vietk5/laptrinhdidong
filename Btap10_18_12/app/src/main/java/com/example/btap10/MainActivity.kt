package com.example.btap10


import com.example.btap10.model.Task
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var dbHandler: DatabaseHelper
    lateinit var recyclerView: RecyclerView
    lateinit var taskAdapter: TaskAdapter
    var currentUserId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Khởi tạo Database
        dbHandler = DatabaseHelper(this)

        // 2. Lấy User ID từ LoginActivity
        currentUserId = intent.getIntExtra("USER_ID", -1)
        // --- CODE MỚI: Cập nhật tiêu đề theo tên người dùng ---
        val tvHeaderTitle = findViewById<TextView>(R.id.tvHeaderTitle)
        val userName = dbHandler.getUsername(currentUserId)
        tvHeaderTitle.text = "Xin chào, $userName!"
        // -----------------------------------------------------

        recyclerView = findViewById(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 3. Thiết lập RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Khởi tạo Adapter với danh sách rỗng ban đầu và định nghĩa hành động Xóa/Sửa
        taskAdapter = TaskAdapter(ArrayList(),
            onDeleteClick = { task ->
                deleteTask(task)
            },
            onUpdateClick = { task ->
                updateTaskStatus(task)
            }
        )
        recyclerView.adapter = taskAdapter

        // 4. Load dữ liệu từ SQLite lên
        loadTasks()

        // 5. Nút thêm công việc
        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            showDialogAddTask()
        }
    }

    // Hàm load lại dữ liệu
    private fun loadTasks() {
        val taskList = dbHandler.getAllTasks(currentUserId)
        taskAdapter.updateData(taskList) // Cập nhật dữ liệu vào Adapter
    }

    // Hàm hiển thị Dialog thêm mới
    private fun showDialogAddTask() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thêm công việc mới")

        val inflater = LayoutInflater.from(this)
        val dialogLayout = inflater.inflate(R.layout.dialog_add_task, null)
        val edtTitle = dialogLayout.findViewById<EditText>(R.id.edtTitle)
        val edtContent = dialogLayout.findViewById<EditText>(R.id.edtContent)

        builder.setView(dialogLayout)
        builder.setPositiveButton("Lưu") { dialog, i ->
            val title = edtTitle.text.toString()
            val content = edtContent.text.toString()

            if (title.isNotEmpty()) {
                val newTask = Task(title = title, content = content, userId = currentUserId, isCompleted = 0)
                dbHandler.addTask(newTask)
                loadTasks() // Load lại list sau khi thêm
            }
        }
        builder.setNegativeButton("Hủy", null)
        builder.show()
    }

    // Xử lý xóa Task
    private fun deleteTask(task: Task) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Xác nhận")
        builder.setMessage("Bạn có chắc muốn xóa '${task.title}' không?")
        builder.setPositiveButton("Xóa") { _, _ ->
            dbHandler.deleteTask(task.id)
            loadTasks()
        }
        builder.setNegativeButton("Không", null)
        builder.show()
    }

    // Xử lý cập nhật trạng thái (Xong/Chưa xong)
    private fun updateTaskStatus(task: Task) {
        dbHandler.updateTask(task)
    }
}