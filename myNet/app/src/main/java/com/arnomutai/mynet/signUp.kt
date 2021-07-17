package com.arnomutai.mynet

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var btn : Button
private lateinit var signup_btn: Button
private lateinit var fname: EditText
private lateinit var username: EditText
private lateinit var email: EditText
private lateinit var password: EditText
class signUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn = findViewById(R.id.signup_signin_btn)
        signup_btn = findViewById(R.id.signup_btn)
        fname = findViewById(R.id.fuser_signup)
        username = findViewById(R.id.username_signup)
        email = findViewById(R.id.email_signup)
        password = findViewById(R.id.password_signup)

        btn.setOnClickListener {
            startActivity(Intent(this, signIn::class.java))
        }
        signup_btn.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        val fullname = fname.text.toString()
         val userName = username.text.toString()
         val useremail = email.text.toString()
         val pass = password.text.toString()

        when {
            TextUtils.isEmpty(fullname) -> Toast.makeText(
                this,
                "full name is required",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(
                this,
                "username is required",
                Toast.LENGTH_SHORT
            ).show()
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
                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                val progressDialog = ProgressDialog(this@signUp)
                progressDialog.setTitle("sign Up")
                progressDialog.setMessage("please wait this may take a while")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                mAuth.createUserWithEmailAndPassword(useremail, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            saveUserInfo(fullname, userName, useremail,)

                        }else{
                            val message = task.exception.toString()
                            Toast.makeText(this, "error: $message", Toast.LENGTH_SHORT).show()
                            FirebaseAuth.getInstance().signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(fullname: String, userName: String, useremail: String, ) {

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["fullname"] = fullname.lowercase()
        userMap["userName"] = userName.lowercase()
        userMap["email"] = useremail
        userMap["bio"] = "hey i love coding"
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/mynet-8dcf6.appspot.com/o/default%20images%2Fprofile.png?alt=media&token=6d7c80ed-1b53-4c23-bf2b-99dcf9fbc5ce"

        userRef.child(currentUserId).setValue(userMap)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "account created sucessfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@signUp, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                else{
                    val message = task.exception.toString()
                    Toast.makeText(this, "error: $message", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                }
            }
    }
}