package com.example.socialize

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Daftar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daftar)

        val edt_email:EditText = findViewById(R.id.edt_email)
        val edt_password:EditText = findViewById(R.id.edt_password)
        val edt_fullname:EditText = findViewById(R.id.edt_fullname)
        val edt_username:EditText = findViewById(R.id.edt_username)
        val btn_daftar:Button = findViewById(R.id.btn_daftar)

        btn_daftar.setOnClickListener {
            val isi_email:String = edt_email.text.toString()
            val isi_password:String = edt_password.text.toString()
            val isi_fullname:String = edt_fullname.text.toString()
            val isi_username:String = edt_username.text.toString()
            val db:SQLiteDatabase = openOrCreateDatabase("socialize", MODE_PRIVATE,null)
            val query = db.rawQuery("INSERT INTO users(user_email,user_password,user_fullname,user_name) VALUES ('$isi_email','$isi_password','$isi_fullname','$isi_username')",null)
            query.moveToNext()

            val pindah:Intent = Intent(this,Login::class.java)
            startActivity(pindah)
        }
    }




}