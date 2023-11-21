package com.example.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.databinding.ActivityLoginBinding

class login : AppCompatActivity() {

    private val IP_ADDRESS = "capstone123.dothome.co.kr"
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val login = findViewById<Button>(R.id.loginbutton)


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 자동 로그인 체크
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", null)
        if (uid != null) {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginbutton.setOnClickListener {
            val uid = binding.id.text.toString()
            val pw = binding.password.text.toString()

            if (uid.isEmpty()) {
                Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (pw.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                login(uid, pw)
            }
        }

        val signup = findViewById<Button>(R.id.signupbutton)

        signup.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }

    }

    private fun login(uid: String, pw: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://capstone123.dothome.co.kr/logintest.php"

        val request = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                if (response != null) {
                    if (response == "success") {
                        // 로그인 성공
                        // SharedPreferences에 로그인 정보 저장
                        val sharedPreferences: SharedPreferences =
                            getSharedPreferences("login", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("uid", uid)
                        editor.apply()

                        val intent = Intent(this, home::class.java)
                        startActivity(intent)
                        finish()

                    } else if (response == "fail") {
                        // 비밀번호 실패
                        Toast.makeText(this, "비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show()
                    } else { // 아이디 실패
                        Toast.makeText(this, "아이디가 틀립니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }

            },
            Response.ErrorListener { error ->
                // 네트워크 오류 처리
                Toast.makeText(this, "네트워크 오류: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["uid"] = uid
                params["pw"] = pw
                return params
            }
        }
        queue.add(request)
    }
}
