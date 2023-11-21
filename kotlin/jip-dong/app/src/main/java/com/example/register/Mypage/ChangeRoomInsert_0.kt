package com.example.register.Mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.register.AddRoom.AddressApiActivity
import com.example.register.AddRoom.NetworkStatus
import com.example.register.AddRoom.NetworkUtil
import com.example.register.AddRoom.RoomInsert_1
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

class ChangeRoomInsert_0 : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var detailAddress: EditText
    private lateinit var button: Button

    private lateinit var adress: EditText
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_room_insert0)
        NetworkUtil.setNetworkPolicy()


        val backbutton = findViewById<ImageButton>(R.id.imageButton)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        val roomid = intent.getStringExtra("roomid")


        detailAddress = findViewById(R.id.editText2)
        button = findViewById(R.id.button)

        adress = findViewById(R.id.button3)



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

                        val address = json.getString("adress")
                        val detailAdress = json.getString("detailAdress")




                        adress.setText(address)
                        detailAddress.setText(detailAdress)

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 터치 안되게 막기
        adress.setFocusable(false)
        // 주소입력창 클릭
        adress.setOnClickListener(View.OnClickListener { view: View? ->
            Log.i("주소설정페이지", "주소입력창 클릭")
            val status = NetworkStatus.getConnectivityStatus(applicationContext)
            if (status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
                Log.i("주소설정페이지", "주소입력창 클릭")
                val i = Intent(applicationContext, AddressApiActivity::class.java)
                // 화면전환 애니메이션 없애기
                overridePendingTransition(0, 0)
                // 주소결과
                startActivityForResult(
                    i,
                    SEARCH_ADDRESS_ACTIVITY
                )
            } else {
                Toast.makeText(applicationContext, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }
        })


        button.setOnClickListener{

            try {
                if (adress.text.toString().isNotEmpty() &&detailAddress.text.toString().isNotEmpty()) {

                    val address = adress.text.toString()
                    val detailAddress = detailAddress.text.toString()
                    val intent = Intent(this, ChangeRoomInser_1::class.java)
                    intent.putExtra("address", address)
                    intent.putExtra("detailAddress", detailAddress)
                    intent.putExtra("roomid", roomid)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "모든 항목을 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }




    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        Log.i("test", "onActivityResult")
        when (requestCode) {
            SEARCH_ADDRESS_ACTIVITY -> if (resultCode == RESULT_OK) {
                val data = intent!!.extras!!.getString("data")
                if (data != null) {
                    Log.i("test", "data:$data")
                    adress!!.setText(data)
                }
            }
        }
    }

    companion object {
        // 주소 요청코드 상수 requestCode
        private const val SEARCH_ADDRESS_ACTIVITY = 10000
    }

}
