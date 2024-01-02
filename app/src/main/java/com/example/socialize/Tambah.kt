package com.example.socialize

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import android.net.Uri


class Tambah : Fragment() {

    var img_tambah:ImageView? = null
    var uriGambar:Uri? = null
    var bitmapGambar:Bitmap? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val session: SharedPreferences = requireContext().getSharedPreferences("user",Context.MODE_PRIVATE)
        val view = inflater.inflate(R.layout.tambah, container, false)
        val edt_caption:EditText = view.findViewById(R.id.edt_caption)
        val edt_deskripsi:EditText = view.findViewById(R.id.edt_deskripsi)
        val btn_tambah:Button = view.findViewById(R.id.btn_tambah)
        val id_pelogin:String = session.getString("user_id",null).toString()
        img_tambah = view.findViewById(R.id.img_tambah)

        val pilihGambar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                val gambarDiperoleh = it.data
                if(gambarDiperoleh!=null){
                    uriGambar = gambarDiperoleh.data
                    bitmapGambar = MediaStore.Images.Media.getBitmap(requireContext().contentResolver,uriGambar)
                    img_tambah?.setImageBitmap(bitmapGambar)
                }
            }
        }

        img_tambah?.setOnClickListener{
            val bukaGaleri:Intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            pilihGambar.launch(bukaGaleri)
        }

        btn_tambah.setOnClickListener {
            val isi_caption: String = edt_caption.text.toString()
            val isi_deskripsi: String = edt_deskripsi.text.toString()
            val bos = ByteArrayOutputStream()
            bitmapGambar?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val byteArrayGambar = bos.toByteArray()
            val db: SQLiteDatabase =
                requireContext().openOrCreateDatabase("socialize", Context.MODE_PRIVATE, null)
            val tambah =
                "INSERT INTO posting (posting_caption,posting_deskripsi,posting_gambar,id_user) VALUES (?,?,?,?)"
            val statement = db.compileStatement(tambah)
            statement.clearBindings()
            statement.bindString(1, isi_caption)
            statement.bindString(2, isi_deskripsi)
            statement.bindBlob(3, byteArrayGambar)
            statement.bindString(4,id_pelogin)
            statement.executeInsert()

            val pindah:Intent = Intent(requireContext(),Dashboard::class.java)
            startActivity(pindah)

        }

            return view
    }


}