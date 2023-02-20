package com.example.chatapplication.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.chatapplication.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
   private lateinit var binding : FragmentLoginBinding
   private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container,false)
        binding.textView2.setOnClickListener{
            Navigation.findNavController(it).navigate(LoginFragmentDirections.toSignUp())
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()
            login(email, password)
        }
        return binding.root
    }

  fun login(email:String, password: String){
     auth.signInWithEmailAndPassword(email, password)
         .addOnCompleteListener{ task ->
             if (task.isSuccessful){
                Navigation.findNavController(requireView()).navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
             }else{
                 Toast.makeText(requireContext(), "Login process failured!", Toast.LENGTH_SHORT).show()
             }

         }
  }
}