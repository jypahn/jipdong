package com.example.register.Mypage

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.register.R
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

class MyRoommateDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_roommate_detail)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            val intent = Intent(this, MyRoomMateList::class.java)
            startActivity(intent)
            finish()
        }

        val roommateid = intent.getStringExtra("roommateid")

        val name = findViewById<TextView>(R.id.name)
        val location = findViewById<TextView>(R.id.textViewAuthor)
        val period = findViewById<TextView>(R.id.textViewDate)
        val content = findViewById<TextView>(R.id.textViewContent)
        val price = findViewById<TextView>(R.id.price)

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

        val changebutton = findViewById<Button>(R.id.changebutton)

        fun moveToChangePage() {
            val intent = Intent(this, ChangeMyRoommate::class.java)
            intent.putExtra("roommateid",roommateid)
            startActivity(intent)
        }

        changebutton.setOnClickListener {
            moveToChangePage()
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
