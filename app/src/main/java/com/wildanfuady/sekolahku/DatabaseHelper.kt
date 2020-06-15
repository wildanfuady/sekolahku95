package com.wildanfuady.sekolahku

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,
    "sekolahku", null, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE siswa ( " +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAMAD TEXT, " +
                "$COL_NAMAB TEXT, " +
                "$COL_NO_HP TEXT, " +
                "$COL_GENDER TEXT, " +
                "$COL_JENJANG TEXT, " +
                "$COL_HOBI TEXT, " +
                // DAY 3 - Email dan Calender : BEGIN PART 4
                "$COL_EMAIL TEXT, " +
                "$COL_TGL_LAHIR TEXT, " +
                // DAY 3 - Email dan Calender : END PART 4
                "$COL_ALAMAT TEXT)"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int, newVersion: Int) {
        // hapus table
        val sql = "DROP TABLE IF EXISTS siswa"
        db?.execSQL(sql)
        // buat table baru
        onCreate(db)

    }

    // memberikan nama alias pada field table siswa
    companion object{
        val COL_ID = "id"
        val COL_NAMAD = "nama_depan"
        val COL_NAMAB = "nama_belakang"
        val COL_NO_HP = "no_hp"
        val COL_GENDER = "gender"
        val COL_JENJANG = "jenjang"
        val COL_HOBI = "hobi"
        val COL_ALAMAT = "alamat"
        // DAY 3 - Email dan Calender : BEGIN PART 3
        val COL_EMAIL = "email"
        val COL_TGL_LAHIR = "tanggal_lahir"
        // DAY 3 - Email dan Calender : END PART 3
    }


}