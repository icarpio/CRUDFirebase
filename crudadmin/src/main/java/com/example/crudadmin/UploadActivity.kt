package com.example.crudadmin

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.crudadmin.data.UserData
import com.example.crudadmin.databinding.ActivityMainBinding
import com.example.crudadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val name = binding.uploadName.text.toString()
            val operator = binding.uploadOperator.text.toString()
            val location = binding.uploadLocation.text.toString()
            val phone = binding.uploadPhone.text.toString()

            // Obtener la fecha y hora actual
            val currendate = LocalDateTime.now()
            // Formatear la fecha y hora al formato deseado
            val format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            val formatedDate = currendate.format(format)
            val registerDate = formatedDate

            databaseReference = FirebaseDatabase.getInstance().getReference("Phone Directory")
            val users = UserData(name,operator,location, phone, registerDate)
            databaseReference.child(phone).setValue(users).addOnSuccessListener {
                binding.uploadName.text.clear()
                binding.uploadOperator.text.clear()
                binding.uploadLocation.text.clear()
                binding.uploadPhone.text.clear()

                Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()

                val intent = Intent(this@UploadActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
            }


        }

    }
}