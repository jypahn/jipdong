package com.example.register.AddRoom

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.register.R
import java.net.MalformedURLException

class RoomInsert_0 : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var detailAddress: EditText
    private lateinit var button: Button

    private lateinit var adress: EditText
    private lateinit var handler: Handler



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maemul)
        NetworkUtil.setNetworkPolicy()

        detailAddress = findViewById(R.id.editText2)
        button = findViewById(R.id.button)

        adress = findViewById(R.id.button3)


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

        val backbutton = findViewById<ImageButton>(R.id.imageButton)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        button.setOnClickListener{

            try {
                if (adress.text.toString().isNotEmpty() &&detailAddress.text.toString().isNotEmpty()) {

                    val address = adress.text.toString()
                    val detailAddress = detailAddress.text.toString()
                        val intent = Intent(this, RoomInsert_1::class.java)
                        intent.putExtra("address", address)
                        intent.putExtra("detailAddress", detailAddress)
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
