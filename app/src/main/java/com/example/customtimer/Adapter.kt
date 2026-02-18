package com.example.customtimer


import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit


class Adapter(private val launchSound:()->Unit, private val stopSound:()-> Unit,private val addItem:(Int)->Unit, var data: ArrayList<Item>):RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvSecLeft:TextView =view.findViewById(R.id.tvSecLeft)
        var btTstart:Button = view.findViewById(R.id.btTstart)
        var btCancel:Button = view.findViewById(R.id.btCancel)
        var btAdd:Button = view.findViewById(R.id.btAdd)
        var swRepeatMain: Switch = view.findViewById(R.id.swRepeatMain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_shablon,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[holder.bindingAdapterPosition]
        val time =item.time.toComponents { hours, minutes, seconds, nanoseconds ->
            "${hours}ч:${minutes}м:${seconds}с"
        }
        holder.tvSecLeft.text = time
        val timer = object : CountDownTimer(data[holder.bindingAdapterPosition].time.toLong(DurationUnit.MILLISECONDS),1000){
            override fun onTick(millisUntilFinished: Long) {
                holder.tvSecLeft.text = millisUntilFinished.milliseconds.toComponents { hours, minutes, seconds, nanoseconds ->
                        "${hours}ч:${minutes}м:${seconds}с"
                    }
            }
            override fun onFinish() {
                launchSound()
                holder.tvSecLeft.text = "Готово"
            }
        }

        holder.btTstart.setOnClickListener {
            timer.start()
        }
        holder.btCancel.setOnClickListener {
            timer.cancel()
            if(holder.swRepeatMain.isChecked){
                stopSound()
                timer.start()
            }else{
                holder.tvSecLeft.text = item.time.toComponents { hours, minutes, seconds, nanoseconds ->
                    "${hours}ч:${minutes}м:${seconds}с"
                }
            }

        }
        holder.btAdd.setOnClickListener {
            addItem(holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}