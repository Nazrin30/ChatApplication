package com.example.chatapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.R
import com.example.chatapplication.data.entity.User
import com.example.chatapplication.databinding.FragmentChatBinding
import com.example.chatapplication.ui.adapter.ChatAdapter
import com.example.chatapplication.ui.viewmodels.ChatViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        binding.chatFragment = this
        val bundle : ChatFragmentArgs by navArgs()
        viewModel.user.value = bundle.user
        binding.user = bundle.user
        viewModel.user.observe(viewLifecycleOwner){
            viewModel.loadChat(Firebase.auth.currentUser?.uid.toString(), it.uid.toString())
        }


        viewModel.chat.observe(viewLifecycleOwner){

            val adapter = ChatAdapter(requireContext(), it)
            binding.adapter = adapter
            binding.recyclerViewMessages.layoutManager = LinearLayoutManager(context)
        }


        binding.sentButton.setOnClickListener {
            val fromID = Firebase.auth.currentUser?.uid
            val text = binding.messageToSend.text.toString()
            val currentTimestamp = System.currentTimeMillis()
            val toId = binding.user?.uid.toString()
            sent(fromID!!, text, currentTimestamp, toId)
            binding.messageToSend.text = null
        }


        return binding.root
    }



   fun sent (fromID: String, text: String, timeStamp: Long, toId: String){
       viewModel.sent(fromID, text, timeStamp, toId)
   }
}