package com.example.customtimer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

class AdapterTimer(var dataTimer: ArrayList<Item>,private val launchTimer:(Int)->Unit): RecyclerView.Adapter<AdapterTimer.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvTextTimer = view.findViewById<TextView>(R.id.tvTextTimer)
        val tvDel = view.findViewById<Button>(R.id.btDel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_timer_shablon,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataTimer[holder.bindingAdapterPosition]
        holder.tvTextTimer.text = "Осталось времени: "+item.time.toInt(DurationUnit.SECONDS)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isNotEmpty()) {
            if(dataTimer[position].time < 999.milliseconds) {
                holder.tvTextTimer.text = "Готово"
            }else{
                holder.tvTextTimer.text = "Осталось времени: "+ dataTimer[position].time.inWholeSeconds
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return dataTimer.size
    }
}