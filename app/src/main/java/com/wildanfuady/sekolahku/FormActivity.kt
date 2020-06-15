package com.wildanfuady.sekolahku

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.wildanfuady.sekolahku.model.Siswa
import kotlinx.android.synthetic.main.activity_form.*
import java.util.*
import kotlin.collections.ArrayList

// DAY 3 - Email dan Calender : BEGIN PART 10
class FormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var tgl_lahir = "${dayOfMonth} - ${month + 1} - ${year}"
        inputTanggalLahir.setText(tgl_lahir)
    }

    // DAY 3 - Email dan Calender : END PART 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        //set tombol back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_simpan.setOnClickListener {
            simpan()
        }

        // DAY 3 - Email dan Calender : BEGIN PART 11
        inputTanggalLahir.setOnClickListener {
            showChooseDatePickerDialog()
        }
        // DAY 3 - Email dan Calender : END PART 11

        var id = intent.getIntExtra("id", 0)

        if(id > 0){
            title = "Edit Siswa"
            showFormUpdateSiswa(id)
        } else {
            title = "Tambah Siswa"
        }

    }

    fun showFormUpdateSiswa(id : Int){

        var siswaDataSource = SiswaDataSource(this)
        var siswa = siswaDataSource.getSiswa(id)

        inputNamaDepan.setText(siswa.namad)
        inputNamaBelakang.setText(siswa.namab)
        inputNoHp.setText(siswa.no_hp)
        inputEmail.setText(siswa.email)
        inputTanggalLahir.setText(siswa.tgl_lahir)
        inputAlamat.setText(siswa.alamat)

        // radio button
        var gender = siswa.gender

        if(gender.equals("Pria")){
            rbPria.isChecked = true
        } else {
            rbWanita.isChecked = true
        }

        // select dropdown
        var jenjang = siswa.jenjang
        var adapter = spinnerJenjang.adapter as ArrayAdapter<String>

        // ambil posisi jenjeng
        var jenjangPos = adapter.getPosition(jenjang)
        spinnerJenjang.setSelection(jenjangPos)

        // hobi
        var hobi = siswa.hobi
        if (hobi!!.contains("Membaca"))  cbMembaca.isChecked = true
        if (hobi!!.contains("Menulis")) cbMenulis.isChecked = true
        if (hobi!!.contains("Menggambar")) cbMenggambar.isChecked = true

        btn_simpan.setText("UPDATE")
    }

    // DAY 3 - Email dan Calender : BEGIN PART 12
    fun showChooseDatePickerDialog(){

        var calender = Calendar.getInstance()
        var datePick = DatePickerDialog(this, this,
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH))

        datePick.show()

    }
    // DAY 3 - Email dan Calender : END PART 12

    // buat kelompok data dari form ke model siswa
    fun getInput() : Siswa {

        var nama_depan      = inputNamaDepan.text.toString()
        var nama_belakang   = inputNamaBelakang.text.toString()
        var no_hp           = inputNoHp.text.toString()
        var alamat          = inputAlamat.text.toString()
        var gender          = getSelectedGender()
        var jenjang         = spinnerJenjang.selectedItem.toString()
        var hobi            = getSelectedHobi()
        // DAY 3 - Email dan Calender : BEGIN PART 8
        var email           = inputEmail.text.toString()
        var tgl_lahir       = inputTanggalLahir.text.toString()
        // DAY 3 - Email dan Calender : END PART 8

        var siswa = Siswa()

        siswa.namad     = nama_depan
        siswa.namab     = nama_belakang
        siswa.no_hp     = no_hp
        siswa.gender    = gender
        siswa.jenjang   = jenjang
        siswa.hobi      = hobi
        siswa.alamat    = alamat
        // DAY 3 - Email dan Calender : BEGIN PART 9
        siswa.email     = email
        siswa.tgl_lahir = tgl_lahir
        // DAY 3 - Email dan Calender : END PART 9

        return siswa
    }

    fun simpan(){

        if(validateAll() == true){
            return
        }

        // buat variabel untuk menampung class SiswaDataSource
        var siswaDataSource = SiswaDataSource(this)

        // ambil data input di dalam model siswa
        var siswa = getInput()

        var id = intent.getIntExtra("id", 0)

        if(id > 0){
            // update data
            siswaDataSource.updateSiswa(siswa, id)
            Toast.makeText(this, "Berhasil Mengubah data",
                Toast.LENGTH_SHORT).show()
        } else {

            // panggil function insertSiswa yang akan membawa data siswa
            // dari model siswa
            siswaDataSource.insertSiswa(siswa)
            // tampilkan pesan berhasil menyimpan data
            Toast.makeText(
                this, "Berhasil menyimpan data",
                Toast.LENGTH_SHORT
            ).show()
        }

        var intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
        // hapus activity form
        finish()

    }

    fun getSelectedGender() : String {

        var gender = ""
        if(rbPria.isChecked){
            gender = "Pria"
        } else {
            gender = "Wanita"
        }
        return gender
    }

    fun getSelectedHobi() : String {

        var listHobi = ArrayList<String>()

        if(cbMembaca.isChecked){
            listHobi.add("Membaca")
        }

        if(cbMenulis.isChecked){
            listHobi.add("Menulis")
        }

        if(cbMenggambar.isChecked){
            listHobi.add("Menggambar")
        }

        return TextUtils.join(", ", listHobi)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // daftarkan menu save ke class FormActivity
        menuInflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.save){
            simpan()
        }
        return super.onOptionsItemSelected(item)
    }

    fun validateNamaDepan() : Boolean {

        var nama_depan = inputNamaDepan.text.toString()

        if(nama_depan.isEmpty()){
            inputNamaDepan.setError("Nama Depan Wajib Diisi")
            inputNamaDepan.requestFocus()
            return true
        }

        return false
    }

    fun validateNamaBelakang() : Boolean {

        var nama_belakang = inputNamaBelakang.text.toString()

        if(nama_belakang.isEmpty()){
            inputNamaBelakang.setError("Nama Belakang Wajib Diisi")
            inputNamaBelakang.requestFocus()
            return true
        }

        return false
    }

    fun validateNoHp() : Boolean {

        var no_hp = inputNoHp.text.toString()

        if(no_hp.isEmpty()){
            inputNoHp.setError("No Hp Wajib Diisi")
            inputNoHp.requestFocus()
            return true
        }

        return false
    }

    fun validateAlamat() : Boolean {

        var alamat = inputAlamat.text.toString()

        if(alamat.isEmpty()){
            inputAlamat.setError("Alamat Wajib Diisi")
            inputAlamat.requestFocus()
            return true
        }

        return false
    }

    // DAY 3 - Email dan Calender : BEGIN PART 7

    fun validateEmail() : Boolean {

        var email = inputEmail.text.toString()

        if(email.isEmpty()){
            inputEmail.setError("Email Wajib Diisi")
            inputEmail.requestFocus()
            return true
        }

        return false
    }

    fun validateTglLahir() : Boolean {

        var tgl_lahir = inputTanggalLahir.text.toString()

        if(tgl_lahir.isEmpty()){
            inputTanggalLahir.setError("Tanggal Lahir Wajib Diisi")
            inputTanggalLahir.requestFocus()
            return true
        }

        return false
    }

    fun validateAll() : Boolean {

        if(validateNamaDepan() && validateNamaBelakang()
            && validateNoHp() && validateAlamat() && validateEmail()
            && validateTglLahir()){
            return true
        }

        return false
    }

    // DAY 3 - Email dan Calender : END PART 7
}
