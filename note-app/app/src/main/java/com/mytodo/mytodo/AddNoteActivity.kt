package com.shaluambasta.noteapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.shaluambasta.noteapp.db.DBOpenHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddNoteActivity : AppCompatActivity() {

    private lateinit var etTitle: TextInputLayout
    private lateinit var etDescription: TextInputLayout
    private lateinit var fabSend: FloatingActionButton
    private val dbOpenHelper = DBOpenHelper(this)
    private var selectedDateTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        etTitle = findViewById(R.id.et_title)
        etDescription = findViewById(R.id.et_description)
        fabSend = findViewById(R.id.fab_send)

        fabSend.setOnClickListener {
            fabSendData()
        }
        val buttonDateTime = findViewById<Button>(R.id.btn_date_time)
        buttonDateTime.setOnClickListener {
            showDateTimePicker()
        }
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)

                val timePickerDialog = TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)

                        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                        selectedDateTime = dateFormat.format(calendar.time)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                )
                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun fabSendData() {
        val title = etTitle.editText?.text.toString().trim()
        val description = etDescription.editText?.text.toString().trim()

        if (title.isEmpty()) {
            etTitle.error = "Please enter your Title"
            etTitle.requestFocus()
            return
        }

        if (description.isEmpty()) {
            etDescription.error = "Please enter your Description"
            etDescription.requestFocus()
            return
        }

        if (selectedDateTime.isEmpty()) {
            Toast.makeText(this, "Please select Date and Time", Toast.LENGTH_SHORT).show()
            return
        }

        dbOpenHelper.addNote(
            title,
            description,

        )

        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
        val intentToMainActivity = Intent(this, MainActivity::class.java)
        startActivity(intentToMainActivity)
        finish()
    }




    private fun notEmpty(): Boolean {
        return (etTitle.editText?.text.toString().isNotEmpty()
                && etDescription.editText?.text.toString().isNotEmpty())
    }

}