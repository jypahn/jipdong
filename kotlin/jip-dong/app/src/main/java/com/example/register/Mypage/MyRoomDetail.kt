package com.example.register.Mypage

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.register.FindRoom.RoomDetail
import com.example.register.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MyRoomDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_room_detail)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            val intent = Intent(this, MyRoomList::class.java)
            startActivity(intent)
            finish()
        }

        val roomId = intent.getStringExtra("roomid") ?: ""

        // roomId를 사용하여 방 상세 데이터를 가져오는 요청을 생성
        val queue = Volley.newRequestQueue(this)
        val url = "http://capstone123.dothome.co.kr/roomdetail_4.php?roomid=$roomId"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val listData = mutableListOf<RoomDetail.RoomData>()

                try {
                    for (i in 0 until response.length()) {
                        val roomObject = response.getJSONObject(i)
                        val roomtitle = roomObject.optString("roomtitle")
                        val sex = roomObject.optString("sex")
                        val age = roomObject.optString("age")
                        val postdate = roomObject.optString("postdate")
                        val housesort = roomObject.optString("housesort")
                        val numfloor= roomObject.optString("numfloor")
                        val floorSort = roomObject.optString("sort_of_floor")
                        val smoke = roomObject.optString("smoke")
                        val animal = roomObject.optString("animal")
                        val area = roomObject.optString("area")
                        val content = roomObject.optString("content")
                        val roomcount = roomObject.optString("roomcount")
                        val toiletcount = roomObject.optString("toiletcount")
                        val options = roomObject.optString("options")
                        val enterdate = roomObject.optString("enterdate")
                        val leastdate = roomObject.optString("leastdate")
                        val gurantee = roomObject.optString("gurantee")
                        val monthfee = roomObject.optString("monthfee")
                        val manage = roomObject.optString("manage")
                        val host= roomObject.optString("host")
                        val roomData = RoomDetail.RoomData(host,roomId, roomtitle, sex, age, postdate, housesort, floorSort, numfloor,smoke,animal,area,content,roomcount,toiletcount,options,enterdate,leastdate,gurantee,monthfee,manage)
                        listData.add(roomData)
                    }
                    val dataTextView: TextView = findViewById(R.id.text)
                    val host: TextView = findViewById(R.id.id)
                    val date: TextView = findViewById(R.id.date)
                    val houseSortTextView: TextView = findViewById(R.id.typetext)
                    val floorSortTextView: TextView = findViewById(R.id.floortext)
                    val smokingtextview: TextView = findViewById(R.id.smokingtext)
                    val pettext: TextView = findViewById(R.id.pettext)
                    val conditionTextView:TextView = findViewById(R.id.conditiontext)
                    val areaTextView: TextView = findViewById(R.id.areatext)
                    val contentTextView: TextView = findViewById(R.id.info)
                    val roomctextView: TextView = findViewById(R.id.roomctext)
                    val toiletctextView: TextView = findViewById(R.id.toiletctext)
                    val guaranteetextView: TextView = findViewById(R.id.guaranteetext)
                    val monthfeetextView: TextView = findViewById(R.id.monthfeetext)
                    val managetextView: TextView = findViewById(R.id.managetext)
                    val enterdatetextView: TextView = findViewById(R.id.enterdatetext)
                    val leastdatetextView: TextView = findViewById(R.id.leastdatetext)
                    val optionstextView: TextView = findViewById(R.id.optionstext)
                    val data = listData.joinToString("\n\n") { roomData ->
                        "Room ID: ${roomData.roomId}\n" +
                                "Room Title: ${roomData.roomTitle}\n" +
                                "Sex: ${roomData.sex}\n" +
                                "Age: ${roomData.age}\n" +
                                "Post Date: ${roomData.postDate}\n" +
                                "House Sort: ${roomData.houseSort}\n" +
                                "Floor Sort: ${roomData.floorSort}\n" +
                                "numfloor: ${roomData.numfloor}\n" +
                                "smoke:${roomData.smoke}\n" +
                                "animal:${roomData.animal}\n"+
                                "info:${roomData.content}\n"+
                                "roomctext:${roomData.roomcount}\n"+
                                "toiletctext:${roomData.toiletcount}\n"+
                                "guaranteetext:${roomData.gurantee}\n"+
                                "monthfeetext:${roomData.monthfee}\n"+
                                "managetext:${roomData.manage}\n"+
                                "enterdate:${roomData.enterdate}\n"+
                                "leastdate:${roomData.leastdate}\n"+
                                "options:${roomData.options}\n"+
                                "host:${roomData.host}\n"+
                                "Area: ${roomData.area}\n"
                    }

                    val url2 = "http://capstone123.dothome.co.kr/getusername.php?uid=${listData[0].host}"
                    val username = FetchUsernameTask().execute(url2).get()



                    dataTextView.text = listData[0].roomTitle

                    date.text = listData[0].postDate
                    houseSortTextView.text = listData[0].houseSort
                    floorSortTextView.text = "${listData[0].floorSort} ${listData[0]. numfloor} "
                    smokingtextview.text = listData[0].smoke
                    pettext.text = listData[0].animal
                    conditionTextView.text = "${listData[0].sex} , ${listData[0].age}세"
                    areaTextView.text = listData[0].area+"㎡"
                    contentTextView.text = listData[0].content
                    roomctextView.text = listData[0].roomcount+"개"
                    toiletctextView.text = listData[0].toiletcount+"개"
                    guaranteetextView.text = listData[0].gurantee+"만원"
                    monthfeetextView.text = listData[0].monthfee+"만원"
                    managetextView.text = listData[0].manage+"만원"
                    enterdatetextView.text = listData[0].enterdate
                    leastdatetextView.text = listData[0].leastdate+"개월"
                    optionstextView.text = listData[0].options
                    host.text = username
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                // 에러 처리
                Log.e("RoomDetail", "Error retrieving room data: $error")
            })

        Log.d("RoomDetail", "Received roomid: $roomId")
        queue.add(jsonArrayRequest)

        val Changebutton = findViewById<Button>(R.id.Changebutton)

        fun moveToChangeRoomPage(){
            val intent = Intent(this, ChangeRoomInsert_0::class.java)
            intent.putExtra("roomid", roomId)
            startActivity(intent)

        }

        Changebutton.setOnClickListener {
            moveToChangeRoomPage()
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

    data class RoomData(
        val host: String,
        val roomId: String,
        val roomTitle: String,
        val sex: String,
        val age: String,
        val postDate: String,
        val houseSort: String,
        val floorSort: String,
        val numfloor: String,
        val smoke:String,
        val animal:String,
        val area: String,
        val content: String,
        val roomcount: String,
        val toiletcount: String,
        val options: String,
        val enterdate: String,
        val leastdate: String,
        val gurantee: String,
        val monthfee: String,
        val manage: String
    )

}