package com.example.register.Mypage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.register.FindRoom.RoomDetail
import com.example.register.FindRoom.RoomList
import com.example.register.Profile
import com.example.register.R

class MyRoomList : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private lateinit var uid: String
    private lateinit var listData: MutableList<RoomData>
    private lateinit var adapter: RoomListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_room_list)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("login", Context.MODE_PRIVATE)
        uid = sharedPreferences.getString("uid", "") ?: ""

        val url = "http://capstone123.dothome.co.kr/userroomlist.php?uid=$uid"

        queue = Volley.newRequestQueue(this)
        listData = mutableListOf()
        adapter = RoomListAdapter(listData)

        val recyclerView = findViewById<RecyclerView>(R.id.myscrap_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fetchData(url)
    }

    private fun fetchData(url: String) {
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                listData.clear()
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val roomtitle = jsonObject.getString("roomtitle")
                    val address = jsonObject.getString("adress")
                    val housesort = jsonObject.getString("housesort")
                    val sort_of_floor = jsonObject.getString("sort_of_floor")
                    val guarantee = jsonObject.getString("gurantee")
                    val monthfee = jsonObject.getString("monthfee")
                    val roomid = jsonObject.getString("roomid")
                    val roomData = RoomData(roomtitle, address, housesort, sort_of_floor, guarantee, monthfee, roomid)
                    listData.add(roomData)
                }
                adapter.notifyDataSetChanged() // 리사이클러뷰 갱신
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
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.myroomlist, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val roomData = listData[position]
            holder.nameTextView.text = roomData.roomtitle
            holder.locationTextView.text = roomData.adress
            holder.infoTextView.text = " ${roomData.housesort},  ${roomData.sort_of_floor}"
            holder.priceTextView.text = " ${roomData.gurantee},  ${roomData.monthfee}"

            holder.itemView.setOnLongClickListener {
                showDeleteConfirmationDialog(roomData.roomid)
                true
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(this@MyRoomList, MyRoomDetail::class.java)
                intent.putExtra("roomid", roomData.roomid)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return listData.size
        }
    }

    private fun showDeleteConfirmationDialog(roomId: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("삭제 확인")
            .setMessage("해당 방 정보를 삭제하시겠습니까?")
            .setPositiveButton("예") { _, _ ->
                deleteRoomInfo(roomId)
                val intent = Intent(this@MyRoomList, MyRoomList::class.java)
                intent.putExtra("uid", uid )
                startActivity(intent)
                finish()
            }
            .setNegativeButton("취소", null)
            .create()
            .show()
    }

    private fun deleteRoomInfo(roomId: String) {
        val url = "http://capstone123.dothome.co.kr/deleteroom.php?roomid=$roomId"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                // 방 정보 삭제 성공
                listData.removeAll { it.roomid == roomId }
                adapter.notifyDataSetChanged() // 리사이클러뷰 갱신
            },
            { error ->
                // 방 정보 삭제 실패
            })

        queue.add(jsonArrayRequest)
    }

    data class RoomData(
        val roomtitle: String,
        val adress: String,
        val housesort: String,
        val sort_of_floor: String,
        val gurantee: String,
        val monthfee: String,
        val roomid: String
    )
}
