package com.example.btap10.model

data class Task(
    var id: Int = 0,
    var title: String,
    var content: String,
    var isCompleted: Int = 0, // 0: chưa xong, 1: xong
    var userId: Int // Khóa ngoại liên kết với User
)