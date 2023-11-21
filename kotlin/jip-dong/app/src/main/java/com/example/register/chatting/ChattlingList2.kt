package com.example.register.chatting

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
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.register.Mypage.InterestList
import com.example.register.Profile
import com.example.register.R
import com.example.register.home
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ChattlingList2 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatRoomAdapter
    private val chatRooms = ArrayList<ChatRoom>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chattling_list2)

        recyclerView = findViewById(R.id.chattinglist)
        adapter = ChatRoomAdapter(chatRooms)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val url = "http://capstone123.dothome.co.kr/chatroomlist2.php?uid=$uid" // PHP 파일의 URL 설정
        FetchChatRoomsTask().execute(url)


        val profilebutton = findViewById<ImageButton>(R.id.profilebutton)

        fun movetoprofilepage(){
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        profilebutton.setOnClickListener{
            movetoprofilepage()
            overridePendingTransition(0, 0)
            finish()

        }


        val homebutton = findViewById<ImageButton>(R.id.homebutton)

        fun movetohomepage(){
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }

        homebutton.setOnClickListener{
            movetohomepage()
            overridePendingTransition(0, 0)
            finish()
        }

        val favoritebutton = findViewById<ImageButton>(R.id.heartbutton)

        fun movetofavoritepage(){
            val intent = Intent(this, InterestList::class.java)
            startActivity(intent)
        }

        favoritebutton.setOnClickListener{
            movetofavoritepage()
            overridePendingTransition(0, 0)
            finish()

        }

    }

    inner class FetchChatRoomsTask : AsyncTask<String, Void, String>() {
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

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
            val uid = sharedPreferences.getString("uid", "")
            try {
                val jsonArray = JSONArray(result)

                // 채팅방 목록 생성
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val id = jsonObject.getString("id")
                    val user1 = jsonObject.getString("user1")
                    val user2 = jsonObject.getString("user2")
                    val lastMessage = jsonObject.getString("last_message")
                    val timestamp = jsonObject.getString("timestamp")
                    val currentUser = if (user1 == uid) user2 else user1


                    // 쿼리문 실행하여 u_name 값 가져오기
                    val url = "http://capstone123.dothome.co.kr/getusername.php?uid=$currentUser"
                    val username = FetchUsernameTask().execute(url).get()
                    val chatRoom = ChatRoom(id, user1, user2, lastMessage, timestamp, currentUser, username)
                    chatRooms.add(chatRoom)
                }

                // 어댑터에 데이터 변경 알림
                adapter.notifyDataSetChanged()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
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

    data class ChatRoom(
        val id: String,
        val user1: String,
        val user2: String,
        val lastMessage: String,
        val timestamp: String,
        val currentUser: String,
        val username: String
    )
    inner class ChatRoomAdapter(private val chatRooms: List<ChatRoom>) : RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {
            val chatImage: ImageView = itemView.findViewById(R.id.chatimage)
            val chatId: TextView = itemView.findViewById(R.id.chatid)
            val chatMessage: TextView = itemView.findViewById(R.id.chatmessage)
            val chatDate: TextView = itemView.findViewById(R.id.chatdate)

            init {
                // View의 LongClick 이벤트 리스너 설정
                itemView.setOnLongClickListener(this)
            }

            override fun onLongClick(view: View): Boolean {
                val context = view.context
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder.setMessage("나가시겠습니까?")
                    .setPositiveButton("나가기") { dialog, _ ->
                        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                        val uid = sharedPreferences.getString("uid", "")
                        val chatRoom = chatRooms[adapterPosition]
                        val url = "http://capstone123.dothome.co.kr/quitchat.php?chatroom_id=${chatRoom.id}&user_id=$uid"
                        LeaveChatRoomTask().execute(url)
                        dialog.dismiss()
                    }
                    .setNegativeButton("취소") { dialog, _ ->
                        dialog.dismiss()
                    }
                val alertDialog = dialogBuilder.create()
                alertDialog.show()
                return true
            }
        }
        inner class LeaveChatRoomTask : AsyncTask<String, Void, String>() {
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

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                val intent = intent
                finish()
                startActivity(intent)
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.chatlist, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val chatRoom = chatRooms[position]

            // 채팅방 데이터 설정
            holder.chatId.text = chatRoom.username
            if (chatRoom.lastMessage == "null" ){
                holder.chatMessage.text = "대화 내용이 없습니다"
            }else {
                holder.chatMessage.text = chatRoom.lastMessage
            }
            if(chatRoom.timestamp == "null") {
                holder.chatDate.text = ""
            }else{
                holder.chatDate.text = chatRoom.timestamp
            }
            // 나머지 작업 수행
            // 예를 들어 채팅방 이미지 설정 등 추가 작업 수행
            // holder.chatImage.setImageResource(R.drawable.chat_icon)

            // 채팅방 클릭 이벤트 처리
            holder.itemView.setOnClickListener {
                val intent = Intent(this@ChattlingList2, Chatting::class.java)
                intent.putExtra("chatRoomName", chatRoom.username)
                intent.putExtra("chatRoomId", chatRoom.id.toInt()) // chatRoom.id를 Int로 변환하여 전달
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return chatRooms.size
        }
    }
}


