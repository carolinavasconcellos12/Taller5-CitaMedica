package com.example.citamedica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvGoRegister = findViewById<TextView>(R.id.btn_register)
        tvGoRegister.setOnClickListener {
            goToRegister()
        }

        val dbHelper = miSQLiteHelper(this)

        val btnLogin = findViewById<Button>(R.id.btn_iniciarsesion)
        btnLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.etCorreoLoguin).text.toString()
            val password = findViewById<EditText>(R.id.etPassLoguin).text.toString()

            val usuario = dbHelper.getUsuarioByEmailAndPassword(email, password)

            if (usuario != null) {
                val userName = usuario.nombre

                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("userName", userName)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }
}
