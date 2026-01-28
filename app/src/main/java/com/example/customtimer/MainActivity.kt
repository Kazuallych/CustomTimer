package com.example.customtimer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customtimer.databinding.ActivityMainBinding
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit


class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var data: ArrayList<Item>
    lateinit var dataTimer: ArrayList<Item>
    lateinit var adapter: Adapter
    lateinit var adapterTimer: AdapterTimer
    lateinit var mediaPlayer: MediaPlayer
    var positionTimer = 0
    lateinit var tempTimers: ArrayList<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = ArrayList()
        dataTimer = ArrayList()
        val arrayTimer = ArrayList<CountDownTimer>()
        tempTimers = ArrayList<Item>()
        data.add(Item(10.seconds))
        //dataTimer.add(Item(10.seconds))
        //Запуск последовательности таймеров
        binding.btStart.setOnClickListener {
            positionTimer = 0
            arrayTimer[positionTimer].start()
        }

        //Адаптер Динамического списка таймеров
        adapterTimer = AdapterTimer(dataTimer,{})
        binding.rcViewTimer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rcViewTimer.adapter = adapterTimer

        //Адаптер Основного списка таймеров
        adapter = Adapter({
            mediaPlayer.start()
        },{
            if(mediaPlayer.isPlaying){
                mediaPlayer.stop()
            }
        },{position->
            dataTimer.add(data[position])

            tempTimers.addAll(dataTimer)
            Log.d("MyLog","${tempTimers[0].time}")

            val timerAdd = object : CountDownTimer((dataTimer[positionTimer].time.toLong(DurationUnit.MILLISECONDS)),1000){
                override fun onTick(millisUntilFinished: Long) {
                    adapterTimer.notifyItemChanged(positionTimer,"TEXT_CHANGED")
                    dataTimer[positionTimer].time = millisUntilFinished.milliseconds
                }
                override fun onFinish() {
                    positionTimer++
                    if(positionTimer<=dataTimer.size-1) {
                        arrayTimer[positionTimer].start()
                    }else{
                        //Нужно сделать перерисовку чтобы после того как закончаться таймеры, то он вернулись в исходное положение
                        dataTimer.addAll(tempTimers)

                        for(i in 0..dataTimer.size-1){
                            Log.d("MyLog","${tempTimers[i].time.inWholeSeconds}")
                            adapterTimer.notifyItemChanged(i)
                        }
                    }

                }
            }
            arrayTimer.add(timerAdd)
            adapterTimer.notifyItemInserted(dataTimer.size-1)
        }, data)
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter

        //Определение музыки
        mediaPlayer = MediaPlayer()
        mediaPlayer = MediaPlayer.create(this,R.raw.elec_alarm)
    }
}