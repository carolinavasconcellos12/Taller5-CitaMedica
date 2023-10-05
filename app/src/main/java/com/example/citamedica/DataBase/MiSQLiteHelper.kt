package com.example.citamedica.DataBase

import com.example.citamedica.Models.Usuario
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class miSQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "usuarios.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_NAME = "usuarios"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "nombre"
        const val COLUMN_SURNAME = "apellido"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USER = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_SURNAME TEXT," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_PASSWORD TEXT)")

        db?.execSQL(CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(usuario: Usuario) {
        val data = ContentValues()
        // No es necesario proporcionar un valor para el campo ID, se genera automáticamente
        data.put(COLUMN_USERNAME, usuario.nombre)
        data.put(COLUMN_SURNAME, usuario.apellido)
        data.put(COLUMN_EMAIL, usuario.email)
        data.put(COLUMN_PASSWORD, usuario.password)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, data)
        db.close()
    }

    fun getUsuarioByEmailAndPassword(email: String, password: String): Usuario? {
        val db = readableDatabase
        val projection = arrayOf(COLUMN_ID, COLUMN_USERNAME, COLUMN_SURNAME, COLUMN_EMAIL, COLUMN_PASSWORD) // Incluye COLUMN_ID en la proyección
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null)

        val usuario: Usuario?

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
            val apellido = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME))
            val userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val userPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            usuario = Usuario(id, nombre, apellido, userEmail, userPassword)
        } else {
            usuario = null
        }

        cursor.close()
        db.close()

        return usuario
    }
}
