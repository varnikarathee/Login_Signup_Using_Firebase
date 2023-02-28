package com.example.loginsignupwithfirebase

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.loginsignupwithfirebase.databinding.ActivityLoginBinding
import com.example.loginsignupwithfirebase.databinding.ActivitySingupBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener{
            var email=binding.loginEmail.text.toString()
            var password=binding.loginPassword.text.toString()

            if(email.isNotEmpty()&& password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent= Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

            }else{
                Toast.makeText(this,"The fields cannot be empty", Toast.LENGTH_SHORT ).show()
            }
        }
        //create this function and email comparison for forgot password
        binding.forgotPassword.setOnClickListener{
            val builder=AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_forgot,null)
            val userEmail=view.findViewById<EditText>(R.id.editbox)
            builder.setView(view)
            val dialog= builder.create()

            view.findViewById<Button>(R.id.btnReset).setOnClickListener{
                compareEmail(userEmail)
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btncancel).setOnClickListener {
                dialog.dismiss()
            }
            if(dialog.window!=null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()


        }
        binding.signupRedirecText.setOnClickListener {
            val sintent= Intent(this, Singup::class.java)
            startActivity(sintent)
        }
    }

    private fun compareEmail(email:EditText){
        if(email.text.toString().isEmpty()){
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
             return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener {Task->
            if(Task.isSuccessful){
                Toast.makeText(this,"Check your email",Toast.LENGTH_SHORT).show()
            }
        }

    }
}