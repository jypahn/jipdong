package com.example.register.Mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.register.AddRoom.RoomInsert_3
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

class ChangeRoomInsert_2 : AppCompatActivity() {

    private var sex: String? = null
    private var age: String? = null
    private var smoke: String? = null
    private var animal: String? = null

    private lateinit var button16: Button  //남성
    private lateinit var button10: Button //상관 없음
    private lateinit var button6: Button  //여성
    private lateinit var button17: Button  //무관
    private lateinit var button7: Button   //20~25
    private lateinit var button38: Button  //26~30
    private lateinit var button48: Button  //30~34
    private lateinit var button9: Button  //35이상
    private lateinit var button19: Button  //흡연
    private lateinit var button15: Button  //비흡연
    private lateinit var button18: Button  //불가능
    private lateinit var button28: Button  //가능
    private var button8: Button? = null  //다음 버튼


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_room_insert2)
        val address = intent.getStringExtra("address")
        val detailAddress = intent.getStringExtra("detailAddress")
        val housesort = intent.getStringExtra("housesort")
        val sort_of_floor = intent.getStringExtra("sort_of_floor")
        val num_of_floor = intent.getStringExtra("num_of_floor")
        val area = intent.getStringExtra("area")
        val roomcount = intent.getStringExtra("roomcount")
        val toiletcount = intent.getStringExtra("toiletcount")
        val options = intent.getStringExtra("options")


        val roomid = intent.getStringExtra("roomid")


        val radius = R.drawable.radius
        val okradius = R.drawable.okradius


        val backbutton = findViewById<ImageButton>(R.id.imageButton30)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        button16 = findViewById<View>(R.id.button16) as Button;
        button16.setOnClickListener {
            sex = button16.text.toString()
            button16.setBackgroundResource(okradius)
            button10.setBackgroundResource(radius)
            button6.setBackgroundResource(radius)
        }

        button10 = findViewById<View>(R.id.button10) as Button;
        button10.setOnClickListener {
            sex = button10.text.toString()
            button10.setBackgroundResource(okradius)
            button16.setBackgroundResource(radius)
            button6.setBackgroundResource(radius)
        }

        button6 = findViewById<View>(R.id.button6) as Button;
        button6.setOnClickListener {
            sex = button6.text.toString()
            button6.setBackgroundResource(okradius)
            button10.setBackgroundResource(radius)
            button16.setBackgroundResource(radius)
        }

        button17 = findViewById<View>(R.id.button17) as Button;
        button17.setOnClickListener {
            age = button17.text.toString()
            button17.setBackgroundResource(okradius)
            button7.setBackgroundResource(radius)
            button38.setBackgroundResource(radius)
            button48.setBackgroundResource(radius)
            button9.setBackgroundResource(radius)
        }

        button7 = findViewById<View>(R.id.button7) as Button;
        button7.setOnClickListener {
            age = button7.text.toString()
            button7.setBackgroundResource(okradius)
            button17.setBackgroundResource(radius)
            button38.setBackgroundResource(radius)
            button48.setBackgroundResource(radius)
            button9.setBackgroundResource(radius)
        }

        button38 = findViewById<View>(R.id.button38) as Button;
        button38.setOnClickListener {
            age = button38.text.toString()
            button38.setBackgroundResource(okradius)
            button7.setBackgroundResource(radius)
            button17.setBackgroundResource(radius)
            button48.setBackgroundResource(radius)
            button9.setBackgroundResource(radius)
        }

        button48 = findViewById<View>(R.id.button48) as Button;
        button48.setOnClickListener {
            age = button48.text.toString()
            button48.setBackgroundResource(okradius)
            button7.setBackgroundResource(radius)
            button38.setBackgroundResource(radius)
            button17.setBackgroundResource(radius)
            button9.setBackgroundResource(radius)
        }

        button9 = findViewById<View>(R.id.button9) as Button;
        button9.setOnClickListener {
            age = button9.text.toString()
            button9.setBackgroundResource(okradius)
            button7.setBackgroundResource(radius)
            button38.setBackgroundResource(radius)
            button48.setBackgroundResource(radius)
            button17.setBackgroundResource(radius)
        }

        button19 = findViewById<View>(R.id.button19) as Button;
        button19.setOnClickListener {
            smoke = button19.text.toString()
            button19.setBackgroundResource(okradius)
            button15.setBackgroundResource(radius)
        }

        button15 = findViewById<View>(R.id.button15) as Button;
        button15.setOnClickListener {
            smoke = button15.text.toString()
            button15.setBackgroundResource(okradius)
            button19.setBackgroundResource(radius)
        }

        button18 = findViewById<View>(R.id.button18) as Button;
        button18.setOnClickListener {
            animal = button18.text.toString()
            button18.setBackgroundResource(okradius)
            button28.setBackgroundResource(radius)
        }

        button28 = findViewById<View>(R.id.button28) as Button;
        button28.setOnClickListener {
            animal = button28.text.toString()
            button28.setBackgroundResource(okradius)
            button18.setBackgroundResource(radius)
        }


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

                        val sex1 = json.getString("sex")
                        val age1 = json.getString("age")
                        val smoke1 = json.getString("smoke")
                        val animal1 = json.getString("animal")

                        if(sex1 == "남성"){
                            button16.performClick();
                        }else if(sex1 == "여성"){
                            button6.performClick();
                        }else {
                            button10.performClick();
                        }

                        if(age1 == "무관"){
                            button17.performClick();
                        }else if(age1 == "20~25"){
                            button7.performClick();
                        }else if (age1 == "26~30"){
                            button38.performClick();
                        }else if (age1 == "30~34"){
                            button48.performClick();
                        }else {
                            button9.performClick();
                        }

                        if(smoke1 == "흡연"){
                            button19.performClick();
                        }else{
                            button15.performClick();
                        }

                        if (animal1=="가능"){
                            button28.performClick();
                        }else{
                            button18.performClick();
                        }


                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }






        button8 = findViewById<View>(R.id.button8) as Button
        button8!!.setOnClickListener {
            try {
                if (sex != null && age != null && smoke != null && animal != null) {

                    val intent = Intent(this, ChangeRoomInsert_3::class.java)
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
                    intent.putExtra("sex", sex)
                    intent.putExtra("age", age)
                    intent.putExtra("smoke", smoke)
                    intent.putExtra("animal", animal)
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
