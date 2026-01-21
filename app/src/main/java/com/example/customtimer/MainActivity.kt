package com.example.customtimer

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.collection.mutableIntListOf
import com.example.customtimer.databinding.ActivityMainBinding
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit


class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer()

        mediaPlayer = MediaPlayer.create(this,R.raw.elec_alarm)


        binding.btStart.setOnClickListener {
            timerStart(binding.edHour.text.toString(),binding.edMin.text.toString(),binding.edSec.text.toString())
        }
    }
    fun timerStart(hour: String = "0", minute: String = "0", second: String="0"){
        val sum = ((hour.toInt().hours)+(minute.toInt().minutes)+(second.toInt().seconds)).toLong(DurationUnit.MILLISECONDS)
        object : CountDownTimer(sum,1000){
            override fun onFinish() {
                mediaPlayer.isLooping = true
                mediaPlayer.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.tvText.setText("Осталось времени: ${millisUntilFinished/1000}")
            }
        }.start()
    }
}