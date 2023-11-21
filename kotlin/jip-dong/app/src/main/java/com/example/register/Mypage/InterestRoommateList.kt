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
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.FindRoommate.Roommate
import com.example.register.FindRoommate.RoommateDetail
import com.example.register.Profile
import com.example.register.R
import com.example.register.chatting.ChattlingList2
import com.example.register.home
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class InterestRoommateList : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private lateinit var listData: MutableList<RoomData>
    private lateinit var adapter: RoomMateAdapter
    private lateinit var emptytext: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest_roommate_list)

        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "") ?: ""
        emptytext = findViewById(R.id.emptytext)
        emptytext.visibility=View.INVISIBLE

        val url1 = "http://capstone123.dothome.co.kr/checkfavoriteroommateid.php" // PHP 파일의 URL을 입력하세요.

        val request = object : StringRequest(
            Request.Method.POST, url1,
            Response.Listener { response ->
                // 서버로부터 응답을 받았을 때 처리하는 부분
                val jsonArray = JSONArray(response) // JSON 배열로 변환

                val roommateidList = mutableListOf<String>()
                for (i in 0 until jsonArray.length()) {
                    val roommateid = jsonArray.getString(i)
                    roommateidList.add(roommateid)
                }

                queue = Volley.newRequestQueue(this)
                listData = mutableListOf()
                adapter = RoomMateAdapter(listData)

                val recyclerView = findViewById<RecyclerView>(R.id.roomscrap_recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter

                fetchData(roommateidList)
            },
            Response.ErrorListener { error ->
                // 서버 통신 중 오류가 발생했을 때 처리하는 부분
                Toast.makeText(this, "서버 통신 오류: $error", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["uid"] = uid // uid를 파라미터로 추가
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)

        val chatbutton = findViewById<ImageButton>(R.id.chatbutton)
        chatbutton.setOnClickListener{
            movetochatpage()
            overridePendingTransition(0, 0)
            finish()
        }

        val profilebutton = findViewById<ImageButton>(R.id.profilebutton)
        profilebutton.setOnClickListener{
            movetoprofilepage()
            overridePendingTransition(0, 0)
            finish()
        }

        val homebutton = findViewById<ImageButton>(R.id.homebutton)
        homebutton.setOnClickListener{
            movetohomepage()
            overridePendingTransition(0, 0)
            finish()
        }

        val InterestRoombutton = findViewById<Button>(R.id.roombutton)
        InterestRoombutton.setOnClickListener{
            movetoRoompage()
            overridePendingTransition(0, 0)
            finish()
        }

    }

    private fun fetchData(roommateidList: List<String>) {
        if (roommateidList.isEmpty()) {
            emptytext.visibility=View.VISIBLE
            listData.clear() // 기존 데이터를 모두 지우고 초기화
            adapter.notifyDataSetChanged() // 어댑터에 변경 사항 알림
            return
        }
        for (roommateid in roommateidList) {
            val url = "http://capstone123.dothome.co.kr/favoriteroommatelist.php?roommateid=$roommateid"

            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                { response ->
                    val newDataList = mutableListOf<RoomData>()
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
                        newDataList.add(roomData)
                    }

                    listData.addAll(newDataList)
                    adapter.notifyDataSetChanged() // 리사이클러뷰 갱신
                },
                { error ->
                    // handle error here
                })

            queue.add(jsonArrayRequest)
        }
    }

    private fun movetochatpage() {
        val intent = Intent(this, ChattlingList2::class.java)
        startActivity(intent)
    }

    private fun movetoprofilepage() {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
    }

    private fun movetohomepage() {
        val intent = Intent(this, home::class.java)
        startActivity(intent)
    }

    private fun movetoRoompage() {
        val intent = Intent(this, InterestList::class.java)
        startActivity(intent)
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
                    val params = java.util.HashMap<String, String>()
                    params["uid"] = uid
                    params["roommateid"] = roomData.id
                    return params
                }
            }

            val checkFavoriteQueue = Volley.newRequestQueue(this@InterestRoommateList)
            checkFavoriteQueue.add(checkFavoriteRequest)


            holder.itemView.setOnClickListener {
                val intent = Intent(this@InterestRoommateList, RoommateDetail::class.java)
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