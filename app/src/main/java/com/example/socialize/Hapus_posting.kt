package com.example.socialize

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class Hapus_posting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hapus_posting)

        val id_terpilih = intent.getStringExtra("id_terpilih").toString()
        val db:SQLiteDatabase = openOrCreateDatabase("socialize", MODE_PRIVATE,null)
        val cek = db.rawQuery("DELETE FROM posting WHERE posting_id = '$id_terpilih'",null)

        if (cek.moveToNext()){
            Toast.makeText(this, "Data gagal dihapus ", Toast.LENGTH_LONG).show()
            val pindah:Intent = Intent(this,Dashboard::class.java)
            startActivity(pindah)
        } else{
            Toast.makeText(this, "Data Berhasil Dihapus ", Toast.LENGTH_LONG).show()
            val pindah:Intent = Intent(this,Dashboard::class.java)
            startActivity(pindah)
        }
    }
}