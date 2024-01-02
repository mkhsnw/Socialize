package com.example.socialize

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.io.ByteArrayInputStream
import kotlin.math.log


class Profile : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
         val view = inflater.inflate(R.layout.profile, container, false)

        val session:SharedPreferences = requireContext().getSharedPreferences("user",Context.MODE_PRIVATE)
        val id_pelogin:String = session.getString("user_id",null).toString()
        val db:SQLiteDatabase = requireContext().openOrCreateDatabase("socialize",Context.MODE_PRIVATE,null)
        val query1 = db.rawQuery("SELECT * FROM users WHERE user_id='$id_pelogin'",null)
        query1.moveToNext()
        val nama_pelogin:String = query1.getString(4)
        val email_pelogin:String = query1.getString(1)
        val fullname_login:String = query1.getString(3)
        val foto:ByteArray? = query1.getBlob(5)
        val status:String? = query1.getString(6)
        val bio:String? = query1.getString(7)

        val username_pelogin:TextView = view.findViewById(R.id.nama)
        val email:TextView = view.findViewById(R.id.email)
        val fullname:TextView = view.findViewById(R.id.fullname)
        val image_profile:ImageView? = view.findViewById(R.id.profile_image)


        try {
            val blobGambar = ByteArrayInputStream(foto)
            val gambarBitmap : Bitmap = BitmapFactory.decodeStream(blobGambar)
            image_profile?.setImageBitmap(gambarBitmap)
        }catch (a:Exception){
            val gambarBitmap: Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.lol)
            image_profile?.setImageBitmap(gambarBitmap)
        }



        username_pelogin.text = nama_pelogin
        email.text = email_pelogin
        fullname.text = fullname_login

        val btn_ubah:Button = view.findViewById(R.id.btn_ubah)
        val btn_logout:Button = view.findViewById(R.id.btn_logout)

        btn_logout.setOnClickListener {
            val edit = session.edit()
            edit.clear()
            edit.commit()

            val pindah:Intent = Intent(requireContext(),Login::class.java)
            startActivity(pindah)
            requireActivity().finish()
        }

        btn_ubah.setOnClickListener {
            val pindah: Intent = Intent(requireContext(), Ubah_profile::class.java)
            startActivity(pindah)
        }

            return view
    }


}