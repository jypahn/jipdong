package com.example.register.Mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.register.R

class ChangeFindlocation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_findlocation)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        val roommateid = intent.getStringExtra("roommateid")


        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        val seoulbutton = findViewById<Button>(R.id.seoul)
        val whiteseoulbutton = findViewById<Button>(R.id.whiteseoul)
        val gwangjubutton = findViewById<Button>(R.id.gwangju)
        val whitegwangjubutton = findViewById<Button>(R.id.whitegwangju)
        val allseoulbutton = findViewById<Button>(R.id.allseoul)
        val gwangsangubutton = findViewById<Button>(R.id.gwangsangu)
        val namgubutton = findViewById<Button>(R.id.namgu)
        val donggubutton = findViewById<Button>(R.id.donggu)
        val bukgubutton = findViewById<Button>(R.id.bukgu)
        val seogubutton = findViewById<Button>(R.id.seogu)




        seoulbutton.visibility = View.INVISIBLE
        gwangsangubutton.visibility = View.INVISIBLE
        namgubutton.visibility = View.INVISIBLE
        donggubutton.visibility = View.INVISIBLE
        bukgubutton.visibility = View.INVISIBLE
        seogubutton.visibility = View.INVISIBLE
        whitegwangjubutton.visibility = View.INVISIBLE


        seoulbutton.setOnClickListener {
            allseoulbutton.visibility = View.VISIBLE
            gwangsangubutton.visibility = View.INVISIBLE
            namgubutton.visibility = View.INVISIBLE
            donggubutton.visibility = View.INVISIBLE
            bukgubutton.visibility = View.INVISIBLE
            seogubutton.visibility = View.INVISIBLE
            seoulbutton.visibility = View.INVISIBLE
            whiteseoulbutton.visibility = View.VISIBLE
            whitegwangjubutton.visibility = View.INVISIBLE
            gwangjubutton.visibility = View.VISIBLE
        }

        gwangjubutton.setOnClickListener {
            allseoulbutton.visibility = View.INVISIBLE
            gwangsangubutton.visibility = View.VISIBLE
            namgubutton.visibility = View.VISIBLE
            donggubutton.visibility = View.VISIBLE
            bukgubutton.visibility = View.VISIBLE
            seogubutton.visibility = View.VISIBLE
            gwangjubutton.visibility = View.INVISIBLE
            whitegwangjubutton.visibility = View.VISIBLE
            whiteseoulbutton.visibility = View.INVISIBLE
            seoulbutton.visibility = View.VISIBLE
        }


        val Roommateloction = Intent(this, ChangeMyRoommate::class.java)



        allseoulbutton.setOnClickListener {

            val extra = Bundle()
            val intent = Intent()
            extra.putString("regionname", "서울 전체")
            extra.putString("roommateid", roommateid)
            intent.putExtras(extra)
            setResult(RESULT_OK, intent)
            finish()
        }



        gwangsangubutton.setOnClickListener {

            val extra = Bundle()
            val intent = Intent()
            extra.putString("regionname", "광주 광산구")
            extra.putString("roommateid", roommateid)
            intent.putExtras(extra)
            setResult(RESULT_OK, intent)
            finish()
        }

        namgubutton.setOnClickListener {

            val extra = Bundle()
            val intent = Intent()
            extra.putString("regionname", "광주 남구")
            extra.putString("roommateid", roommateid)
            intent.putExtras(extra)
            setResult(RESULT_OK, intent)
            finish()
        }

        donggubutton.setOnClickListener {
            val extra = Bundle()
            val intent = Intent()
            extra.putString("regionname", "광주 동구")
            extra.putString("roommateid", roommateid)
            intent.putExtras(extra)
            setResult(RESULT_OK, intent)
            finish()
        }

        bukgubutton.setOnClickListener {
            val extra = Bundle()
            val intent = Intent()
            extra.putString("regionname", "광주 북구")
            extra.putString("roommateid", roommateid)
            intent.putExtras(extra)
            setResult(RESULT_OK, intent)
            finish()
        }

        seogubutton.setOnClickListener {
            val extra = Bundle()
            val intent = Intent()
            extra.putString("regionname", "광주 서구")
            extra.putString("roommateid", roommateid)
            intent.putExtras(extra)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}