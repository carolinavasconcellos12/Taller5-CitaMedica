package com.example.citamedica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citamedica.DataBase.AppointmentsDatabaseHelper
import com.example.citamedica.util.AppointmentAdapter

class AppointmentsActivity : AppCompatActivity() {
    private lateinit var dbHelper: AppointmentsDatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        userId = intent.getIntExtra("userId", 0)

        dbHelper = AppointmentsDatabaseHelper(this)
        recyclerView = findViewById(R.id.rv_appointments)

        val appointments = dbHelper.getAppointmentsForUser(userId)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AppointmentAdapter(appointments)
    }
}
