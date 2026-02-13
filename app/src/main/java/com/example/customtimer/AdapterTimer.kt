package com.example.customtimer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.time.Duration.Companion.milliseconds

class AdapterTimer(var dataTimer: ArrayList<Item>,private val launchTimer:(Int)->Unit): RecyclerView.Adapter<AdapterTimer.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvTextTimer:TextView = view.findViewById(R.id.tvTextTimer)
        val tvDel:Button = view.findViewById(R.id.btDel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_timer_shablon,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataTimer[holder.bindingAdapterPosition]
        val time =item.time.toComponents { hours, minutes, seconds, nanoseconds ->
            "${hours}ч:${minutes}м:${seconds}с"
        }
        holder.tvTextTimer.text = time
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isNotEmpty()) {
            if(dataTimer[position].time < 999.milliseconds) {
                holder.tvTextTimer.text = "Готово"
            }else{
                val time =dataTimer[position].time.toComponents { hours, minutes, seconds, nanoseconds ->
                    "${hours}ч:${minutes}м:${seconds}с"
                }
                holder.tvTextTimer.text = time
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return dataTimer.size
    }
}