package com.example.citamedica.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.citamedica.Models.Appointment
import kotlinx.coroutines.awaitAll
import kotlin.math.log

class AppointmentsDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Appointments.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "appointments"
        const val COLUMN_ID = "id"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_DOCTOR_NAME = "doctor_name"
        const val COLUMN_SCHEDULED_DATE = "scheduled_date"
        const val COLUMN_SCHEDULED_TIME = "scheduled_time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USER_ID INTEGER," + // Agregamos el campo user_id
                "$COLUMN_DOCTOR_NAME TEXT," +
                "$COLUMN_SCHEDULED_DATE TEXT," +
                "$COLUMN_SCHEDULED_TIME TEXT)")

        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USER_ID INTEGER," +
                "$COLUMN_DOCTOR_NAME TEXT," +
                "$COLUMN_SCHEDULED_DATE TEXT," +
                "$COLUMN_SCHEDULED_TIME TEXT)")

        db.execSQL(CREATE_TABLE)
    }

    fun insertAppointment(userId: Int, doctorName: String, scheduledDate: String, scheduledTime: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_ID, userId)
        values.put(COLUMN_DOCTOR_NAME, doctorName)
        values.put(COLUMN_SCHEDULED_DATE, scheduledDate)
        values.put(COLUMN_SCHEDULED_TIME, scheduledTime)
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAppointmentsForUser(userId: Int): ArrayList<Appointment> {
        val appointmentsList = ArrayList<Appointment>()
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USER_ID = ?"
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery(selectQuery, arrayOf(userId.toString()))
        var id: Int
        var doctorName: String
        var scheduledDate: String
        var scheduledTime: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                doctorName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_NAME))
                scheduledDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCHEDULED_DATE))
                scheduledTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCHEDULED_TIME))

                val appointment = Appointment(id, userId, doctorName, scheduledDate, scheduledTime)
                appointmentsList.add(appointment)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return appointmentsList
    }
}
