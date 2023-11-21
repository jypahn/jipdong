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
import android.widget.EditText
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
import com.example.register.FindRoom.RoomDetail
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

class InterestList : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private lateinit var listData: MutableList<RoomData>
    private lateinit var adapter: RoomListAdapter
    private lateinit var emptytext: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest_list)



        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "") ?: ""

        emptytext = findViewById(R.id.emptytext)
        emptytext.visibility=View.INVISIBLE
        val url1 = "http://capstone123.dothome.co.kr/checkfavoriteroomid2.php" // PHP 파일의 URL을 입력하세요.

        val request = object : StringRequest(
            Request.Method.POST, url1,
            Response.Listener { response ->

                val jsonArray = JSONArray(response) // JSON 배열로 변환

                val roomidList = mutableListOf<String>()
                for (i in 0 until jsonArray.length()) {
                    val roomid = jsonArray.getString(i)
                    roomidList.add(roomid)
                }

                queue = Volley.newRequestQueue(this)
                listData = mutableListOf()
                adapter = RoomListAdapter(listData)

                val recyclerView = findViewById<RecyclerView>(R.id.myscrap_recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter

                fetchData(roomidList)
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

        val InterestRoommatebutton = findViewById<Button>(R.id.roommatebutton)
        InterestRoommatebutton.setOnClickListener{
            movetoRoommatepage()
            overridePendingTransition(0, 0)
            finish()
        }
    }

    private fun fetchData(roomidList: List<String>) {
        if (roomidList.isEmpty()) {
            emptytext.visibility=View.VISIBLE
            listData.clear() // 기존 데이터를 모두 지우고 초기화
            adapter.notifyDataSetChanged() // 어댑터에 변경 사항 알림
            return
        }
        for (roomid in roomidList) {
            val url = "http://capstone123.dothome.co.kr/favoriteroomlist.php?roomid=$roomid"

            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                { response ->
                    val newDataList = mutableListOf<RoomData>()
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val roomtitle = jsonObject.getString("roomtitle")
                        val address = jsonObject.getString("adress")
                        val housesort = jsonObject.getString("housesort")
                        val sort_of_floor = jsonObject.getString("sort_of_floor")
                        val guarantee = jsonObject.getString("gurantee")
                        val monthfee = jsonObject.getString("monthfee")
                        val host = jsonObject.getString("host")
                        val roomData = RoomData(roomtitle, address, housesort, sort_of_floor, guarantee, monthfee, roomid, host)
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

    private fun movetoRoommatepage() {
        val intent = Intent(this, InterestRoommateList::class.java)
        startActivity(intent)
    }

    inner class RoomListAdapter(private val listData: List<RoomData>) :
        RecyclerView.Adapter<RoomListAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.name)
            val locationTextView: TextView = itemView.findViewById(R.id.location)
            val infoTextView: TextView = itemView.findViewById(R.id.info)
            val priceTextView: TextView = itemView.findViewById(R.id.price)
            val heartbutton : ImageButton = itemView.findViewById(R.id.Heart)
            val emptyheartbutton : ImageButton = itemView.findViewById(R.id.EmptyHeart)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.roomlist, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val roomData = listData[position]
            holder.nameTextView.text = roomData.roomtitle
            holder.locationTextView.text = roomData.adress
            holder.infoTextView.text = " ${roomData.housesort},  ${roomData.sort_of_floor}"
            holder.priceTextView.text = " ${roomData.gurantee},  ${roomData.monthfee}"

            // 서버에 데이터를 전송하여 해당 방이 사용자의 즐겨찾기 목록에 있는지 확인
            val checkFavoriteUrl = "http://capstone123.dothome.co.kr/favorite_check.php"
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
                    params["roomid"] = roomData.roomid
                    return params
                }
            }

            val checkFavoriteQueue = Volley.newRequestQueue(this@InterestList)
            checkFavoriteQueue.add(checkFavoriteRequest)

            // 채팅방 클릭 이벤트 처리
            holder.itemView.setOnClickListener {
                val intent = Intent(this@InterestList, RoomDetail::class.java)
                intent.putExtra("roomid", roomData.roomid)
                intent.putExtra("host",roomData.host)
                startActivity(intent)
            }

            holder.emptyheartbutton.setOnClickListener {
                val roomData = listData[position]
                val roomid = roomData.roomid
                val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                val uid = sharedPreferences.getString("uid", "")

                val task = InsertData()
                task.execute(
                    "http://capstone123.dothome.co.kr/favorite2.php",
                    uid,
                    roomid

                )

                // 가시성 변경 후 서버에 추가 요청 결과를 다시 확인
                holder.emptyheartbutton.visibility = View.GONE
                holder.heartbutton.visibility = View.VISIBLE
            }

            holder.heartbutton.setOnClickListener {
                val roomData = listData[position]
                val roomid = roomData.roomid
                val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                val uid = sharedPreferences.getString("uid", "")

                val task = InsertData()
                task.execute(
                    "http://capstone123.dothome.co.kr/deletefavorite.php",
                    uid,
                    roomid

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
        val roomtitle: String,
        val adress: String,
        val housesort: String,
        val sort_of_floor: String,
        val gurantee: String,
        val monthfee: String,
        val roomid: String,
        val host: String
    )


    private class InsertData : AsyncTask<String, Void, String>() {


        override fun doInBackground(vararg params: String?): String {

            val serverURL: String? = params[0]
            val uid: String? = params[1]
            val roomid: String? = params[2]


            val postParameters: String = "uid=$uid&roomid=$roomid"

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
