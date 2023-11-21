package com.example.register.Mypage

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import com.example.register.AddRoom.RoomInsert_4
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
import java.util.*

class ChangeRoomInsert_3 : AppCompatActivity() {

    private lateinit var enterdate: EditText
    private lateinit var leastdate: EditText
    private lateinit var gurantee: EditText
    private lateinit var monthfee: EditText
    private lateinit var manage: EditText
    private var button20: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_room_insert3)
        val address = intent.getStringExtra("address")
        val detailAddress = intent.getStringExtra("detailAddress")
        val housesort = intent.getStringExtra("housesort")
        val sort_of_floor = intent.getStringExtra("sort_of_floor")
        val num_of_floor = intent.getStringExtra("num_of_floor")
        val area = intent.getStringExtra("area")
        val roomcount = intent.getStringExtra("roomcount")
        val toiletcount = intent.getStringExtra("toiletcount")
        val options = intent.getStringExtra("options")
        val sex = intent.getStringExtra("sex")
        val age = intent.getStringExtra("age")
        val smoke = intent.getStringExtra("smoke")
        val animal = intent.getStringExtra("animal")

        val roomid = intent.getStringExtra("roomid")


        val backbutton = findViewById<ImageButton>(R.id.imageButton33)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        enterdate = findViewById<View>(R.id.editText3) as EditText
        leastdate = findViewById<View>(R.id.editText4) as EditText
        gurantee = findViewById<View>(R.id.editText6) as EditText
        monthfee = findViewById<View>(R.id.editText5) as EditText
        manage = findViewById<View>(R.id.editText11) as EditText
        button20 = findViewById<View>(R.id.button20) as Button


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

                        val enterdate1 = json.getString("enterdate")
                        val leastdate1 = json.getString("leastdate")
                        val gurantee1 = json.getString("gurantee")
                        val monthfee1 = json.getString("monthfee")
                        val manage1 = json.getString("manage")


                        enterdate.setText(enterdate1)
                        leastdate.setText(leastdate1)
                        gurantee.setText(gurantee1)
                        monthfee.setText(monthfee1)
                        manage.setText(manage1)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        enterdate.setFocusable(false)

        enterdate.setOnClickListener{
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date: Int = today.get(Calendar.DATE)
            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    enterdate.setText("${year}월${month+1}월 ${dayOfMonth}일")
                }
            }, year, month, date)
            dlg.show()
        }

        button20!!.setOnClickListener {
            val enterdate = enterdate!!.text.toString()
            val leastdate = leastdate!!.text.toString()
            val gurantee = gurantee!!.text.toString()
            val monthfee = monthfee!!.text.toString()
            val manage = manage!!.text.toString()

            val intent = Intent(this, ChangeRoomInsert_4::class.java)
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
            intent.putExtra("enterdate", enterdate)
            intent.putExtra("leastdate", leastdate)
            intent.putExtra("gurantee", gurantee)
            intent.putExtra("monthfee", monthfee)
            intent.putExtra("manage", manage)
            startActivity(intent)
        }
    }

}