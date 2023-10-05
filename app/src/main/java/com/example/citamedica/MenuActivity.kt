// MenuActivity.kt
package com.example.citamedica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    private var userId: Int = 0 // Debes obtener este ID de alguna manera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Obtiene el ID del usuario desde la actividad anterior (por ejemplo, MainActivity)
        userId = intent.getIntExtra("userId", 0)

        val btnLogout = findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            goToLogin()
        }

        val tvWelcomeMessage = findViewById<TextView>(R.id.tvWelcomeMessage)

        val userName = intent.getStringExtra("userName")
        if (userName != null) {
            val welcomeMessage = "Hola, $userName!"
            tvWelcomeMessage.text = welcomeMessage
        }

        val btnReservarCita = findViewById<Button>(R.id.btn_reservar_cita)
        btnReservarCita.setOnClickListener {
            goToCreateAppointment()
        }

        val btnMisCitas = findViewById<Button>(R.id.btn_mis_citas)
        btnMisCitas.setOnClickListener {
            goToAppointments()
        }
    }

    private fun goToLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun goToCreateAppointment() {
        val i = Intent(this, CreateAppointmentActivity::class.java)
        i.putExtra("userId", userId) // Pasa el ID del usuario a la actividad de creación de citas
        startActivity(i)
    }

    private fun goToAppointments() {
        val intent = Intent(this, AppointmentsActivity::class.java)
        intent.putExtra("userId", userId) // Pasa el ID del usuario a la actividad de citas
        startActivity(intent)
    }
}