package com.example.citamedica

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppointmentsDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Appointments.db"
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "appointments"
        const val COLUMN_ID = "id"
        const val COLUMN_DOCTOR_NAME = "doctor_name"
        const val COLUMN_SCHEDULED_DATE = "scheduled_date"
        const val COLUMN_SCHEDULED_TIME = "scheduled_time"
        const val COLUMN_USER_ID = "user_id" // Agregamos la columna user_id
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_DOCTOR_NAME TEXT," +
                "$COLUMN_SCHEDULED_DATE TEXT," +
                "$COLUMN_SCHEDULED_TIME TEXT," +
                "$COLUMN_USER_ID INTEGER)")

        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Método para insertar una cita médica en la base de datos asociada a un usuario
    fun insertAppointment(doctorName: String, scheduledDate: String, scheduledTime: String, userId: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_DOCTOR_NAME, doctorName)
        values.put(COLUMN_SCHEDULED_DATE, scheduledDate)
        values.put(COLUMN_SCHEDULED_TIME, scheduledTime)
        values.put(COLUMN_USER_ID, userId) // Asociamos la cita con un usuario
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    // Método para obtener todas las citas médicas de un usuario
    fun getAppointmentsForUser(userId: Int): ArrayList<Appointment> {
        val appointmentsList = ArrayList<Appointment>()
        val db = this.readableDatabase

        val projection = arrayOf(
            COLUMN_ID,
            COLUMN_DOCTOR_NAME,
            COLUMN_SCHEDULED_DATE,
            COLUMN_SCHEDULED_TIME
        )

        val selection = "$COLUMN_USER_ID = ?"
        val selectionArgs = arrayOf(userId.toString())

        val cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val doctorName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_NAME))
            val scheduledDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCHEDULED_DATE))
            val scheduledTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCHEDULED_TIME))

            appointmentsList.add(Appointment(id.toInt(), doctorName, scheduledDate, scheduledTime))
        }

        cursor.close()
        db.close()

        return appointmentsList
    }
}
