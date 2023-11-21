package com.example.register.Mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.register.AddRoom.RoomInsert_2
import com.example.register.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class ChangeRoomInser_1 : AppCompatActivity() {
    private var housesort : String? =null
    private var sort_of_floor : String? =null


    private lateinit var button13: Button  //주/빌
    private lateinit var button4: Button //아파트
    private lateinit var button11: Button  //오피스텔
    private lateinit var button12: Button  //밪니하
    private lateinit var button14: Button   //옥탑
    private lateinit var button121: Button  //해당없음

    private lateinit var num_of_floor: EditText
    private lateinit var area: EditText
    private lateinit var roomcount: EditText
    private lateinit var toiletcount: EditText
    private lateinit var options: EditText
    private var optionsValue: String? = null

    private var button5: Button? = null  //다음 버튼

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_room_inser1)

        val address = intent.getStringExtra("address")
        val detailAddress = intent.getStringExtra("detailAddress")

        val roomid = intent.getStringExtra("roomid")


        val radius = R.drawable.radius
        val okradius = R.drawable.okradius


        num_of_floor = findViewById<View>(R.id.editText9) as EditText
        area = findViewById<View>(R.id.editText8) as EditText
        roomcount = findViewById<View>(R.id.editText7) as EditText
        toiletcount = findViewById<View>(R.id.editText) as EditText
        options = findViewById<View>(R.id.editText10) as EditText

        button13 = findViewById<View>(R.id.button13) as Button;
        button13.setOnClickListener {
            housesort = button13.text.toString()
            button13.setBackgroundResource(okradius)
            button4.setBackgroundResource(radius)
            button11.setBackgroundResource(radius)
        }

        button4 = findViewById<View>(R.id.button4) as Button;
        button4.setOnClickListener {
            housesort = button4.text.toString()
            button4.setBackgroundResource(okradius)
            button13.setBackgroundResource(radius)
            button11.setBackgroundResource(radius)
        }

        button11 = findViewById<View>(R.id.button11) as Button;
        button11.setOnClickListener {
            housesort = button11.text.toString()
            button11.setBackgroundResource(okradius)
            button13.setBackgroundResource(radius)
            button4.setBackgroundResource(radius)
        }

        button12 = findViewById<View>(R.id.button12) as Button;
        button12.setOnClickListener {
            sort_of_floor = button12.text.toString()
            button12.setBackgroundResource(okradius)
            button14.setBackgroundResource(radius)
            button121.setBackgroundResource(radius)
        }

        button14 = findViewById<View>(R.id.button14) as Button;
        button14.setOnClickListener {
            sort_of_floor = button14.text.toString()
            button14.setBackgroundResource(okradius)
            button12.setBackgroundResource(radius)
            button121.setBackgroundResource(radius)
        }

        button121 = findViewById<View>(R.id.button121) as Button;
        button121.setOnClickListener {
            sort_of_floor = button121.text.toString()
            button121.setBackgroundResource(okradius)
            button14.setBackgroundResource(radius)
            button12.setBackgroundResource(radius)
        }





        button5 = findViewById<View>(R.id.button5) as Button



        // PHP 서버 주소
        val phpUrl = "http://capstone123.dothome.co.kr/myroom1.php"

        // 네트워크 요청 및 응답 처리
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // URL 생성
                val url = URL("$phpUrl?roomid=$roomid")

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
                        val json = JSONObject(response.toString())

                        val housesort1 = json.getString("housesort")
                        val sort_of_floor1 = json.getString("sort_of_floor")
                        val num_of_floor1 = json.getString("num_of_floor")
                        val area1 = json.getString("area")
                        val roomcount1 = json.getString("roomcount")
                        val toiletcount1 = json.getString("toiletcount")
                        val options1 = json.getString("options")


                        if (housesort1 == "주택/빌라") {
                            button13.performClick();
                        }else if(housesort1 == "아파트"){
                            button4.performClick();
                        }else{
                            button11.performClick();
                        }

                        if (sort_of_floor1 == "반지하") {
                            button12.performClick();
                        }else if(sort_of_floor1 == "옥탑"){
                            button14.performClick();
                        }else{
                            button121.performClick();
                        }

                        num_of_floor.setText(num_of_floor1)
                        area.setText(area1)
                        roomcount.setText(roomcount1)
                        toiletcount.setText(toiletcount1)
                        options.setText(options1)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val backbutton = findViewById<ImageButton>(R.id.imageButton3)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }


        button5!!.setOnClickListener {
            try {
                if (housesort != null && sort_of_floor != null && num_of_floor != null && area != null && roomcount != null && toiletcount != null && options != null) {
                    val num_of_floor = num_of_floor!!.text.toString()
                    val area = area!!.text.toString()
                    val roomcount = roomcount!!.text.toString()
                    val toiletcount = toiletcount!!.text.toString()
                    val options = options!!.text.toString()
                    val intent = Intent(this, ChangeRoomInsert_2::class.java)
                    intent.putExtra("roomid", roomid)
                    intent.putExtra("address", address)
                    intent.putExtra("detailAddress", detailAddress)
                    intent.putExtra("housesort", housesort)
                    intent.putExtra("sort_of_floor", sort_of_floor)
                    intent.putExtra("num_of_floor", num_of_floor)
                    intent.putExtra("area", area)
                    intent.putExtra("roomcount", roomcount)
                    intent.putExtra("toiletcount", toiletcount)
                    intent.putExtra("options", options)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "모든 항목을 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }
    }
}
