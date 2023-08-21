package com.example.citamedica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.citamedica.PreferenceHelper.set

class RegisterActivity : AppCompatActivity() {

    lateinit var dbHelper: miSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = miSQLiteHelper(this)

        val btnRegistrar = findViewById<Button>(R.id.btn_registrarse)

        btnRegistrar.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.registro_nombre).text.toString()
            val apellido = findViewById<EditText>(R.id.registro_apellido).text.toString()
            val email = findViewById<EditText>(R.id.registro_correo).text.toString()
            val password = findViewById<EditText>(R.id.registro_password).text.toString()

            if (nombre.isNotBlank() && apellido.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                val usuario = Usuario(-1, nombre, apellido, email, password)

                dbHelper.insertUser(usuario)

                // Obtén el ID generado después de insertar el usuario
                val usuarioConId = dbHelper.getUsuarioByEmailAndPassword(email, password)

                if (usuarioConId != null) {
                    Toast.makeText(this, "Estás registrado", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MenuActivity::class.java)
                    intent.putExtra("userName", nombre)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        val tvGoLogin = findViewById<TextView>(R.id.tv_go_to_login)
        tvGoLogin.setOnClickListener{
            btnLogin()
        }
    }

    private fun btnLogin(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}
