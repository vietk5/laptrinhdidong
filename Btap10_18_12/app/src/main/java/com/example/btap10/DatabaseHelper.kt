package com.example.btap10

// File: DatabaseHelper.kt
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import  com.example.btap10.model.User;
import  com.example.btap10.model.Task;


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "ToDoListDB"
        private const val DB_VERSION = 1

        // Tên bảng và cột
        const val TABLE_USER = "User"
        const val TABLE_TASK = "Task"
        const val COL_ID = "id"
        // User columns
        const val COL_USERNAME = "username"
        const val COL_PASSWORD = "password"
        // Task columns
        const val COL_TITLE = "title"
        const val COL_CONTENT = "content"
        const val COL_COMPLETED = "isCompleted"
        const val COL_USER_ID = "userId"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Tạo bảng User
        val createUserTable = ("CREATE TABLE $TABLE_USER (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_USERNAME TEXT," +
                "$COL_PASSWORD TEXT)")

        // Tạo bảng Task có liên kết với User
        val createTaskTable = ("CREATE TABLE $TABLE_TASK (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_TITLE TEXT," +
                "$COL_CONTENT TEXT," +
                "$COL_COMPLETED INTEGER," +
                "$COL_USER_ID INTEGER," +
                "FOREIGN KEY($COL_USER_ID) REFERENCES $TABLE_USER($COL_ID))")

        db?.execSQL(createUserTable)
        db?.execSQL(createTaskTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        onCreate(db)
    }
    // Đăng ký tài khoản
    fun registerUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME, user.username)
        values.put(COL_PASSWORD, user.password)
        val result = db.insert(TABLE_USER, null, values)
        db.close()
        return result
    }

    // Kiểm tra đăng nhập
    fun checkLogin(username: String, pass: String): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COL_ID FROM $TABLE_USER WHERE $COL_USERNAME=? AND $COL_PASSWORD=?", arrayOf(username, pass))
        var userId = -1
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0)
        }
        cursor.close()
        return userId // Trả về -1 nếu sai, trả về ID nếu đúng
    }

    // Thêm công việc mới
    fun addTask(task: Task): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_TITLE, task.title)
        values.put(COL_CONTENT, task.content)
        values.put(COL_COMPLETED, 0)
        values.put(COL_USER_ID, task.userId)

        val result = db.insert(TABLE_TASK, null, values)
        db.close()
        return result
    }

    // Lấy danh sách công việc của User cụ thể
    fun getAllTasks(userId: Int): ArrayList<Task> {
        val taskList = ArrayList<Task>()
        val selectQuery = "SELECT * FROM $TABLE_TASK WHERE $COL_USER_ID = $userId"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)),
                    content = cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTENT)),
                    isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(COL_COMPLETED)),
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID))
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return taskList
    }

    // Cập nhật công việc
    fun updateTask(task: Task): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_TITLE, task.title)
        values.put(COL_CONTENT, task.content)
        values.put(COL_COMPLETED, task.isCompleted)

        return db.update(TABLE_TASK, values, "$COL_ID=?", arrayOf(task.id.toString()))
    }

    // Xóa công việc
    fun deleteTask(taskId: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_TASK, "$COL_ID=?", arrayOf(taskId.toString()))
    }
    // Thêm vào trong class DatabaseHelper
    fun getUsername(userId: Int): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COL_USERNAME FROM $TABLE_USER WHERE $COL_ID = ?", arrayOf(userId.toString()))
        var name = "Người dùng"
        if (cursor.moveToFirst()) {
            name = cursor.getString(0)
        }
        cursor.close()
        return name
    }
}