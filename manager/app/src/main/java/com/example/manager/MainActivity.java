package com.example.manager;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private EditText edtMessage;
    private Button btnSend;
    private TextView txtChat;

    private String role = "manager";
    private String customerId = "M001";

    {
        try {
            // ⚠️ Emulator dùng 10.0.2.2
            mSocket = IO.socket("http://10.0.2.2:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        txtChat = findViewById(R.id.txtChat);

        mSocket.connect();

        if (role.equals("customer")) {
            mSocket.emit("join_customer", customerId);
        } else {
            mSocket.emit("join_manager");
        }

        mSocket.on("new_message", onNewMessage);

        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String msg = edtMessage.getText().toString();
        if (msg.isEmpty()) return;

        JSONObject data = new JSONObject();
        try {
            data.put("customerId", customerId);
            data.put("sender", role);
            data.put("message", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (role.equals("customer")) {
            mSocket.emit("customer_message", data);

            txtChat.append("Me: " + msg + "\n"); // 👈 THÊM DÒNG NÀY
        } else {
            mSocket.emit("manager_message", data);

            txtChat.append("Me: " + msg + "\n");
        }

        edtMessage.setText("");
    }

    private Emitter.Listener onNewMessage = args -> {
        JSONObject data = (JSONObject) args[0];
        runOnUiThread(() -> {
            txtChat.append(data.toString() + "\n");
        });
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("new_message", onNewMessage);
    }
}
