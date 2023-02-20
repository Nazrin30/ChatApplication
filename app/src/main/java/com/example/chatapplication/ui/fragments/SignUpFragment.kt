package com.example.chatapplication.ui.fragments

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.chatapplication.data.entity.User
import com.example.chatapplication.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentSignUpBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        binding.btnSignUp.setOnClickListener {
            val email = binding.emailText2.text.toString()
            val password = binding.passwordText2.text.toString()
            val name = binding.usernameText.text.toString()
            signUp(name, email, password)
        }

        binding.textView.setOnClickListener{
            Navigation.findNavController(it).navigate(SignUpFragmentDirections.toLogin())
        }

        binding.btnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            Log.e("Register fragment", "startActivitybefore")
            startActivityForResult(intent, 0)
            Log.e("Register fragment", "startActivityafter")
        }
        return binding.root
    }

    fun signUp( name: String, email: String,password : String ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful)
                {
                    //val user = auth.currentUser
                    uploadImagetoDatabase(name, email)
                    Navigation.findNavController(requireView()).navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
                }else{
                    Toast.makeText(requireContext(), "Registration process failed", Toast.LENGTH_SHORT).show()
                    Log.e("firebase", task.exception.toString() )
                }


            }
    }
    var selectedPhotoUri : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val resolver = requireActivity().contentResolver
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            Log.e("Register fragment", " Photo was selected")
            selectedPhotoUri = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(resolver, selectedPhotoUri)
            val bitmapDrawable = BitmapDrawable(this.resources, bitmap)
            Log.e("Register fragment", bitmapDrawable.toString())
            binding.profilePhoto.setImageBitmap(bitmap)
            binding.btnSelectPhoto.alpha = 0f
            Log.e("Register fragment", binding.btnSelectPhoto.background.toString() )

        }
    }
//   fun addUserToDatabase(name : String, email : String, uid :String){
//      dbRef = FirebaseDatabase.getInstance().getReference()
//       dbRef.child("path").child(uid).setValue(User(name, email, uid))
//   }
   fun uploadImagetoDatabase(name : String, email : String){
      if(selectedPhotoUri == null) return
      val filename = UUID.randomUUID().toString()
      val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
      ref.putFile(selectedPhotoUri!!)
          .addOnSuccessListener {
              Log.e("Register fragment", "Succesfully uploaded ${it.metadata?.path}")

              ref.downloadUrl.addOnSuccessListener {
                  it.toString()
                  Log.e("Register fragment", "File location : $it")
                  saveUserToDatabase(name, email, it.toString())
              }
          }



   }
   fun saveUserToDatabase (name: String, email: String, profilePhoto : String)  {
       val uid = auth.uid
       val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
       val user = User(name, email, uid, profilePhoto)
       ref.setValue(user)
           .addOnSuccessListener {
               Log.e("Register Fragment", "We saved user to db")
           }
   }

    }
