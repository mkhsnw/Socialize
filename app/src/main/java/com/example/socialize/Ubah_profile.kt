package com.example.socialize

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

class Ubah_profile : AppCompatActivity() {
    var img_tambah:ImageView? = null
    var uriGambar: Uri? = null
    var bitmapGambar: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ubah_profile)

        val session: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)

        val id_pelogin:String = session.getString("user_id",null).toString()
        val profile_image:ImageView? = findViewById(R.id.profile_image)

        val pilihGambar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                val gambarDiperoleh = it.data
                if(gambarDiperoleh!=null){
                    uriGambar = gambarDiperoleh.data
                    bitmapGambar = MediaStore.Images.Media.getBitmap(contentResolver,uriGambar)
                    profile_image?.setImageBitmap(bitmapGambar)
                }
            }
        }

        profile_image?.setOnClickListener {
            val bukaGaleri:Intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            pilihGambar.launch(bukaGaleri)
        }


        val edt_username:EditText = findViewById(R.id.edt_username)
        val edt_email:EditText = findViewById(R.id.edt_email)
        val edt_password:EditText = findViewById(R.id.edt_password)
        val edt_fullname:EditText = findViewById(R.id.edt_fullname)
        val btn_ubah:Button = findViewById(R.id.btn_ubah)
        val image_profile:ImageView = findViewById(R.id.profile_image)
        val edt_status:EditText = findViewById(R.id.edt_status)
        val edt_bio:EditText = findViewById(R.id.edt_bio)

        val db:SQLiteDatabase = openOrCreateDatabase("socialize", MODE_PRIVATE, null)
        val query1 = db.rawQuery("SELECT * FROM users WHERE user_id = '$id_pelogin'",null)
        query1.moveToNext()
        val nama_pelogin:String = query1.getString(4)
        val email_pelogin:String = query1.getString(1)
        val password_pelogin:String = query1.getString(2)
        val fullname_login:String = query1.getString(3)
        val foto:ByteArray? = query1.getBlob(5)
        val status_pelogin:String? = query1.getString(6)
        val bio_pelogin:String? = query1.getString(7)




        edt_username.setText(nama_pelogin)
        edt_email.setText(email_pelogin)
        edt_password.setText(password_pelogin)
        edt_fullname.setText(fullname_login)
        edt_bio?.setText(bio_pelogin)
        edt_status?.setText(status_pelogin)

        try {
            val blobGambar = ByteArrayInputStream(foto)
            val gambarBitmap : Bitmap = BitmapFactory.decodeStream(blobGambar)
            image_profile?.setImageBitmap(gambarBitmap)
        }catch (a:Exception){
            val gambarBitmap: Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.lol)
            image_profile?.setImageBitmap(gambarBitmap)
        }

        btn_ubah.setOnClickListener {
            val isi_username:String = edt_username.text.toString()
            val isi_email:String = edt_email.text.toString()
            val isi_password:String = edt_password.text.toString()
            val isi_fullname:String = edt_fullname.text.toString()
            val isi_status:String? = edt_status.text.toString()
            val isi_bio:String? = edt_bio.text.toString()
            val bos = ByteArrayOutputStream()
            bitmapGambar?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val byteArrayGambar = bos.toByteArray()
            val db: SQLiteDatabase =
                openOrCreateDatabase("socialize", Context.MODE_PRIVATE, null)
            val tambah =
                "UPDATE users SET user_email = ?, user_password = ?, user_fullname = ? , user_name = ?, user_foto = ?,user_status = ?,user_bio = ? WHERE user_id = '$id_pelogin'"
            val statement = db.compileStatement(tambah)
            statement.clearBindings()
            statement.bindString(1, isi_email)
            statement.bindString(2, isi_password)
            statement.bindString(3, isi_fullname)
            statement.bindBlob(5, byteArrayGambar)
            statement.bindString(4,isi_username)
            statement.bindString(6,isi_status)
            statement.bindString(7,isi_bio)
            statement.executeUpdateDelete()

            val pindah:Intent = Intent(this,Dashboard::class.java)
            startActivity(pindah)

        }


    }
}