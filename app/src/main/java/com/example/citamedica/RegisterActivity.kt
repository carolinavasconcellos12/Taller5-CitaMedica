package com.example.citamedica

import com.example.citamedica.Models.Usuario
import android.content.Intent
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.citamedica.DataBase.miSQLiteHelper

class RegisterActivity : AppCompatActivity() {

    lateinit var dbHelper: miSQLiteHelper
    lateinit var registroNombre: EditText
    lateinit var registroApellido: EditText
    lateinit var registroCorreo: EditText
    lateinit var registroPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = miSQLiteHelper(this)

        registroNombre = findViewById(R.id.registro_nombre)
        registroApellido = findViewById(R.id.registro_apellido)
        registroCorreo = findViewById(R.id.registro_correo)
        registroPassword = findViewById(R.id.registro_password)

        val btnRegistrar = findViewById<Button>(R.id.btn_registrarse)

        btnRegistrar.setOnClickListener {
            val nombre = registroNombre.text.toString()
            val apellido = registroApellido.text.toString()
            val email = registroCorreo.text.toString()
            val password = registroPassword.text.toString()

            if (nombre.isNotBlank() && apellido.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                // Validar que el correo electrónico tenga un formato válido
                if (isValidEmail(email)) {
                    // Validar que la contraseña tenga al menos 8 caracteres y al menos un número
                    if (isValidPassword(password)) {
                        val existingUser = dbHelper.getUsuarioByEmail(email)

                        if (existingUser == null) {
                            // El correo electrónico no está registrado, puedes proceder con el registro
                            val usuario = dbHelper.insertUser(Usuario(-1, nombre, apellido, email, password))
                            Toast.makeText(this, "Estás registrado", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, MenuActivity::class.java)
                            intent.putExtra("userId", usuario.id)  // Pasar el ID del usuario a la actividad del menú
                            intent.putExtra("userName", nombre)
                            startActivity(intent)

                            clearFields()
                        } else {
                            // El correo electrónico ya está registrado
                            Toast.makeText(this, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres y al menos un número", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        val tvGoLogin = findViewById<TextView>(R.id.tv_go_to_login)
        tvGoLogin.setOnClickListener {
            btnLogin()
        }
    }

    private fun btnLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun clearFields() {
        registroNombre.text.clear()
        registroApellido.text.clear()
        registroCorreo.text.clear()
        registroPassword.text.clear()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        // Verificar que la contraseña tenga al menos 8 caracteres y al menos un número
        return password.length >= 8 && password.any { it.isDigit() }
    }
}

