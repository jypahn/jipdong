package com.example.register.AddRoom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.register.R
import java.net.MalformedURLException


class RoomInsert_1 : AppCompatActivity() {
    private var housesort : String? =null
    private var sort_of_floor : String? =null


    private lateinit var button13: Button  //주/빌
    private lateinit var button4: Button //아파트
    private lateinit var button11: Button  //오피스텔
    private lateinit var button12: Button  //밪니하
    private lateinit var button14: Button   //옥탑
    private lateinit var button121: Button  //해당없음

    private var num_of_floor: EditText? = null
    private var area: EditText? = null
    private var roomcount: EditText? = null
    private var toiletcount: EditText? = null
    private var options: EditText? = null
    private var optionsValue: String? = null

    private var button5: Button? = null  //다음 버튼


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maemul_1)

        val address = intent.getStringExtra("address")
        val detailAddress = intent.getStringExtra("detailAddress")

        num_of_floor = findViewById<View>(R.id.editText9) as EditText
        area = findViewById<View>(R.id.editText8) as EditText
        roomcount = findViewById<View>(R.id.editText7) as EditText
        toiletcount = findViewById<View>(R.id.editText) as EditText
        options = findViewById<View>(R.id.editText10) as EditText

        val radius = R.drawable.radius
        val okradius = R.drawable.okradius

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


        val backbutton = findViewById<ImageButton>(R.id.imageButton3)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }


        button5 = findViewById<View>(R.id.button5) as Button
        button5!!.setOnClickListener {
            try {
                if (housesort != null && sort_of_floor != null && num_of_floor != null && area != null && roomcount != null && toiletcount != null && options != null) {
                    val num_of_floor = num_of_floor!!.text.toString()
                    val area = area!!.text.toString()
                    val roomcount = roomcount!!.text.toString()
                    val toiletcount = toiletcount!!.text.toString()
                    val options = options!!.text.toString()
                        val intent = Intent(this, RoomInsert_2::class.java)
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
