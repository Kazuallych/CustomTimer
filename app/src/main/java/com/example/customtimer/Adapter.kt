package com.example.customtimer


import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.time.DurationUnit


class Adapter(private val launchSound:()->Unit, private val stopSound:()-> Unit,private val addItem:(Int)->Unit, var data: ArrayList<Item>):RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvSecLeft =view.findViewById<TextView>(R.id.tvSecLeft)
        var btTstart = view.findViewById<Button>(R.id.btTstart)
        var btCancel = view.findViewById<Button>(R.id.btCancel)
        var btAdd = view.findViewById<Button>(R.id.btAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_shablon,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[holder.bindingAdapterPosition]
        holder.tvSecLeft.setText("Осталось времени: ${item.time.toInt(DurationUnit.SECONDS)}")
        val timer = object : CountDownTimer(data[holder.bindingAdapterPosition].time.toLong(DurationUnit.MILLISECONDS),1000){
            override fun onTick(millisUntilFinished: Long) {
                holder.tvSecLeft.setText("Осталось времени: ${millisUntilFinished/1000}")
            }
            override fun onFinish() {
                launchSound()
                holder.tvSecLeft.setText("Готово")
            }
        }

        holder.btTstart.setOnClickListener {
            timer.start()
        }
        holder.btCancel.setOnClickListener {
            stopSound()
            timer.cancel()
            holder.tvSecLeft.text = "Осталось времени: "+ item.time
        }
        holder.btAdd.setOnClickListener {
            addItem(holder.bindingAdapterPosition)
        }

        //val sum = ((hour.toInt().hours) + (minute.toInt().minutes) + (second.toInt().seconds)).toLong(DurationUnit.MILLISECONDS)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}