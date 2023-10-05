package com.example.citamedica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.citamedica.DataBase.miSQLiteHelper

class MainActivity : AppCompatActivity() {

    lateinit var dbHelper: miSQLiteHelper
    lateinit var etCorreoLoguin: EditText
    lateinit var etPassLoguin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = miSQLiteHelper(this)
        etCorreoLoguin = findViewById(R.id.etCorreoLoguin)
        etPassLoguin = findViewById(R.id.etPassLoguin)

        val tvGoRegister = findViewById<TextView>(R.id.btn_register)
        tvGoRegister.setOnClickListener {
            goToRegister()
        }

        val btnLogin = findViewById<Button>(R.id.btn_iniciarsesion)
        btnLogin.setOnClickListener {
            val email = etCorreoLoguin.text.toString()
            val password = etPassLoguin.text.toString()

            val usuario = dbHelper.getUsuarioByEmailAndPassword(email, password)

            if (usuario != null) {
                val userId = usuario.id  // Obtener el ID del usuario
                val userName = usuario.nombre

                val intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("userId", userId)  // Pasar el ID del usuario a la actividad del menú
                intent.putExtra("userName", userName)
                startActivity(intent)

                clearFields()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }

    private fun clearFields() {
        etCorreoLoguin.text.clear()
        etPassLoguin.text.clear()
    }
}
