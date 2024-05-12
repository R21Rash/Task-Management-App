package com.mytodo.mytodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.shaluambasta.noteapp.MainActivity
import com.shaluambasta.noteapp.R

class MainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        // Create a Handler object
        val handler = Handler()

        // Post a delayed action to start MainActivity after 3 seconds
        handler.postDelayed({
            // Create an Intent to start MainActivity
            val intent = Intent(this@MainActivity1, MainActivity::class.java)
            // Start the MainActivity
            startActivity(intent)
            // Finish MainActivity1 to prevent it from being in the back stack
            finish()
        }, 3000) // Delay in milliseconds (3 seconds)
    }
}
