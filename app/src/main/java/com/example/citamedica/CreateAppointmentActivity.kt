package com.example.citamedica

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.example.citamedica.DataBase.AppointmentsDatabaseHelper
import com.example.citamedica.util.DatePickerHelper
import com.example.citamedica.util.TimerPickerHelper
import com.google.android.material.snackbar.Snackbar

class CreateAppointmentActivity : AppCompatActivity() {
    private lateinit var etScheduledDate: EditText
    private lateinit var etScheduledTime: EditText
    private var userId: Int = 0

    val optionsAddress = arrayOf("Hidalgo 1250", "Gorriti 6005")
    val geoAddress = arrayOf("-34.6056983, -58.4474221","-34.5815155,-58.4439568")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appointment)

        var spinnerAddress = findViewById<Spinner>(R.id.spinner_domicilio)

        val btnNext = findViewById<Button>(R.id.btn_siguiente)
        val btnNext2 = findViewById<Button>(R.id.btn_siguiente_dos)
        val btnConfirm = findViewById<Button>(R.id.btn_confirmar)

        val cvNext = findViewById<CardView>(R.id.cv_siguiente)
        val cvConfirm = findViewById<CardView>(R.id.cv_confirmar)
        val cvResumen = findViewById<CardView>(R.id.cv_resumen)

        val etDescription = findViewById<EditText>(R.id.et_description)
        etScheduledDate = findViewById(R.id.et_fecha)
        etScheduledTime = findViewById(R.id.et_hora)

        val linearLayoutCreateAppointment = findViewById<LinearLayout>(R.id.linearLayout_create_appointment)

        etScheduledDate.setOnClickListener {
            showDatePickerDialog()
        }

        etScheduledTime.setOnClickListener {
            showTimePickerDialog()
        }

        btnNext.setOnClickListener {
            if (etDescription.text.toString().length < 3) {
                etDescription.error = "La descripción es demasiado corta"
            } else {
                cvNext.visibility = View.GONE
                cvConfirm.visibility = View.VISIBLE
            }
        }

        btnNext2.setOnClickListener {
            if (etScheduledDate.text.toString().isEmpty() || etScheduledTime.text.toString().isEmpty()) {
                Snackbar.make(
                    linearLayoutCreateAppointment,
                    "Debe seleccionar una fecha y hora para la cita",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                showAppointmentDataToConfirm()
                cvConfirm.visibility = View.GONE
                cvResumen.visibility = View.VISIBLE
            }
        }

        val locationMaps = findViewById<ImageView>(R.id.location)
        locationMaps.setOnClickListener{
            // Abre Google Maps Street View para la ubicación de Hidalgo
            openStreetView(spinnerAddress.selectedItemPosition)
        }


        val spinnerDoctor = findViewById<Spinner>(R.id.spinner_medico)

        btnConfirm.setOnClickListener {
            // Inserta la cita médica en la base de datos utilizando la función insertAppointment
            val dbHelper = AppointmentsDatabaseHelper(this)
            val doctorName = spinnerDoctor.selectedItem.toString()
            val scheduledDate = etScheduledDate.text.toString()
            val scheduledTime = etScheduledTime.text.toString()

            // Obtener el ID de usuario de la actividad anterior
            userId = intent.getIntExtra("userId", 0)

            val newRowId = dbHelper.insertAppointment(userId, doctorName, scheduledDate, scheduledTime)

            if (newRowId != -1L) {
                Toast.makeText(applicationContext, "Cita realizada exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "Error al programar la cita", Toast.LENGTH_SHORT).show()
            }
        }




        spinnerAddress.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsAddress)

        val optionsDoctor = arrayOf("Laura Gutierrez", "Carmen Abaldo")
        spinnerDoctor.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsDoctor)
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerHelper.newInstance { _, year, month, day ->
            fechaSeleccionada(day, month, year)
        }
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun showTimePickerDialog() {
        val newFragment = TimerPickerHelper.newInstance { _, hourOfDay, minute ->
            horaSeleccionada(hourOfDay, minute)
        }
        newFragment.show(supportFragmentManager, "timePicker")
    }

    private fun fechaSeleccionada(day: Int, month: Int, year: Int) {
        val selectedDate = "$day/${month + 1}/$year"
        etScheduledDate.setText(selectedDate)
    }

    private fun horaSeleccionada(hourOfDay: Int, minute: Int) {
        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        etScheduledTime.setText(selectedTime)
    }

    private fun showAppointmentDataToConfirm() {
        val tvConfirmDescription = findViewById<TextView>(R.id.tv_resumen_sintomas)
        val tvConfirmAddress = findViewById<TextView>(R.id.tv_resumen_domicilio)
        val tvConfirmDoctor = findViewById<TextView>(R.id.tv_resumen_medico)
        val tvConfirmDate = findViewById<TextView>(R.id.tv_resumen_fecha)
        val tvConfirmTime = findViewById<TextView>(R.id.tv_resumen_hora)

        val description = findViewById<EditText>(R.id.et_description).text.toString()
        val address = findViewById<Spinner>(R.id.spinner_domicilio).selectedItem.toString()
        val doctor = findViewById<Spinner>(R.id.spinner_medico).selectedItem.toString()
        val date = etScheduledDate.text.toString()
        val time = etScheduledTime.text.toString()

        tvConfirmDescription.text = description
        tvConfirmAddress.text = address
        tvConfirmDoctor.text = doctor
        tvConfirmDate.text = date
        tvConfirmTime.text = time
    }

    fun openWhatsApp(view: View) {
        val phoneNumber = "+541135564769"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data =
            Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=Hola,%20tengo%20una%20duda...")
        startActivity(intent)
    }



    // Función para abrir Google Maps en una ubicación específica
    private fun openStreetView(arrayPosition: Int) {
        val geoPosition = geoAddress.get(arrayPosition)
        val gmmIntentUri = Uri.parse("geo:$geoPosition?z=20")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(packageManager)?.let {
            startActivity(mapIntent)
        }

    }

}