package com.example.customtimer

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterTimer(var data: ArrayList<Item>,private val launchTimer:(Int)->Unit): RecyclerView.Adapter<AdapterTimer.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvTextTimer = view.findViewById<TextView>(R.id.tvTextTimer)
        val tvDel = view.findViewById<Button>(R.id.btDel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTimer.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_timer_shablon,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterTimer.ViewHolder, position: Int) {
        val item = data[holder.bindingAdapterPosition]
//        holder.tvDel.setOnClickListener {
//            launchTimer(holder.bindingAdapterPosition)
//        }
        holder.tvTextTimer.text = "Остлось времени: "+item.time
    }

    override fun getItemCount(): Int {
        return data.size
    }
}