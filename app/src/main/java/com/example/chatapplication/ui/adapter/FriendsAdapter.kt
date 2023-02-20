package com.example.chatapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.data.entity.Message
import com.example.chatapplication.data.entity.User
import com.example.chatapplication.databinding.FriendsCardDesignBinding

class FriendsAdapter (var mcontext : Context, var friendsList : List<Message>)
    : RecyclerView.Adapter<FriendsAdapter.FriendsCardDesignHolder>(){

    inner class FriendsCardDesignHolder(var binding: FriendsCardDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsCardDesignHolder {
        var binding : FriendsCardDesignBinding = DataBindingUtil.inflate(LayoutInflater.from(mcontext), R.layout.friends_card_design, parent, false)
        return FriendsCardDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsCardDesignHolder, position: Int) {
        val friend = friendsList.get(position)
        holder.binding.friend = friend

    }

    override fun getItemCount(): Int {
       return friendsList.size
    }
}