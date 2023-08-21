package com.example.citamedica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnLogout = findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener{
            goToLogin()
        }

        val tvWelcomeMessage = findViewById<TextView>(R.id.tvWelcomeMessage)

        val userName = intent.getStringExtra("userName")
        if (userName != null) {
            val welcomeMessage = "Hola, $userName!"
            tvWelcomeMessage.text = welcomeMessage
        }

        val btnReservarCita = findViewById<Button>(R.id.btn_reservar_cita)
        btnReservarCita.setOnClickListener{
            goToCreatAppointment()
        }

        val btnMisCitas = findViewById<Button>(R.id.btn_mis_citas)
        btnMisCitas.setOnClickListener{
            goToMyAppointments()
        }

    }
    private fun goToLogin(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun goToCreatAppointment(){
        val i = Intent(this, CreateAppointmentActivity::class.java)
        startActivity(i)
    }

    private fun goToMyAppointments(){
        val intent = Intent(this, AppointmentsActivity::class.java )
        startActivity(intent)
    }


}


