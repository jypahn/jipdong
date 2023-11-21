package com.example.register

import android.widget.EditText
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class ValidatenameRequest(u_name: String, listener: Response.Listener<String?>?) :
    StringRequest(Request.Method.POST, URL, listener, null) {
    private val map: MutableMap<String, String>

    init {
        map = HashMap()
        map["u_name"] = u_name
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }

    companion object {
        //서버 url 설정(php파일 연동)
        private const val URL = "http://capstone123.dothome.co.kr/namevalid.php"
    }
}