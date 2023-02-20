package com.example.chatapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapplication.R
import com.example.chatapplication.data.entity.Message
import com.example.chatapplication.databinding.ReceivedMessageDesignBinding
import com.example.chatapplication.databinding.SentMessageDesignBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatAdapter (var mContext: Context, var messagesList : List<Message>)  :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val item_view_type_sent = 1
    private val item_view_type_received = 2

    inner class SentMessagesDesignHolder (var binding : SentMessageDesignBinding): RecyclerView.ViewHolder(binding.root)

    inner class RecievedMessageDesignHolder (var binding : ReceivedMessageDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

         if (viewType == item_view_type_sent){
            val binding : SentMessageDesignBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.sent_message_design, parent, false )
            return SentMessagesDesignHolder(binding)
        }else{
            val binding : ReceivedMessageDesignBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.received_message_design, parent, false)
            return RecievedMessageDesignHolder(binding)
        }

    }

//    override fun onBindViewHolder(holder: SentMessagesDesignHolder, position: Int) {
//        val message = messagesList.get(position)
//    }

    override fun getItemCount(): Int {
       return messagesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messagesList.get(position)
        if (holder.itemViewType == item_view_type_sent){
            val viewHolder = holder as SentMessagesDesignHolder
            viewHolder.binding.sentMessage.text = message.text
        }else{
            val viewHolder = holder as RecievedMessageDesignHolder
            viewHolder.binding.receivedMessage.text = message.text
        }


    }

    override fun getItemViewType(position: Int): Int {
        val message = messagesList.get(position)
        return if (message.fromId == Firebase.auth.currentUser?.uid){
            item_view_type_sent
        }else
            item_view_type_received
    }
}