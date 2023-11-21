package com.example.register.Mypage



import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.R
import com.example.register.chatting.Chatting
import com.example.register.chatting.ChattlingList2
import com.example.register.home
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Ai_result_2 : AppCompatActivity() {
    private lateinit var textView20: TextView
    private lateinit var button2: Button
    private lateinit var button1: Button
    private lateinit var secondHighestSimilarityUser: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ai_result2)
        textView20 = findViewById(R.id.textView20)

        val phpUrl = "http://capstone123.dothome.co.kr/Jaccard_personal.php"

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        RequestTask().execute(phpUrl)
        button2 = findViewById<View>(R.id.button2) as Button;
        button2.setOnClickListener {
            // 다른 액티비티 실행
            val intent = Intent(this, home::class.java)
            startActivity(intent)
            finish()
        }
        button1 = findViewById<View>(R.id.button1) as Button;
        button1.setOnClickListener {
            val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
            val uid = sharedPreferences.getString("uid", "")
            val user2_id = secondHighestSimilarityUser

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
                                val intent = Intent(this@Ai_result_2, Chatting::class.java)
                                intent.putExtra("chatRoomName", username)
                                intent.putExtra("chatRoomId", roomId.toInt()) // chatRoom.id를 Int로 변환하여 전달
                                startActivity(intent)
                            },
                            Response.ErrorListener { error ->
                                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                            }) {
                            override fun getParams(): Map<String, String> {
                                val params = HashMap<String, String>()
                                val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                                val uid = sharedPreferences.getString("uid", "")
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

    inner class RequestTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg urls: String): String {
            val url = URL(urls[0])
            val connection = url.openConnection() as HttpURLConnection

            try {
                val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                val uid = sharedPreferences.getString("uid", "")



                // uid 값을 PHP 파일로 전송
                val postData = "uid=$uid"
                connection.setRequestProperty("Content-Length", postData.length.toString())
                connection.doOutput = true
                connection.outputStream.write(postData.toByteArray(Charsets.UTF_8))

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

        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val url = "http://capstone123.dothome.co.kr/getusername.php?uid=$uid"
        val username = FetchUsernameTask().execute(url).get()

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

        override fun onPostExecute(result: String) {
            try {
                val jsonResult = JSONObject(result)
                secondHighestSimilarityUser = jsonResult.getString("secondHighestSimilarityUser")
                val secondHighestSimilarity = jsonResult.getDouble("secondHighestSimilarity")
                val formattedSimilarity = String.format("%.2f", secondHighestSimilarity * 100)

                val url3 = "http://capstone123.dothome.co.kr/getusername.php?uid=$secondHighestSimilarityUser"
                val username2 = FetchUsernameTask().execute(url3).get()

                // 여기서 highestSimilarityUser와 highestSimilarity 변수로 사용하거나 원하는 형태로 가공하여 출력 또는 처리할 수 있습니다.
                textView20.text = "집동이가 추천 해드리는 분! $username2\n" +
                        "$username 님과 잘 맞을 확률은 $formattedSimilarity%입니다."
            } catch (e: JSONException) {
                e.printStackTrace()
            }
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