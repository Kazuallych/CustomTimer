package com.example.customtimer

import android.os.Bundle
import android.os.CountDownTimer
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btStart.setOnClickListener {
            timerStart(binding.edHour.text.toString(),binding.edMin.text.toString(),binding.edSec.text.toString())
        }
    }
    fun timerStart(hour: String, minute: String, second: String){
        val sum = ((hour.toInt().hours)+(minute.toInt().minutes)+(second.toInt().seconds)).toLong(DurationUnit.MILLISECONDS)
        object : CountDownTimer(sum,1000){
            override fun onFinish() {
                binding.tvText.setText("Готово")
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.tvText.setText("Осталось времени: ${millisUntilFinished/1000}")
            }
        }.start()
    }
}