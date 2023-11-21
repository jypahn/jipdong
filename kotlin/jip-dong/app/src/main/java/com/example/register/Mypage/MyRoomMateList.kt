package com.example.register.Mypage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.Profile
import com.example.register.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MyRoomMateList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_room_mate_list)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val url = "http://capstone123.dothome.co.kr/userroommatelist.php?uid=$uid"

        val queue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val listData = mutableListOf<RoomData>()
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val id = jsonObject.getString("id")
                    val location = jsonObject.getString("location")
                    val start_date = jsonObject.getString("start_date")
                    val end_date = jsonObject.getString("end_date")
                    val content = jsonObject.getString("content")
                    val pricestarttext = jsonObject.getString("pricestarttext")
                    val priceendtext = jsonObject.getString("priceendtext")
                    val uid = jsonObject.getString("uid")
                    val writedate = jsonObject.getString("writedate")

                    val url = "http://capstone123.dothome.co.kr/getusername.php?uid=$uid"
                    val username = FetchUsernameTask().execute(url).get()

                    val roomData = RoomData(id, location, start_date, end_date, content, pricestarttext, priceendtext, uid, writedate, username)
                    listData.add(roomData)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.myroomscrap_recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)
                val adapter = RoomMateListAdapter(listData)
                recyclerView.adapter = adapter
            },
            { error ->
                // handle error here
            })

        queue.add(jsonArrayRequest)
    }
    inner class FetchUsernameTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg urls: String): String {
            val url = URL(urls[0])
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            try {
                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val stringBuilder = StringBuilder()
                var line: String?

                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }

                bufferedReader.close()
                return stringBuilder.toString()
            } finally {
                connection.disconnect()
            }
        }
    }
    inner class RoomMateListAdapter(private val listData: List<RoomData>) :
        RecyclerView.Adapter<RoomMateListAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.name)
            val locationTextView: TextView = itemView.findViewById(R.id.location)
            val infoTextView: TextView = itemView.findViewById(R.id.detail)
            val periodText : TextView = itemView.findViewById(R.id.period)
            val priceText : TextView = itemView.findViewById(R.id.price)
            val writeText : TextView = itemView.findViewById(R.id.time)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.myroommatelist, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val roomData = listData[position]
            holder.nameTextView.text = roomData.username
            holder.locationTextView.text = roomData.location
            holder.priceText.text = "희망금액 : " + roomData.pricestarttext + "만원"+ " ~ " + roomData.priceendtext + "만원"
            holder.periodText.text = "기간 : " + roomData.start_date + " ~ " + roomData.end_date
            holder.infoTextView.text = roomData.content
            holder.writeText.text = roomData.writedate.substring(0 until 10)


            holder.itemView.setOnLongClickListener {
                val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
                alertDialogBuilder.setMessage("룸메이트 정보를 삭제하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("네") { _, _ ->
                        deleteRoommate(roomData.id)
                        val intent = Intent(this@MyRoomMateList, MyRoomMateList::class.java)
                        intent.putExtra("uid", roomData.uid)
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton("아니오") { dialog, _ ->
                        dialog.dismiss()
                    }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()

                true
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(this@MyRoomMateList, MyRoommateDetail::class.java)
                intent.putExtra("roommateid", roomData.id)
                startActivity(intent)
            }
        }
        private fun deleteRoommate(id: String) {

            val url = "http://capstone123.dothome.co.kr/deleteroommate.php?id=$id"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->

                },
                { error ->
                })

            val queue = Volley.newRequestQueue(this@MyRoomMateList)
            queue.add(stringRequest)
        }



        override fun getItemCount(): Int {
            return listData.size
        }
    }
    data class RoomData(
        val id: String,
        val location: String,
        val start_date: String,
        val end_date: String,
        val content: String,
        val pricestarttext: String,
        val priceendtext: String,
        val uid: String,
        val writedate: String,
        val username: String
    )
}