package com.pesan.instagram.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pesan.instagram.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_signin_link.setOnClickListener { Intent(this@RegisterActivity, LoginActivity::class.java)
        }

        btn_register.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        //untuk memberikan aksi ketika text dimasukan ke dalam edit text dan datanya di jadikan ditampung didalam string
        val fullname = fullname_register.text.toString()
        val username = username_register.text.toString()
        val email = email_register.text.toString()
        val password = password_register.text.toString()

        when{
            TextUtils.isEmpty(fullname)-> Toast.makeText(this, "Fullname is required", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(username)-> Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(email)-> Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(password)-> Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()

            else ->{
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Register")
                progressDialog.setMessage("Please wait....")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            saveUserInfo(fullname, username, email, progressDialog)
                        } else {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "error: $message", Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }

                    }

            }
        }

    }

    private fun saveUserInfo( fullname: String,
                              username: String,
                              email: String,
                              progressDialog: ProgressDialog
    ){
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = fullname.toLowerCase()
        userMap["username"] = username.toLowerCase()
        userMap["email"] = email
        //default bio dan photo profile
        userMap["bio"] = "Hey I am a Student at IDN Boarding School"
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/social-media-db094.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=f824a211-74d6-4bca-913d-fc5456f23a39"

        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    Toast.makeText(this@RegisterActivity, "Account Sudah  Dibuat", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }
    }
}