package com.arnomutai.mynet

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

private lateinit var btn : Button
class signIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btn = findViewById(R.id.signup_login_btn)

        btn.setOnClickListener {
            startActivity(Intent(this,signUp::class.java ))
        }
        login_btn.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {

        val useremail = email_login.text.toString()
        val pass = password_login.text.toString()

        when{
            TextUtils.isEmpty(useremail) -> Toast.makeText(
                this,
                "email is required",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(pass) -> Toast.makeText(
                this,
                "password is required",
                Toast.LENGTH_SHORT
            ).show()
            else -> {

                val progressDialog = ProgressDialog(this@signIn)
                progressDialog.setTitle("sign In")
                progressDialog.setMessage("please wait this may take a while")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(useremail, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        progressDialog.dismiss()
                        val intent = Intent(this@signIn, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }else{
                        val message = task.exception.toString()
                        Toast.makeText(this, "error: $message", Toast.LENGTH_SHORT).show()
                        FirebaseAuth.getInstance().signOut()
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser !=null){
            val intent = Intent(this@signIn, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}