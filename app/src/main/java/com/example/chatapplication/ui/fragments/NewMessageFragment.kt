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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.R
import com.example.chatapplication.data.entity.User
import com.example.chatapplication.databinding.FragmentNewMessageBinding
import com.example.chatapplication.ui.adapter.NewMessagesAdapter
import com.example.chatapplication.ui.viewmodels.ChatViewModel
import com.example.chatapplication.ui.viewmodels.NewMessagesViewModel
import dagger.assisted.AssistedFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewMessageFragment : Fragment()  {
    private lateinit var binding: FragmentNewMessageBinding
    private lateinit var viewModel: NewMessagesViewModel
    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_new_message, container, false)
        binding.newMessageFragment = this

        viewModel.newChatsList.observe(viewLifecycleOwner){
            val adapter = NewMessagesAdapter(requireContext(), it, chatViewModel)
            binding.recyclerViewNewMessages.adapter = adapter
            Log.e("new messages", viewModel.newChatsList.value.toString())

        }


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel :  NewMessagesViewModel by viewModels()
        viewModel = tempViewModel

    }

}