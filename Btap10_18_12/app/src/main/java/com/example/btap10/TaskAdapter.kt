package com.example.btap10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import  com.example.btap10.model.User;
import  com.example.btap10.model.Task;


// TaskAdapter nhận vào danh sách task và 2 hành động (callback) để xử lý xóa/cập nhật
class TaskAdapter(
    private var taskList: ArrayList<Task>,
    private val onDeleteClick: (Task) -> Unit,
    private val onUpdateClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder: Ánh xạ các view trong item_task.xml
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val cbCompleted: CheckBox = itemView.findViewById(R.id.cbCompleted)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    // Tạo view cho từng dòng
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    // Gán dữ liệu vào view
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]

        holder.tvTitle.text = task.title
        holder.tvContent.text = task.content

        // Kiểm tra trạng thái hoàn thành (1 là true, 0 là false)
        // Lưu ý: Cần gỡ listener trước khi set trạng thái để tránh vòng lặp vô tận
        holder.cbCompleted.setOnCheckedChangeListener(null)
        holder.cbCompleted.isChecked = (task.isCompleted == 1)

        // Xử lý sự kiện khi bấm nút Xóa
        holder.btnDelete.setOnClickListener {
            onDeleteClick(task) // Gọi về MainActivity để xóa trong DB
        }

        // Xử lý sự kiện khi tích vào CheckBox (Sửa trạng thái)
        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = if (isChecked) 1 else 0
            onUpdateClick(task) // Gọi về MainActivity để update DB
        }
    }

    // Trả về số lượng item
    override fun getItemCount(): Int {
        return taskList.size
    }

    // Hàm cập nhật lại danh sách dữ liệu mới
    fun updateData(newTasks: ArrayList<Task>) {
        taskList = newTasks
        notifyDataSetChanged() // Làm mới giao diện
    }
}