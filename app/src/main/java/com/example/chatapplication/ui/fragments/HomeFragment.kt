package com.example.chatapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.R
import com.example.chatapplication.data.entity.Message
import com.example.chatapplication.data.entity.User
import com.example.chatapplication.databinding.FragmentHomeBinding

import com.example.chatapplication.ui.adapter.FriendsAdapter
import com.example.chatapplication.ui.viewmodels.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var dbRef : DatabaseReference
    private lateinit var  viewModel : HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        binding.homeFragment = this

        verifyUserCheckedIn()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        try{viewModel.friendsChatList.observe(viewLifecycleOwner){

            Log.e("testIt", it.toString())
            val adapter = FriendsAdapter( requireContext(), it)
            binding.recyclerView.adapter = adapter
        }}catch (e:java.lang.Exception){
            Log.e("exception", e.toString())
        }


        binding.btnSignOut.setOnClickListener {
            Firebase.auth.signOut()

            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())

        }
        binding.btnNewMessage.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToNewMessageFragment())
        }

        return binding.root

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : HomeViewModel by viewModels()
        viewModel = tempViewModel
    }

private fun verifyUserCheckedIn(){
    val uid = Firebase.auth.uid
    if (uid == null){
      Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }
}



}