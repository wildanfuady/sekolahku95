package com.wildanfuady.sekolahku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        btn_login.setOnClickListener {
            prosesLogin()
        }


    }

    fun prosesLogin() {

        var username = text_username.text.toString()
        var password = text_password.text.toString()

        if(username == "admin" && password == "admin"){

            var intent = Intent(this, ListActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Berhasil Login",
                Toast.LENGTH_SHORT).show()

            finish()

        } else {
            Toast.makeText(this, "Gagal Login",
                Toast.LENGTH_SHORT).show()
        }

    }
}
