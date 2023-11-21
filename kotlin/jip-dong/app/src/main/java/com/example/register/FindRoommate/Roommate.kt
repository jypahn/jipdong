package com.example.register.FindRoommate

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.FindRoom.RoomDetail
import com.example.register.FindRoom.RoomList
import com.example.register.Mypage.MyRoomDetail
import com.example.register.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class Roommate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roommate)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        val registbutton = findViewById<ImageButton>(R.id.writebutton)

        fun moveToRegistPage(){
            val intent = Intent(this, RegisterRoommate::class.java)
            startActivity(intent)
        }

        registbutton.setOnClickListener {
            moveToRegistPage()
        }



        val queue = Volley.newRequestQueue(this)
        val url = "http://capstone123.dothome.co.kr/roomlist.php"

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
                    val pricestarttext  = jsonObject.getString("pricestarttext")
                    val priceendtext  = jsonObject.getString("priceendtext")
                    val uid = jsonObject.getString("uid")
                    val writedate = jsonObject.getString("writedate")



                    val url = "http://capstone123.dothome.co.kr/getusername.php?uid=$uid"
                    val username = FetchUsernameTask().execute(url).get()

                    val roomData = RoomData(id, location, start_date, end_date, content, pricestarttext, priceendtext, uid, writedate, username)
                    listData.add(roomData)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.roomscrap_recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)
                val adapter = RoomMateAdapter(listData)
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
    inner class RoomMateAdapter(private val listData: List<RoomData>) :
        RecyclerView.Adapter<RoomMateAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.name)
            val locationTextView: TextView = itemView.findViewById(R.id.location)
            val infoTextView: TextView = itemView.findViewById(R.id.detail)
            val periodText : TextView = itemView.findViewById(R.id.period)
            val priceText : TextView = itemView.findViewById(R.id.price)
            val writeText : TextView = itemView.findViewById(R.id.time)
            val heartbutton : ImageButton = itemView.findViewById(R.id.Heart)
            val emptyheartbutton : ImageButton = itemView.findViewById(R.id.EmptyHeart)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.roommatelist, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val roomData = listData[position]
            holder.nameTextView.text = roomData.username
            holder.locationTextView.text = roomData.location
            holder.priceText.text =
                "희망금액 : " + roomData.pricestarttext + "만원" + " ~ " + roomData.priceendtext + "만원"
            holder.periodText.text = "기간 : " + roomData.start_date + " ~ " + roomData.end_date
            holder.infoTextView.text = roomData.content
            holder.writeText.text = roomData.writedate.substring(0 until 10)

            holder.emptyheartbutton.visibility = View.VISIBLE
            holder.heartbutton.visibility = View.GONE

            // 서버에 데이터를 전송하여 해당 방이 사용자의 즐겨찾기 목록에 있는지 확인
            val checkFavoriteUrl = "http://capstone123.dothome.co.kr/favoriteroommate_check.php"
            val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
            val uid = sharedPreferences.getString("uid", "") ?: ""

            val checkFavoriteRequest = object : StringRequest(Method.POST, checkFavoriteUrl,
                Response.Listener { response ->
                    if (response == "favorite") {
                        // 즐겨찾기에 있는 경우
                        holder.emptyheartbutton.visibility = View.GONE
                        holder.heartbutton.visibility = View.VISIBLE
                    } else {
                        // 즐겨찾기에 없는 경우
                        holder.emptyheartbutton.visibility = View.VISIBLE
                        holder.heartbutton.visibility = View.GONE
                    }
                },
                Response.ErrorListener { error ->
                    // 에러 처리
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["uid"] = uid
                    params["roommateid"] = roomData.id
                    return params
                }
            }

            val checkFavoriteQueue = Volley.newRequestQueue(this@Roommate)
            checkFavoriteQueue.add(checkFavoriteRequest)


            holder.itemView.setOnClickListener {
                val intent = Intent(this@Roommate, RoommateDetail::class.java)
                intent.putExtra("roommateid", roomData.id)
                intent.putExtra("uid", roomData.uid)
                startActivity(intent)
            }

            holder.emptyheartbutton.setOnClickListener {
                val roomData = listData[position]
                val roommateid = roomData.id
                val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                val uid = sharedPreferences.getString("uid", "")

                val task = InsertData()
                task.execute(
                    "http://capstone123.dothome.co.kr/favoriteroommate.php",
                    uid,
                    roommateid

                )

                // 가시성 변경 후 서버에 추가 요청 결과를 다시 확인
                holder.emptyheartbutton.visibility = View.GONE
                holder.heartbutton.visibility = View.VISIBLE
            }


            holder.heartbutton.setOnClickListener {
                val roomData = listData[position]
                val roommateid = roomData.id
                val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                val uid = sharedPreferences.getString("uid", "")

                val task = InsertData()
                task.execute(
                    "http://capstone123.dothome.co.kr/deletefavoriteroommate.php",
                    uid,
                    roommateid

                )

                // 가시성 변경 후 서버에 추가 요청 결과를 다시 확인
                holder.emptyheartbutton.visibility = View.VISIBLE
                holder.heartbutton.visibility = View.GONE


            }

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
        val priceendtext : String,
        val uid: String,
        val writedate: String,
        val username: String
    )

    private class InsertData : AsyncTask<String, Void, String>() {


        override fun doInBackground(vararg params: String?): String {

            val serverURL: String? = params[0]
            val uid: String? = params[1]
            val roommateid: String? = params[2]


            val postParameters: String = "uid=$uid&roommateid=$roommateid"

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
