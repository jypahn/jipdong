package com.example.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.Mypage.InterestList
import com.example.register.Mypage.MyRoomList
import com.example.register.Mypage.MyRoomMateList
import com.example.register.Mypage.UserInfo
import com.example.register.chatting.ChattlingList2
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)



        val myroombutton = findViewById<Button>(R.id.myroom)

        myroombutton.setOnClickListener{
            val intent = Intent(this, MyRoomList::class.java)
            startActivity(intent)
        }

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")




        val logoutButton = findViewById<Button>(R.id.logout)

        logoutButton.setOnClickListener {
            // 로그아웃 처리
            // SharedPreferences에서 uid 삭제
            sharedPreferences.edit().remove("uid").apply()

            // 로그인 페이지로 이동
            val intent = Intent(this, login::class.java)
            Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        val userpagebutton = findViewById<Button>(R.id.infobutton)

        fun moveToUserPage(){
            val intent = Intent(this, UserInfo::class.java)
            startActivity(intent)
        }

        userpagebutton.setOnClickListener {
            moveToUserPage()
        }


        val breakdownbutton = findViewById<Button>(R.id.breakdown)

        fun movetToMatePage(){
            val intent = Intent(this, MyRoomMateList::class.java)
            startActivity(intent)
        }

        breakdownbutton.setOnClickListener {
            movetToMatePage()
        }

        val SettingImage = findViewById<ImageView>(R.id.settingImage)

        SettingImage.bringToFront();



        val chatbutton = findViewById<ImageButton>(R.id.chatbutton)

        fun movetochatpage(){
            val intent = Intent(this, ChattlingList2::class.java)
            startActivity(intent)
        }

        chatbutton.setOnClickListener{
            movetochatpage()
            overridePendingTransition(0, 0)
            finish()
        }


        val homebutton = findViewById<ImageButton>(R.id.homebutton)

        fun movetohomepage(){
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }

        homebutton.setOnClickListener{
            movetohomepage()
            overridePendingTransition(0, 0)
            finish()
        }

        val favoritebutton = findViewById<ImageButton>(R.id.heartbutton)

        fun movetofavoritepage(){
            val intent = Intent(this, InterestList::class.java)
            startActivity(intent)
        }

        favoritebutton.setOnClickListener{
            movetofavoritepage()
            overridePendingTransition(0, 0)
            finish()

        }



        val username = findViewById<TextView>(R.id.username)

        val url = "http://capstone123.dothome.co.kr/getusername.php?uid=$uid"
        val name = FetchUsernameTask().execute(url).get()
        username.text = name



        val profileimage = findViewById<ImageButton>(R.id.prdileimage)


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