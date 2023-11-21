package com.example.register.Mypage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.register.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

class ChangeUserInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user_info)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        // SharedPreferences에서 로그인 정보 가져오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val username = findViewById<TextView>(R.id.username)
        val useremail = findViewById<TextView>(R.id.useremail)
        val userphone = findViewById<TextView>(R.id.userphone)

        // PHP 서버 주소
        val phpUrl = "http://capstone123.dothome.co.kr/UserInfo.php"

        // 네트워크 요청 및 응답 처리
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // URL 생성
                val url = URL("$phpUrl?uid=$uid")

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

                    // UI 업데이트
                    withContext(Dispatchers.Main) {
                        val json = JSONObject(response.toString())
                        val uName = json.getString("u_name")
                        val uEmail = json.getString("email")
                        val uPhone = json.getString("phone")

                        username.text = uName
                        useremail.text = uEmail
                        userphone.text = uPhone
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        val mofifybutton = findViewById<Button>(R.id.modifybutton)

        fun modifyUserInfo() {
            var newname = username.text
            var newemail = useremail.text
            var newphone = userphone.text
            // 변경할 내용 가져오기
            val newName = newname
            val newEmail = newemail
            val newPhone = newphone

            // PHP 서버 주소
            val phpUrl = "http://capstone123.dothome.co.kr/changeinfo.php"

            // 네트워크 요청 및 응답 처리
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    // URL 생성
                    val url = URL(phpUrl)

                    // HTTPURLConnection 생성 및 설정
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.connectTimeout = 5000
                    connection.doOutput = true

                    // POST 데이터 전송
                    val postData = "uid=$uid&newName=$newName&newEmail=$newEmail&newPhone=$newPhone"
                    val outputStream = connection.outputStream
                    outputStream.write(postData.toByteArray())
                    outputStream.flush()
                    outputStream.close()

                    // 응답 코드 확인
                    if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                        // 응답 처리
                        // ...
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun moveToCheckPWPage() {
            val intent = Intent(this, UserInfo::class.java)
            startActivity(intent)
            finish()
        }

        mofifybutton.setOnClickListener {
            var newname = username.text
            var newemail = useremail.text
            var newphone = userphone.text
            if(newname.isEmpty()){
                Toast.makeText(this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(newphone.isEmpty()){
                Toast.makeText(this, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(!newemail.contains("@") || !newemail.contains(".co")) {
                Toast.makeText(this, "이메일에 @ 및 .co을 포함시키세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                modifyUserInfo()
                Toast.makeText(this, "성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                moveToCheckPWPage()
            }
        }

        val email_confirm = findViewById<TextView>(R.id.useremail_confirm)

        useremail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!Pattern.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
                        useremail.getText().toString()
                    )){
                    email_confirm.setText("이메일 형식으로 입력해주세요")
                    email_confirm.setTextColor(Color.RED)
                }else{
                    email_confirm.setText("")
                }


            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!Pattern.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
                        useremail.getText().toString()
                    )){
                    email_confirm.setText("이메일 형식으로 입력해주세요")
                    email_confirm.setTextColor(Color.RED)
                }else{
                    email_confirm.setText("")

                }


            }
        })

    }


}