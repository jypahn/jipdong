package com.example.register.chatting

import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.register.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private val TYPE_SENT_MESSAGE = 101
    private val TYPE_RECEIVED_MESSAGE = 102

    private var dataSet = ArrayList<MessageData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(getViewSrc(viewType), parent, false)
        return ViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun addMessage(messageData: MessageData) {
        dataSet.add(messageData)
        notifyItemInserted(dataSet.size - 1)
    }

    fun submitData(newData: ArrayList<MessageData>) {
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

    private fun getViewSrc(viewType: Int): Int {
        return if (viewType == TYPE_RECEIVED_MESSAGE) {
            R.layout.recycler_received_message
        } else {
            R.layout.recycler_sent_message
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position].name == "user") {
            TYPE_SENT_MESSAGE
        } else {
            TYPE_RECEIVED_MESSAGE
        }
    }

    inner class ViewHolder(itemView: View, private val viewType: Int) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MessageData) {
            if (viewType == TYPE_SENT_MESSAGE) {
                bindSentMessage(item)
            } else if (viewType == TYPE_RECEIVED_MESSAGE) {
                bindReceivedMessage(item)
            }
        }


        private fun timeMessage(item: MessageData) {
            val rSentMessage = itemView.findViewById<TextView>(R.id.r_sent_message)
            val rTimestamp = itemView.findViewById<TextView>(R.id.r_timestamp)
            rSentMessage.text = item.message
            rTimestamp.text = item.timestamp
        }


        private fun bindSentMessage(item: MessageData) {
            val rSentMessage = itemView.findViewById<TextView>(R.id.r_sent_message)
            val rTimestamp = itemView.findViewById<TextView>(R.id.r_timestamp)
            rSentMessage.text = item.message
            rTimestamp.text = item.timestamp
        }


        private fun bindReceivedMessage(item: MessageData) {
            val lReceivedMessage = itemView.findViewById<TextView>(R.id.l_received_message)
            val lTimestamp = itemView.findViewById<TextView>(R.id.l_timestamp)
            val lName = itemView.findViewById<TextView>(R.id.l_name)
            lReceivedMessage.text = item.message
            lTimestamp.text = item.timestamp
            val name = item.name
            val url = "http://capstone123.dothome.co.kr/getusername.php?uid=$name"
            val username = FetchUsernameTask().execute(url).get()
            lName.text = username
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
}
