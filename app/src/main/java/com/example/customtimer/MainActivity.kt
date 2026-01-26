package com.example.customtimer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customtimer.databinding.ActivityMainBinding
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds



class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var data: ArrayList<Item>
    lateinit var dataTimer: ArrayList<Item>
    lateinit var adapter: Adapter
    lateinit var adapterTimer: AdapterTimer
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = ArrayList()
        dataTimer = ArrayList()
//        val timerAdd = object : CountDownTimer(10000,1000){
//            override fun onFinish() {
//                TODO("Not yet implemented")
//            }
//
//            override fun onTick(millisUntilFinished: Long) {
//                Log.d("MyLog","122312")
//            }
//        }
        dataTimer.add(Item(10.seconds))
        data.add(Item(10.seconds))
        data.add(Item(10.seconds))

        mediaPlayer = MediaPlayer()
        mediaPlayer = MediaPlayer.create(this,R.raw.elec_alarm)

//        adapterTimer = AdapterTimer(dataTimer,{pos->
//            dataTimer[pos].start()
//        })
        binding.rcViewTimer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rcViewTimer.adapter = adapterTimer


        adapter = Adapter({
            mediaPlayer.start()
        },{
            if(mediaPlayer.isPlaying){
                mediaPlayer.stop()
            }
          },{position->
              dataTimer.add(data[position])
            adapterTimer.notifyItemInserted(dataTimer.size-1)
            }, data)

        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter

    }
}