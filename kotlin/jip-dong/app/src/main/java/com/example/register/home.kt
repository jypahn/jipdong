package com.example.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.register.FindRoom.SelectRegion
import com.example.register.FindRoommate.Roommate
import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageButton
import android.widget.Toast
import com.example.register.AddRoom.RoomInsert_0
import com.example.register.Mypage.Ai_recommned
import com.example.register.Mypage.InterestList
import com.example.register.chatting.ChattlingList2
import com.example.register.chatting.makechat


class home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        val roommatebutton = findViewById<ImageButton>(R.id.roommatebutton)
        val roombutton = findViewById<ImageButton>(R.id.roombutton)
        val aibutton = findViewById<Button>(R.id.aibutton)
        val addbutton = findViewById<Button>(R.id.addbutton)

        fun moveToAIPage(){
            val intent = Intent(this, Ai_recommned::class.java)
            startActivity(intent)
        }

        aibutton.setOnClickListener {
            moveToAIPage()
        }

        fun moveToRoommatePage(){
            val intent = Intent(this, Roommate::class.java)
            startActivity(intent)
        }
        fun moveToRoomPage(){
            val intent = Intent(this, SelectRegion::class.java)
            startActivity(intent)
        }

        roommatebutton.setOnClickListener {
            moveToRoommatePage()
        }
        roombutton.setOnClickListener {
            moveToRoomPage()
        }


        fun movetoaddroompage(){
            val intent = Intent(this, RoomInsert_0::class.java)
            startActivity(intent)
        }

        addbutton.setOnClickListener {
            movetoaddroompage()
        }

        // SharedPreferences에서 로그인 정보 가져오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        if (uid.isNullOrEmpty()) {
            // 로그인 정보가 없으면 로그인 페이지로 이동
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        } else {
        }










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


        val profilebutton = findViewById<ImageButton>(R.id.profilebutton)

        fun movetoprofilepage(){
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        profilebutton.setOnClickListener{
            movetoprofilepage()
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

    }
}