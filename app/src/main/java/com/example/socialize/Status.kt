package com.example.socialize

import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import java.io.ByteArrayInputStream

class Status : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status)

        val id_pemosting = intent.getStringExtra("id_pemosting")

        val foto_profil:ImageView = findViewById(R.id.profile_image)
        val nama:TextView = findViewById(R.id.nama)
        val status:TextView = findViewById(R.id.status)
        val bio:TextView = findViewById(R.id.bio)

        val db:SQLiteDatabase = openOrCreateDatabase("socialize", MODE_PRIVATE,null)

        val query = db.rawQuery("SELECT * FROM users WHERE user_id = '$id_pemosting'",null)
        query.moveToNext()

        val isi_nama:String = query.getString(4)
        val profile_image:ByteArray? = query.getBlob(5)
        val isi_status:String? = query.getString(6)
        val isi_bio:String? = query.getString(7)

        try {
            val blobGambar = ByteArrayInputStream(profile_image)
            val gambarBitmap : Bitmap = BitmapFactory.decodeStream(blobGambar)
            foto_profil?.setImageBitmap(gambarBitmap)
        }catch (a:Exception){
            val gambarBitmap: Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.lol)
            foto_profil?.setImageBitmap(gambarBitmap)
        }

        status?.text = isi_status
        bio?.text = isi_bio
        nama?.text = isi_nama

    }
}