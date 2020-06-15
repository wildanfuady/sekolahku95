package com.wildanfuady.sekolahku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Detail Siswa")

        // menangkap parameter yang diberikan oleh intent
        var id = 0
        if(intent != null){
            id = intent.getIntExtra("id", 0)
        }

        var siswaDataSource = SiswaDataSource(this)
        var siswa = siswaDataSource.getSiswa(id)

        txt_nama.text       = siswa.namad + " " + siswa.namab
        txt_no_hp.text      = siswa.no_hp
        txt_email.text      = siswa.email
        txt_tgl_lahir.text  = siswa.tgl_lahir
        txt_gender.text     = siswa.gender
        txt_jenjang.text    = siswa.jenjang
        txt_hobi.text       = siswa.hobi
        txt_alamat.text     = siswa.alamat

    }
}
