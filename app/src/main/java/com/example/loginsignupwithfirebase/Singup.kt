package com.example.loginsignupwithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginsignupwithfirebase.databinding.ActivitySingupBinding
import com.google.firebase.auth.FirebaseAuth

class Singup : AppCompatActivity() {
    private lateinit var binding: ActivitySingupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySingupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()
        binding.signupButton.setOnClickListener{
            var email=binding.signupEmail.text.toString()
            var password=binding.signupPassword.text.toString()
            var confirmPassword=binding.signupConfirm.text.toString()
            if(email.isNotEmpty()&& password.isNotEmpty()&& confirmPassword.isNotEmpty()){
                if(password==confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent= Intent(this,Login::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    Toast.makeText(this,"Password does nt match",Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this,"The fields cannot be empty",Toast.LENGTH_SHORT ).show()
            }
        }
        binding.loginRedirecText.setOnClickListener {
            val lintent= Intent(this, Login::class.java)
            startActivity(lintent)
        }
    }
}