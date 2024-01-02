package com.example.socialize

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val edt_email: EditText = findViewById(R.id.edt_email)
        val edt_password: EditText = findViewById(R.id.edt_password)
        val btn_login: Button = findViewById(R.id.btn_login)
        val daftar:TextView = findViewById(R.id.daftar)

        btn_login.setOnClickListener {

            val isi_email: String = edt_email.text.toString()
            val isi_password: String = edt_password.text.toString()

            val dbkampus: SQLiteDatabase = openOrCreateDatabase("socialize", MODE_PRIVATE, null)

            val query = dbkampus.rawQuery(
                "SELECT * FROM users WHERE user_email = '$isi_email' AND user_password='$isi_password'",
                null
            )
            val cek = query.moveToNext()

            if (cek) {

                val id_login = query.getString(0)
                val email_login = query.getString(1)
                val password_login = query.getString(2)
                val fullname_login = query.getString(3)
                val nama_login = query.getString(4)

                val session: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
                val tiket = session.edit()
                tiket.putString("user_id", id_login)
                tiket.putString("user_email", email_login)
                tiket.putString("user_password", password_login)
                tiket.putString("user_name", nama_login)
                tiket.putString("user_fullname",fullname_login)
                tiket.commit()



                val pindah: Intent = Intent(this, Dashboard::class.java)
                startActivity(pindah)
            } else {
                Toast.makeText(this, "Email atau Password Salah! ", Toast.LENGTH_LONG).show()
            }
        }

        daftar.setOnClickListener {
            val pindah:Intent = Intent(this,Daftar::class.java)
            startActivity(pindah)
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()
    }
}