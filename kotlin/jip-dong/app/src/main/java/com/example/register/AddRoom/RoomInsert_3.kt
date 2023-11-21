package com.example.register.AddRoom


import android.app.DatePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.register.R
import com.example.register.register
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class RoomInsert_3 : AppCompatActivity() {
    private var enterdate: EditText? = null
    private var leastdate: EditText? = null
    private var gurantee: EditText? = null
    private var monthfee: EditText? = null
    private var manage: EditText? = null
    private var button20: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maemul_3)

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

        enterdate!!.setFocusable(false)

        enterdate!!.setOnClickListener{
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date: Int = today.get(Calendar.DATE)
            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    enterdate!!.setText("${year}월${month+1}월 ${dayOfMonth}일")
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

                    val intent = Intent(this, RoomInsert_4::class.java)
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