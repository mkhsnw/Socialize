package com.example.socialize

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class Posting_item(val a:Context,val id:MutableList<String>,val caption:MutableList<String>,val deskripsi:MutableList<String>,val foto:MutableList<Bitmap>,val foto_pemosting:MutableList<Bitmap>,val nama_pemosting:MutableList<String>,val id_pemosting:MutableList<String>) : RecyclerView.Adapter<Posting_item.ListViewHolder>(){
    val session:SharedPreferences = a.getSharedPreferences("user",Context.MODE_PRIVATE)
    val id_pelogin:String = session.getString("user_id",null).toString()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postingan:ImageView = itemView.findViewById(R.id.postingan)
        val caption:TextView = itemView.findViewById(R.id.caption)
        val deskripsi:TextView = itemView.findViewById(R.id.deskripsi)
        val btn_ubah:Button = itemView.findViewById(R.id.btn_ubah)
        val btn_hapus:Button = itemView.findViewById(R.id.btn_hapus)
        val profile_image:ImageView = itemView.findViewById(R.id.profile_image)
        val username_pemosting:TextView= itemView.findViewById(R.id.username_pemosting)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.posting_item,parent,false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return caption.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.caption.text = caption.get(position)
        holder.deskripsi.text = deskripsi.get(position)
        holder.postingan.setImageBitmap(foto.get(position))
        holder.profile_image.setImageBitmap(foto_pemosting.get(position))
        holder.username_pemosting.text = nama_pemosting.get(position)

        holder.profile_image.setOnClickListener {
            val id_pemosting:String = id_pemosting.get(position)
            val pindah:Intent = Intent(a,Status::class.java)
            pindah.putExtra("id_pemosting",id_pemosting)
            a.startActivity(pindah)
        }


        holder.btn_hapus.setOnClickListener {
            val id_pemosting:String = id_pemosting.get(position)
            val id_terpilih:String =id.get(position)
            if (id_pelogin == id_pemosting){
                val pindah:Intent = Intent(a,Hapus_posting::class.java)
                pindah.putExtra("id_terpilih",id_terpilih)
                a.startActivity(pindah)
            }else{
                Toast.makeText(a, "Bukan Postingan anda", Toast.LENGTH_LONG).show()
            }
        }

        holder.btn_ubah.setOnClickListener {
            val id_pemosting:String = id_pemosting.get(position)
            val id_terpilih:String = id.get(position)
            if (id_pelogin == id_pemosting){
                val pindah:Intent = Intent(a,Ubah_posting::class.java)
                pindah.putExtra("id_terpilih",id_terpilih)
                a.startActivity(pindah)
            }else{
                Toast.makeText(a, "Bukan Postingan anda", Toast.LENGTH_LONG).show()
            }
        }
    }

}