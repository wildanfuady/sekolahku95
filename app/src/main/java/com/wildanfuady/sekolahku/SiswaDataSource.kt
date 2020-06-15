package com.wildanfuady.sekolahku

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.wildanfuady.sekolahku.model.Siswa

class SiswaDataSource (context: Context) {

    // panggil database helper
    // private var content = context
    private var help = DatabaseHelper(context)
    // panggil sqlite
    private var sqlite: SQLiteDatabase? = null

    fun openAccess() {
        sqlite = help.writableDatabase
    }

    fun closeAccess(){
        help.close()
    }

    // buat function insert siswa
    fun insertSiswa(siswa: Siswa) {

        openAccess()

        var cv = ContentValues()

        cv.put(DatabaseHelper.COL_NAMAD, siswa.namad)
        cv.put(DatabaseHelper.COL_NAMAB, siswa.namab)
        cv.put(DatabaseHelper.COL_NO_HP, siswa.no_hp)
        cv.put(DatabaseHelper.COL_GENDER, siswa.gender)
        cv.put(DatabaseHelper.COL_JENJANG, siswa.jenjang)
        cv.put(DatabaseHelper.COL_HOBI, siswa.hobi)
        cv.put(DatabaseHelper.COL_ALAMAT, siswa.alamat)
        // DAY 3 - Email dan Calender : BEGIN PART 5
        cv.put(DatabaseHelper.COL_EMAIL, siswa.email)
        cv.put(DatabaseHelper.COL_TGL_LAHIR, siswa.tgl_lahir)
        // DAY 3 - Email dan Calender : END PART 5

        sqlite?.insert("siswa", null, cv)

        closeAccess()
    }

    fun fetchRow(cursor: Cursor): Siswa {

        var siswa = Siswa()

        siswa.id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID))

        siswa.namad = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAMAD))
        siswa.namab = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAMAB))
        siswa.no_hp = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NO_HP))
        siswa.gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_GENDER))
        siswa.jenjang = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_JENJANG))
        siswa.hobi = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_HOBI))
        siswa.alamat = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ALAMAT))
        // DAY 3 - Email dan Calender : BEGIN PART 6
        siswa.email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_EMAIL))
        siswa.tgl_lahir = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TGL_LAHIR))
        // DAY 3 - Email dan Calender : END PART 6

        return siswa
    }

    fun getAll() : ArrayList<Siswa>{

        openAccess()

        var cursor = sqlite?.rawQuery("SELECT * FROM siswa", null)
        cursor?.moveToFirst()

        // buat list array siswa
        var listSiswa = ArrayList<Siswa>()
        // []

        // jika data / result query bukan yang terakhir dan harus ada datanya
        while (!cursor!!.isAfterLast){
            // masukan data dari table ke array list
            // []
            /*[
                [0] : [
                'nama_depan' => 'Wildan',
                ...
                ],
                [1] : [
                'nama_depan' => 'Wildan',
                ...
                ],
            ]*/
            listSiswa.add(fetchRow(cursor))
            // i++
            cursor.moveToNext()
        }

        // stop cursor
        cursor?.close()

        return listSiswa

        closeAccess()
    }

    fun getSiswa(id : Int) : Siswa {

        openAccess()

        var sql = "SELECT * FROM siswa WHERE id=$id"

        var cursor = sqlite!!.rawQuery(sql, null)
        cursor.moveToFirst()

        var siswa = fetchRow(cursor)

        return siswa

        closeAccess()
    }

    fun search(keyword: String) : ArrayList<Siswa>{

        openAccess()

        var query =  "SELECT * FROM siswa WHERE ${DatabaseHelper.COL_NAMAD} LIKE ? OR ${DatabaseHelper.COL_NAMAB} LIKE ?"
        var selection = arrayOf("%$keyword%", "%$keyword%")

        var cursor = sqlite!!.rawQuery(query, selection)

        var listSiswaBySearch = ArrayList<Siswa>()

        cursor?.moveToFirst()

        while (!cursor!!.isAfterLast)
        {
            listSiswaBySearch.add(fetchRow(cursor))
            cursor.moveToNext()
        }

        cursor.close()
        closeAccess()

        return listSiswaBySearch
    }

    fun deleteSiswa(id : Int?){

        openAccess()

        sqlite?.delete("siswa", "${DatabaseHelper.COL_ID} = $id",
            null)

        closeAccess()
    }

    fun updateSiswa(siswa: Siswa, id: Int){

        openAccess()

        var cv = ContentValues()

        cv.put(DatabaseHelper.COL_NAMAD, siswa.namad)
        cv.put(DatabaseHelper.COL_NAMAB, siswa.namab)
        cv.put(DatabaseHelper.COL_NO_HP, siswa.no_hp)
        cv.put(DatabaseHelper.COL_GENDER, siswa.gender)
        cv.put(DatabaseHelper.COL_JENJANG, siswa.jenjang)
        cv.put(DatabaseHelper.COL_HOBI, siswa.hobi)
        cv.put(DatabaseHelper.COL_ALAMAT, siswa.alamat)
        cv.put(DatabaseHelper.COL_EMAIL, siswa.email)
        cv.put(DatabaseHelper.COL_TGL_LAHIR, siswa.tgl_lahir)

        sqlite?.update("siswa", cv, "${DatabaseHelper.COL_ID} = ?",
            arrayOf(id.toString()))

        closeAccess()
    }

}
