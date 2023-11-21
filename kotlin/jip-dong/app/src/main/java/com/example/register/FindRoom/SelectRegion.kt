package com.example.register.FindRoom
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.register.R
import com.example.register.home

class SelectRegion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_region)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)



        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        val seoulbutton = findViewById<Button>(R.id.seoul)
        val whiteseoulbutton = findViewById<Button>(R.id.whiteseoul)
        val gwangjubutton = findViewById<Button>(R.id.gwangju)
        val whitegwangjubutton = findViewById<Button>(R.id.whitegwangju)
        val allseoulbutton = findViewById<Button>(R.id.allseoul)
        val allgwangjubutton = findViewById<Button>(R.id.allgwangju)
        val gwangsangubutton = findViewById<Button>(R.id.gwangsangu)
        val namgubutton = findViewById<Button>(R.id.namgu)
        val donggubutton = findViewById<Button>(R.id.donggu)
        val bukgubutton = findViewById<Button>(R.id.bukgu)
        val seogubutton = findViewById<Button>(R.id.seogu)




        seoulbutton.visibility = View.INVISIBLE
        allgwangjubutton.visibility = View.INVISIBLE
        gwangsangubutton.visibility = View.INVISIBLE
        namgubutton.visibility = View.INVISIBLE
        donggubutton.visibility = View.INVISIBLE
        bukgubutton.visibility = View.INVISIBLE
        seogubutton.visibility = View.INVISIBLE
        whitegwangjubutton.visibility = View.INVISIBLE


        seoulbutton.setOnClickListener {
            allseoulbutton.visibility = View.VISIBLE
            allgwangjubutton.visibility = View.INVISIBLE
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
            allgwangjubutton.visibility = View.VISIBLE
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


        val RoomList = Intent(this, RoomList::class.java)



        allseoulbutton.setOnClickListener {
            RoomList.putExtra("regionname","서울 전체")
            startActivity(RoomList)
        }

        allgwangjubutton.setOnClickListener {
            RoomList.putExtra("regionname","광주 전체")
            startActivity(RoomList)
        }

        gwangsangubutton.setOnClickListener {
            RoomList.putExtra("regionname","광주 광산구")
            startActivity(RoomList)
        }

        namgubutton.setOnClickListener {
            RoomList.putExtra("regionname","광주 남구")
            startActivity(RoomList)
        }

        donggubutton.setOnClickListener {
            RoomList.putExtra("regionname","광주 동구")
            startActivity(RoomList)
        }

        bukgubutton.setOnClickListener {
            RoomList.putExtra("regionname","광주 북구")
            startActivity(RoomList)
        }

        seogubutton.setOnClickListener {
            RoomList.putExtra("regionname","광주 서구")
            startActivity(RoomList)
        }

    }
}