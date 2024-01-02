package com.example.socialize

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream
import java.util.zip.Inflater


class Home : Fragment() {

    private lateinit var rv_postingan:RecyclerView
    private lateinit var db:SQLiteDatabase
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.home, container, false)

        val caption = mutableListOf<String>()
        val deskripsi = mutableListOf<String>()
        val foto = mutableListOf<Bitmap>()
        val nama_pemosting = mutableListOf<String>()
        val gambar_pemosting = mutableListOf<Bitmap>()
        val id_posting = mutableListOf<String>()
        val id_pemosting = mutableListOf<String>()


        db = requireContext().openOrCreateDatabase("socialize",Context.MODE_PRIVATE,null)

        val dataPostingan = db.rawQuery("SELECT * FROM posting JOIN users on posting.id_user = users.user_id",null)
        val username_pemosting = dataPostingan.getColumnIndex("user_name")
        val profile_pemosting = dataPostingan.getColumnIndex("user_foto")
        val user_id_posting = dataPostingan.getColumnIndex("id_user")
        val foto_postingan = dataPostingan.getColumnIndex("posting_gambar")
        while (dataPostingan.moveToNext()){
            try {
                val blobGambar = ByteArrayInputStream(dataPostingan.getBlob(foto_postingan))
                val gambarBitmap : Bitmap = BitmapFactory.decodeStream(blobGambar)
                foto.add(gambarBitmap)
            }catch (a:Exception){
                val gambarBitmap:Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.lol)
                foto.add(gambarBitmap)
            }

            try {
                // Handle profile_pemosting
                val blobGambar2 = ByteArrayInputStream(dataPostingan.getBlob(profile_pemosting))
                val gambarBitmap2 = BitmapFactory.decodeStream(blobGambar2)
                gambar_pemosting.add(gambarBitmap2)
            } catch (a: Exception) {
                val gambarBitmap2 = BitmapFactory.decodeResource(this.resources, R.drawable.lol) // Asumsikan gambar default untuk profile
                gambar_pemosting.add(gambarBitmap2)
            }
            nama_pemosting.add(dataPostingan.getString(username_pemosting))
            id_posting.add(dataPostingan.getString(0))
            caption.add(dataPostingan.getString(1))
            deskripsi.add(dataPostingan.getString(2))
            id_pemosting.add(dataPostingan.getString(user_id_posting))
        }

        rv_postingan = view.findViewById(R.id.rv_home)
        val data = Posting_item(requireContext(),id_posting,caption, deskripsi, foto,gambar_pemosting,nama_pemosting,id_pemosting)
        rv_postingan.layoutManager = LinearLayoutManager(context)
        rv_postingan.adapter = data

            return view

    }



}