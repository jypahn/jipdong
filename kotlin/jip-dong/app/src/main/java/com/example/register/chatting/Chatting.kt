package com.example.register.chatting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.R
import com.example.register.databinding.ActivityChattingBinding
import org.json.JSONArray
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
class Chatting : AppCompatActivity() {

    private lateinit var binding: ActivityChattingBinding
    private val messageAdapter = MessageAdapter()
    private val chatRoomId: Int by lazy { intent.getIntExtra("chatRoomId", -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler)

        val chatRoomName = intent.getStringExtra("chatRoomName")  // 채팅방 목록에서 방 이름 선택
        val textroom = findViewById<TextView>(R.id.text)
        textroom.text = chatRoomName

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            super.onBackPressed()

            finish()
        }

        binding.ButtonSend.setOnClickListener {
            val text = binding.EditTextChat.text.toString()

            if (uid != null && chatRoomId != -1) {
                chat(text, uid, chatRoomId)
            } else {
                Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
            binding.EditTextChat.setText("")
        }

        recyclerView.adapter = messageAdapter

        // 채팅 메시지를 주기적으로 받아와서 RecyclerView에 추가
        val handler = Handler()
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    getChatMessages()
                }
            }
        }, 0, 1000) // 1초마다 채팅 메시지 갱신
    }

    // PHP 스크립트를 호출하여 MySQL 서버로부터 채팅 메시지를 받아옵니다.
    private fun getChatMessages() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://capstone123.dothome.co.kr/getmessage.php?chat_id=$chatRoomId"
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // 응답 받은 메시지를 MessageAdapter에 추가합니다.
                messageAdapter.submitData(parseData(response))
            },
            Response.ErrorListener { error ->
                // 네트워크 오류 처리
                Toast.makeText(this, "네트워크 오류: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }

    private fun parseData(response: String): ArrayList<MessageData> {
        val data = ArrayList<MessageData>()
        val jsonArray = JSONArray(response)
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val senderId = jsonObject.getString("sender_id")
            val message = jsonObject.getString("message")
            val timestamp = jsonObject.getString("timestamp")
            val type = if (senderId == uid) "user" else "$senderId"
            data.add(MessageData(timestamp, message, type))
        }
        return data
    }

    private fun chat(text: String, uid: String, chatRoomId: Int) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://capstone123.dothome.co.kr/sendmessage.php"

        val request = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                // 채팅 메시지를 성공적으로 전송한 후에 RecyclerView에 추가
                val messageData = MessageData("전송중", text, "user")
                messageAdapter.addMessage(messageData)
            },
            Response.ErrorListener { error ->
                // 네트워크 오류 처리
                Toast.makeText(this, "네트워크 오류: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["chat_id"] = chatRoomId.toString()
                params["sender_id"] = uid
                params["message"] = text
                return params
            }
        }

        queue.add(request)
    }




}