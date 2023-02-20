package com.example.chatapplication

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.chatapplication.databinding.ActivityMainBinding
import com.example.chatapplication.databinding.FragmentSignUpBinding
import com.example.chatapplication.ui.fragments.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
    }


}