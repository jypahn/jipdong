package com.example.register.FindRoommate

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.R
import com.example.register.chatting.Chatting
import com.example.register.chatting.ChattlingList2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RoommateDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roommate_detail)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            val intent = Intent(this, Roommate::class.java)
            startActivity(intent)
            finish()
        }


        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val name = findViewById<TextView>(R.id.name)
        val location = findViewById<TextView>(R.id.textViewAuthor)
        val period = findViewById<TextView>(R.id.textViewDate)
        val content = findViewById<TextView>(R.id.textViewContent)
        val price = findViewById<TextView>(R.id.price)
        val roommateid = intent.getStringExtra("roommateid")
        val id = intent.getStringExtra("uid")

        val phpUrl = "http://capstone123.dothome.co.kr/roommatedetail.php"

        // 네트워크 요청 및 응답 처리
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // URL 생성
                val url = URL("$phpUrl?id=$roommateid")

                // HTTPURLConnection 생성 및 설정
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000

                // 응답 코드 확인
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    // 응답 데이터 읽기
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }

                    withContext(Dispatchers.Main) {
                        val jsonArray = JSONArray(response.toString())
                        if (jsonArray.length() > 0) {
                            val jsonObject = jsonArray.getJSONObject(0)
                            val id = jsonObject.getString("id")
                            val location1 = jsonObject.getString("location")
                            val start_date = jsonObject.getString("start_date")
                            val end_date = jsonObject.getString("end_date")
                            val content1 = jsonObject.getString("content")
                            val pricestarttext = jsonObject.getString("pricestarttext")
                            val priceendtext = jsonObject.getString("priceendtext")
                            val uid = jsonObject.getString("uid")
                            val writedate = jsonObject.getString("writedate")
                            val url = "http://capstone123.dothome.co.kr/getusername.php?uid=$uid"
                            val username = FetchUsernameTask().execute(url).get()

                            name.text = username
                            location.text = "희망지역: $location1"
                            period.text = "희망기간: $start_date ~ $end_date"
                            price.text = "희망금액 : $pricestarttext 만원 ~ $priceendtext 만원"
                            content.text = "\n$content1"
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val chatbutton = findViewById<Button>(R.id.chatbutton)

        chatbutton.setOnClickListener {
            val user2_id = id

            val url2 = "http://capstone123.dothome.co.kr/getusername.php?uid=$user2_id"
            val username = FetchUsernameTask().execute(url2).get()

            // 서버 URL 및 PHP 파일 경로 설정
            val url = "http://capstone123.dothome.co.kr/createchatroom.php"

            // Volley 라이브러리를 사용하여 서버에 요청 보내기
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> { response ->
                    if (response == "이미 생성된 채팅방") {

                        val url = "http://capstone123.dothome.co.kr/detailchat.php"
                        val getChatRoomIdRequest = object : StringRequest(
                            Request.Method.POST, url,
                            Response.Listener<String> { roomId ->
                                val intent = Intent(this@RoommateDetail, Chatting::class.java)
                                intent.putExtra("chatRoomName", username)
                                intent.putExtra("chatRoomId", roomId.toInt()) // chatRoom.id를 Int로 변환하여 전달
                                startActivity(intent)
                            },
                            Response.ErrorListener { error ->
                                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                            }) {
                            override fun getParams(): Map<String, String> {
                                val params = HashMap<String, String>()
                                params["user1_id"] = uid!!
                                params["user2_id"] = user2_id ?: ""
                                return params
                            }
                        }

                        Volley.newRequestQueue(this).add(getChatRoomIdRequest)



                    } else if (response == "채팅방 생성") {
                        Toast.makeText(this, "채팅방 생성 완료", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, ChattlingList2::class.java)
                        startActivity(intent)
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["user1_id"] = uid!!
                    params["user2_id"] = user2_id ?: ""
                    return params
                }
            }

            Volley.newRequestQueue(this).add(stringRequest)
        }

    }

    inner class FetchUsernameTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg urls: String): String {
            val url = URL(urls[0])
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            try {
                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val stringBuilder = StringBuilder()
                var line: String?

                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }

                bufferedReader.close()
                return stringBuilder.toString()
            } finally {
                connection.disconnect()
            }
        }
    }
}