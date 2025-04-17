package com.firstapp.googlesigninapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class HomeActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("345560598741-72u7hp8lf7oe8f0umqkr8jjsq9ldc6uv.apps.googleusercontent.com")
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        val userName = intent.getStringExtra("USER_NAME")
        welcomeTextView.text = "Welcome, $userName!"

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
} 