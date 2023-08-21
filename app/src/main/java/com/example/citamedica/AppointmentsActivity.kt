package com.example.citamedica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppointmentsActivity : AppCompatActivity() {
    private lateinit var dbHelper: AppointmentsDatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private var userId: Int = 0 // Reemplaza esto con la lógica para obtener el ID de usuario conectado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val userName = intent.getStringExtra("userName")
        userId = obtenerUserIdDesdeLaBaseDeDatos(userName)

        dbHelper = AppointmentsDatabaseHelper(this)
        recyclerView = findViewById(R.id.rv_appointments)

        // Recupera las citas médicas del usuario actual de la base de datos
        val appointments = dbHelper.getAppointmentsForUser(userId)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AppointmentAdapter(appointments)
    }

    private fun obtenerUserIdDesdeLaBaseDeDatos(userName: String?): Int {
        val dbHelper = miSQLiteHelper(this)
        val usuario = dbHelper.getUsuarioByEmailAndPassword(userName ?: "", "") // Reemplaza con tu lógica de obtención de usuario
        return usuario?.id ?: -1
    }
}

