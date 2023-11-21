package com.example.register


import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.register.databinding.ActivityRegisterBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern


class register : AppCompatActivity() {

    private var sex : String? =null


    private val IP_ADDRESS = "capstone123.dothome.co.kr"

    private var validate = false



    private val id: EditText? = null

    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.finalsignup.setOnClickListener {
            val uid = binding.signid.text.toString()
            val pw = binding.signpassword.text.toString()
            val checkpassword = binding.signcheckpassword.text.toString()
            val u_name = binding.signname.text.toString()
            val phone = binding.signphone.text.toString()
            val email = binding.signemail.text.toString()
            val sex = sex

            if (uid.isEmpty()) {
                Toast.makeText(this,"아이디를 입력하세요.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (pw.isEmpty() || checkpassword.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(!email.contains("@") || !email.contains(".co")){
                Toast.makeText(this, "이메일에 @ 및 .co을 포함시키세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (pw != checkpassword){
                Toast.makeText(this,"비밀번호를 확인하세요.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (u_name.isEmpty()) {
                Toast.makeText(this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (phone.isEmpty()) {
                Toast.makeText(this, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(!validate){
                Toast.makeText(this, "아이디 중복 확인을 확인하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$",pw)){
                Toast.makeText(this, "비밀번호 형식을 지켜주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(sex == null){
                Toast.makeText(this, "성별을 선택하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else{
                Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                val task = InsertData()
                task.execute(
                    "http://$IP_ADDRESS/register_process.php",
                    uid,
                    pw,
                    u_name,
                    phone,
                    email,
                    sex
                )
                finish()

            }

        }



        val id = findViewById<EditText>(R.id.signid)
        val checkbutton = findViewById<Button>(R.id.checkidabutton)


        checkbutton.setOnClickListener(
            View.OnClickListener
            {
                val uid = id.text.toString()
                if (validate) {
                    return@OnClickListener
                }
                if (uid == "") {
                    val builder = AlertDialog.Builder(this@register)
                    var dialog = builder.setMessage("아이디를 입력하세요.")
                        .setPositiveButton("확인", null)
                        .create()
                    dialog.show()
                    return@OnClickListener
                }
                val responseListener =
                    Response.Listener<String?> { response ->
                        try {
                            val jsonResponse = JSONObject(response)
                            val success = jsonResponse.getBoolean("success")
                            if (success) {
                                val builder = AlertDialog.Builder(this@register)
                                var dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                    .setPositiveButton("확인", null)
                                    .create()
                                dialog.show()
                                id.setEnabled(false)
                                validate = true
                                binding.checkidabutton.text = "확인"

                            } else {
                                val builder = AlertDialog.Builder(this@register)
                                var dialog = builder.setMessage("중복된 아이디입니다.")
                                    .setNegativeButton("확인", null)
                                    .create()
                                dialog.show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                val validateRequest = ValidateRequest(uid, responseListener)
                val queue = Volley.newRequestQueue(this@register)
                queue.add(validateRequest)
            })

        val name = findViewById<EditText>(R.id.signname)


        val signpassword = findViewById<EditText>(R.id.signpassword)
        val checkpassword = findViewById<EditText>(R.id.signcheckpassword)
        val pw_confirm = findViewById<TextView>(R.id.pw_confirm)
        val pw_confirm2 = findViewById<TextView>(R.id.pw_confirm2)


        checkpassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(signpassword.getText().toString().equals(checkpassword.getText().toString())){
                    pw_confirm2.setText("비밀번호가 일치합니다.")
                    pw_confirm2.setTextColor(Color.BLACK)
                }
                else{
                    pw_confirm2.setText("비밀번호가 일치하지 않습니다.")
                    pw_confirm2.setTextColor(Color.RED)
                }


            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(signpassword.getText().toString().equals(checkpassword.getText().toString())){
                    pw_confirm2.setText("비밀번호가 일치합니다.")
                    pw_confirm2.setTextColor(Color.BLACK)
                }
                else{
                    pw_confirm2.setText("비밀번호가 일치하지 않습니다.")
                    pw_confirm2.setTextColor(Color.RED)
                }


            }
        })

        signpassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$",
                        signpassword.getText().toString()
                    )){
                    pw_confirm.setText("영문/숫자/특수문자를 입력해주세요 (8~20자)")
                    pw_confirm.setTextColor(Color.RED)
                }else{
                    pw_confirm.setText("안전한 비밀번호입니다")
                    pw_confirm.setTextColor(Color.BLACK)
                }


            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$",
                        signpassword.getText().toString()
                    )){
                    pw_confirm.setText("영문/숫자/특수문자를 입력해주세요 (8~20자)")
                    pw_confirm.setTextColor(Color.RED)
                }else{
                    pw_confirm.setText("안전한 비밀번호입니다.")
                    pw_confirm.setTextColor(Color.BLACK)
                }
            }
        })



        val email = findViewById<EditText>(R.id.signemail)
        val email_confirm = findViewById<TextView>(R.id.email_confirm)

        email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!Pattern.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
                        email.getText().toString()
                    )){
                    email_confirm.setText("이메일 형식으로 입력해주세요")
                    email_confirm.setTextColor(Color.RED)
                }else{
                    email_confirm.setText("")
                }


            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!Pattern.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
                        email.getText().toString()
                    )){
                    email_confirm.setText("이메일 형식으로 입력해주세요")
                    email_confirm.setTextColor(Color.RED)
                }else{
                    email_confirm.setText("")
                }


            }
        })


        val malebutton = findViewById<Button>(R.id.malebutton)
        val femalebutton = findViewById<Button>(R.id.femalebutton)

        val radius = R.drawable.radius
        val okradius = R.drawable.okradius
        malebutton.setOnClickListener {
            malebutton.setBackgroundResource(okradius)
            femalebutton.setBackgroundResource(radius)
            sex = malebutton.text.toString()
        }

        femalebutton.setOnClickListener {
            femalebutton.setBackgroundResource(okradius)
            malebutton.setBackgroundResource(radius)
            sex = femalebutton.text.toString()
        }
    }


    private class InsertData : AsyncTask<String, Void, String>() {


        override fun doInBackground(vararg params: String?): String {

            val serverURL: String? = params[0]
            val uid: String? = params[1]
            val pw: String? = params[2]
            val u_name: String? = params[3]
            val phone: String? = params[4]
            val email: String? = params[5]
            val sex : String? = params[6]

            val postParameters: String = "uid=$uid&pw=$pw&u_name=$u_name&phone=$phone&email=$email&sex=$sex"

            try {
                val url = URL(serverURL)
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection


                httpURLConnection.readTimeout = 5000
                httpURLConnection.connectTimeout = 5000
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.connect()


                val outputStream: OutputStream = httpURLConnection.outputStream
                outputStream.write(postParameters.toByteArray(charset("UTF-8")))
                outputStream.flush()
                outputStream.close()

                val responseStatusCode: Int = httpURLConnection.responseCode


                val inputStream: InputStream
                inputStream = if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    httpURLConnection.inputStream
                } else {
                    httpURLConnection.errorStream

                }


                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)

                val sb = StringBuilder()
                var line: String? = null

                while (bufferedReader.readLine().also({ line = it }) != null) {
                    sb.append(line)
                }

                bufferedReader.close();

                return sb.toString();

            } catch (e: Exception) {
                return "Error" + e.message
            }

        }

    }
}






