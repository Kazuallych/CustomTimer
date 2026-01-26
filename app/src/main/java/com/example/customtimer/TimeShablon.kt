package com.example.customtimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.customtimer.databinding.TimerShablonBinding

class TimeShablon : ComponentActivity() {
    lateinit var binding: TimerShablonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TimerShablonBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}