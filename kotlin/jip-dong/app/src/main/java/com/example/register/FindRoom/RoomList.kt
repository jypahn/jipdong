package com.example.register.FindRoom

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.AddRoom.RoomInsert_4
import com.example.register.R
import com.example.register.home
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import org.json.JSONObject


class RoomList : AppCompatActivity() {

    private lateinit var locationbutton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)

        locationbutton = findViewById(R.id.locationname)

        var intent = getIntent()
        locationbutton.setText(intent.getStringExtra("regionname"))

        locationbutton.setOnClickListener {
            super.onBackPressed()
        }

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }



        val queue = Volley.newRequestQueue(this)
        val url = "http://capstone123.dothome.co.kr/rommlist_4.php"

        val regionname = intent.getStringExtra("regionname")

        val jsonArrayRequest = JsonArrayRequest(GET, url, null,
            { response ->
                val listData = mutableListOf<RoomData>()
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val roomtitle = jsonObject.getString("roomtitle")
                    val adress = jsonObject.getString("adress")
                    val housesort = jsonObject.getString("housesort")
                    val sort_of_floor = jsonObject.getString("sort_of_floor")
                    val gurantee = jsonObject.getString("gurantee")
                    val roomid = jsonObject.getString("roomid")
                    val monthfee = jsonObject.getString("monthfee")
                    val host = jsonObject.getString("host")
                    if (regionname != null) {
                        if(regionname.contains("서울 전체")) {
                            if (adress.contains("서울")) {
                                val roomData = RoomData(
                                    roomtitle,
                                    adress,
                                    housesort,
                                    sort_of_floor,
                                    gurantee,
                                    monthfee,
                                    roomid,
                                    host
                                )
                                listData.add(roomData)
                            }
                        }
                        else if(regionname.contains("광주 전체")) {
                            if (adress.contains("광주")) {
                                val roomData = RoomData(
                                    roomtitle,
                                    adress,
                                    housesort,
                                    sort_of_floor,
                                    gurantee,
                                    monthfee,
                                    roomid,
                                    host
                                )
                                listData.add(roomData)
                            }
                        }
                        else if(regionname.contains("광주 광산구")) {
                            if (adress.contains("광주 광산구")) {
                                val roomData = RoomData(
                                    roomtitle,
                                    adress,
                                    housesort,
                                    sort_of_floor,
                                    gurantee,
                                    monthfee,
                                    roomid,
                                    host
                                )
                                listData.add(roomData)
                            }
                        }
                        else if(regionname.contains("광주 남구")) {
                            if (adress.contains("광주 남구")) {
                                val roomData = RoomData(
                                    roomtitle,
                                    adress,
                                    housesort,
                                    sort_of_floor,
                                    gurantee,
                                    monthfee,
                                    roomid,
                                    host
                                )
                                listData.add(roomData)
                            }
                        }
                        else if(regionname.contains("광주 동구")) {
                            if (adress.contains("광주 동구")) {
                                val roomData = RoomData(
                                    roomtitle,
                                    adress,
                                    housesort,
                                    sort_of_floor,
                                    gurantee,
                                    monthfee,
                                    roomid,
                                    host
                                )
                                listData.add(roomData)
                            }
                        }
                        else if(regionname.contains("광주 븍구")) {
                            if (adress.contains("광주 북구")) {
                                val roomData = RoomData(
                                    roomtitle,
                                    adress,
                                    housesort,
                                    sort_of_floor,
                                    gurantee,
                                    monthfee,
                                    roomid,
                                    host
                                )
                                listData.add(roomData)
                            }
                        }
                        else if(regionname.contains("광주 서구")) {
                            if (adress.contains("광주 서구")) {
                                val roomData = RoomData(
                                    roomtitle,
                                    adress,
                                    housesort,
                                    sort_of_floor,
                                    gurantee,
                                    monthfee,
                                    roomid,
                                    host
                                )
                                listData.add(roomData)
                            }
                        }
                    }
                }
                val recyclerView = findViewById<RecyclerView>(R.id.scrap_recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)
                val adapter = RoomListAdapter(listData)
                recyclerView.adapter = adapter
            },
            { error ->
                // handle error here
            })

        queue.add(jsonArrayRequest)
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

            holder.emptyheartbutton.visibility = View.VISIBLE
            holder.heartbutton.visibility = View.GONE

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

            val checkFavoriteQueue = Volley.newRequestQueue(this@RoomList)
            checkFavoriteQueue.add(checkFavoriteRequest)

            holder.itemView.setOnClickListener {
                val intent = Intent(this@RoomList, RoomDetail::class.java)
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