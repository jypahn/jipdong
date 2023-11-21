package com.example.register.Mypage

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.AddRoom.AddressApiActivity
import com.example.register.AddRoom.NetworkStatus
import com.example.register.AddRoom.RoomInsert_0
import com.example.register.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class ChangeMyRoommate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_my_roommate)

        // SharedPreferences에서 로그인 정보 가져오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val roommateid = intent.getStringExtra("roommateid")

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        val locationbutton = findViewById<Button>(R.id.locatebutton)

        var intent = getIntent()





        val startbutton = findViewById<Button>(R.id.startdatebutton)
        val finishbutton = findViewById<Button>(R.id.finishdatebutton)
        val content = findViewById<EditText>(R.id.detail)
        val changebutton = findViewById<Button>(R.id.changebutton)

        val startprice = findViewById<EditText>(R.id.pricestarttext)
        val endprice = findViewById<EditText>(R.id.priceendtext)


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



                            locationbutton.setText("$location1")
                            startbutton.text = "$start_date"
                            finishbutton.text = "$end_date"
                            startprice.setText("$pricestarttext")
                            endprice.setText("$priceendtext")
                            content.setText("$content1")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val location = intent.getStringExtra("regionname")
        locationbutton.text = location ?: "희망지역 선택" // 기본 값으로 "지역 선택"을 설정

        locationbutton.setOnClickListener{ view: View? ->
            val status = NetworkStatus.getConnectivityStatus(applicationContext)
            if (status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
                val i = Intent(applicationContext, ChangeFindlocation::class.java)
                intent.putExtra("roommateid", roommateid)

                // 화면전환 애니메이션 없애기
                overridePendingTransition(0, 0)
                startActivityForResult(
                    i,
                    SEARCH_ACTIVITY
                )
            } else {
                Toast.makeText(applicationContext, "오류 발생", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        startbutton.setOnClickListener {
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date: Int = today.get(Calendar.DATE)
            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    startbutton.setText("${year}월${month+1}월 ${dayOfMonth}일")
                }
            }, year, month, date)
            dlg.show()
        }

        finishbutton.setOnClickListener {
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date: Int = today.get(Calendar.DATE)
            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    finishbutton.setText("${year}월${month+1}월 ${dayOfMonth}일")
                }
            }, year, month, date)
            dlg.show()
        }




        changebutton.setOnClickListener {
            val location = locationbutton.text.toString()
            val startDate = startbutton.text.toString()
            val endDate = finishbutton.text.toString()
            val startprice = startprice.text.toString()
            val endprice = endprice.text.toString()
            val contentText = content.text.toString()

            // uid가 null인 경우에 대한 처리
            if (uid.isNullOrEmpty()) {
                Toast.makeText(this, "사용자 오류" +
                        "로그아웃 후 로그인해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(location=="희망지역 선택") {
                android.widget.Toast.makeText(
                    this,
                    "희망지역을 선택해주세요.",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }else if (startDate=="시작일 선택") {
                android.widget.Toast.makeText(this, "시작일을 선택해주세요.", android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(endDate=="종료일 선택") {
                android.widget.Toast.makeText(
                    this,
                    "종료일을 선택해주세요.",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }else if(contentText.isNullOrEmpty()) {
                android.widget.Toast.makeText(
                    this,
                    "자기 소개를 해주세요.",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            // 데이터 전송을 위한 HTTP 요청 생성
            val queue = Volley.newRequestQueue(this)
            val url = "http://capstone123.dothome.co.kr/changeroommate.php" // PHP 파일의 URL을 입력해야 합니다.

            val stringRequest = object : StringRequest(Method.POST, url,
                Response.Listener<String> { response ->
                    // 요청 성공 시 수행할 작업
                    android.widget.Toast.makeText(this, "수정되었습니다.", android.widget.Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MyRoommateDetail::class.java)
                    intent.putExtra("roommateid", roommateid)
                    startActivity(intent)
                    finish()


                },
                Response.ErrorListener { error ->
                    // 요청 실패 시 수행할 작업
                    Toast.makeText(this, "오류 발생: ${error.message}", Toast.LENGTH_SHORT).show()
                    // 오류 처리를 위한 작업을 수행합니다.
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    // 전송할 데이터를 파라미터 형태로 지정합니다.
                    val params = HashMap<String, String>()
                    params["id"] = roommateid ?: "" // 사용자 ID를 전달합니다.
                    params["location"] = location
                    params["start_date"] = startDate
                    params["end_date"] = endDate
                    params["pricestarttext"] = startprice
                    params["priceendtext"] = endprice
                    params["content"] = contentText
                    params["writer"] = uid // 사용자 ID를 전달합니다.
                    return params
                }
            }

            // 요청을 큐에 추가합니다.
            queue.add(stringRequest)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        Log.i("test", "onActivityResult")
        when (requestCode) {
            SEARCH_ACTIVITY -> if (resultCode == RESULT_OK) {
                val regionname = intent!!.extras!!.getString("regionname")
                if (regionname != null) {
                    Log.i("test", "regionname:$regionname")
                    val locationbutton = findViewById<Button>(R.id.locatebutton)
                    locationbutton!!.setText(regionname)
                }
            }
        }
    }
    companion object {
        // 주소 요청코드 상수 requestCode
        private const val SEARCH_ACTIVITY = 10000
    }
}