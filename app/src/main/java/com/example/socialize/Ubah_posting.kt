package com.example.socialize

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Ubah_posting : AppCompatActivity() {
    var img_tambah:ImageView? = null
    var uriGambar: Uri? = null
    var bitmapGambar:Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ubah_posting)

        val id_terpilih:String = intent.getStringExtra("id_terpilih").toString()

        val edt_caption:EditText = findViewById(R.id.edt_caption)
        val edt_deskripsi:EditText = findViewById(R.id.edt_deskripsi)
        val foto:ImageView= findViewById(R.id.img_ubah)

        val db:SQLiteDatabase = openOrCreateDatabase("socialize", MODE_PRIVATE,null)
        val query = db.rawQuery("SELECT * FROM posting WHERE posting_id = '$id_terpilih'",null)
        val cek = query.moveToNext()
        val caption_lama:String = query.getString(1)
        val deskripsi_lama:String = query.getString(2)
        val foto_lama:ByteArray = query.getBlob(3)

        val pilihGambar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                val gambarDiperoleh = it.data
                if(gambarDiperoleh!=null){
                    uriGambar = gambarDiperoleh.data
                    bitmapGambar = MediaStore.Images.Media.getBitmap(contentResolver,uriGambar)
                    foto?.setImageBitmap(bitmapGambar)
                }
            }
        }


        try {
            val blobGambar = ByteArrayInputStream(foto_lama)
            val gambarBitmap : Bitmap = BitmapFactory.decodeStream(blobGambar)
            foto.setImageBitmap(gambarBitmap)
        }catch (a:Exception){
            val gambarBitmap: Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.lol)
            foto.setImageBitmap(gambarBitmap)
        }

        edt_caption.setText(caption_lama)
        edt_deskripsi.setText(deskripsi_lama)

        foto?.setOnClickListener{
            val bukaGaleri:Intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            pilihGambar.launch(bukaGaleri)
        }

        val btn_simpan:Button = findViewById(R.id.btn_ubah)

        btn_simpan.setOnClickListener {
            val caption_baru = edt_caption.text.toString()
            val deskripsi_baru = edt_deskripsi.text.toString()
            val bos = ByteArrayOutputStream()
            bitmapGambar?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val byteArrayGambar = bos.toByteArray()

//            val query_ubah = db.rawQuery("UPDATE posting SET posting_caption = '$caption_baru', posting_deskripsi = '$deskripsi_baru' WHERE posting_id = '$id_terpilih'",null)
//            query_ubah.moveToNext()
            val tambah =
                "UPDATE posting SET posting_caption = ?,posting_deskripsi = ?,posting_gambar = ? WHERE posting_id = '$id_terpilih'"
            val statement = db.compileStatement(tambah)
            statement.clearBindings()
            statement.bindString(1, caption_baru)
            statement.bindString(2, deskripsi_baru)
            statement.bindBlob(3, byteArrayGambar)
            statement.executeUpdateDelete()

            val pindah:Intent = Intent(this,Dashboard::class.java)
            startActivity(pindah)
        }
    }
}