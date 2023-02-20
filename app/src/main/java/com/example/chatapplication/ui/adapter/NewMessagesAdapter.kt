package com.example.chatapplication.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapplication.R
import com.example.chatapplication.data.entity.User
import com.example.chatapplication.databinding.FragmentSignUpBinding
import com.example.chatapplication.databinding.NewMessageCardDesignBinding
import com.example.chatapplication.ui.fragments.NewMessageFragment
import com.example.chatapplication.ui.fragments.NewMessageFragmentDirections
import com.example.chatapplication.ui.viewmodels.ChatViewModel


class NewMessagesAdapter (var mContext: Context, var chatsList : List<User>, val viewModel: ChatViewModel)
    : RecyclerView.Adapter<NewMessagesAdapter.NewMessagesCardDesignHolder>() {

    inner class NewMessagesCardDesignHolder(var binding: NewMessageCardDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMessagesCardDesignHolder {
       var binding : NewMessageCardDesignBinding = DataBindingUtil
           .inflate(LayoutInflater.from(mContext), R.layout.new_message_card_design, parent, false)
        return NewMessagesCardDesignHolder(binding)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: NewMessagesCardDesignHolder, position: Int) {
       val newChat = chatsList.get(position)
        Log.e("new messages", newChat.toString())
       holder.binding.newChat = newChat
       var url = newChat.profilePhoto
       Glide.with(mContext).load(url).into(holder.binding.userProfilePhoto)
       holder.binding.newMessafeCard.setOnClickListener{
           viewModel.user.value = newChat
           Navigation.findNavController(it).navigate(NewMessageFragmentDirections.actionNewMessageFragmentToChatFragment(newChat))

           Log.e("Test chat6", viewModel.user.value.toString())
       }
    }

    override fun getItemCount(): Int {
        return chatsList.size
    }
}