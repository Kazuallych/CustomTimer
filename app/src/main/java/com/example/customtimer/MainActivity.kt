package com.example.customtimer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customtimer.databinding.ActivityMainBinding
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding
    lateinit var data: ArrayList<Item>
    lateinit var dataTimer: ArrayList<Item>
    lateinit var adapter: Adapter
    lateinit var adapterTimer: AdapterTimer
    lateinit var mediaPlayer: MediaPlayer
    private val dataModel: DataModel by viewModels()
    var positionTimer = 0
    var tempTimers = ArrayList<Item>()
    lateinit var arrayTimer: ArrayList<CountDownTimer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = ArrayList()
        dataTimer = ArrayList()
        tempTimers = ArrayList()
        arrayTimer = ArrayList()
        var isRunning = true
        data.add(Item(10.1.seconds))
        dataModel.item.observe(this,{
            data.add(it)
            adapter.notifyItemInserted(data.size-1)
        })

        //Запуск последовательности таймеров
        binding.btStart.setOnClickListener {
            isRunning = true
            if(dataTimer.isNotEmpty()) {
                visibleGone()
                positionTimer = 0
                arrayTimer[positionTimer].start()
            }else{
                Toast.makeText(this,"Последовательность пуста",Toast.LENGTH_SHORT).show()
            }
        }
        //Полная очиста последовательности таймеров
        binding.btClear.setOnClickListener {
            if(dataTimer.isNotEmpty()){
                Log.d("MyLog","${data.size}")
                adapterTimer.notifyItemRangeRemoved(0,dataTimer.size)
                dataTimer.clear()
                arrayTimer.clear()
                Toast.makeText(this,"Список полностью очищен",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Список уже пуст",Toast.LENGTH_SHORT).show()
            }
        }
        //Полная остановка всех таймеров после их запуска
        binding.btFullStop.setOnClickListener {
            for(i in 0..arrayTimer.size-1){
                arrayTimer[0].cancel()
            }
            binding.btStop.isEnabled = true
            //Обновление элементов после завершения таймеров чтобы вернулся изначальный текст
            refreshItems(tempTimers)
            visibleShow()
        }
        //Остановка и запуск уже запущенной последовательности таймеров
        binding.btStop.setOnClickListener{
            if(isRunning){
                isRunning = false
                arrayTimer[positionTimer].cancel()
                createTimer("refresh",dataTimer[positionTimer])
            }else{
                arrayTimer[positionTimer].start()
                isRunning = true
            }
        }
        binding.btCreate.setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager,"123")
        }
        //Определение музыки
        mediaPlayer = MediaPlayer()
        mediaPlayer = MediaPlayer.create(this,R.raw.elec_alarm)

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
            dataTimer.add(data[position].copy())
            createTimer("create",data[position])
            tempTimers.clear()
            tempTimers = dataTimer.map { it.copy() }.toCollection(ArrayList())
            adapterTimer.notifyItemInserted(dataTimer.size-1)

        }, data)
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter
    }
    fun createTimer(code:String,item: Item){
        val timerAdd = object : CountDownTimer((item.time.toLong(DurationUnit.MILLISECONDS)),1000){
            override fun onTick(millisUntilFinished: Long) {
                adapterTimer.notifyItemChanged(positionTimer,"TEXT_CHANGED")
                dataTimer[positionTimer].time = millisUntilFinished.milliseconds
            }
            override fun onFinish() {
                positionTimer++
                if(positionTimer<=dataTimer.size-1) {
                    arrayTimer[positionTimer].start()
                }else{
                    binding.btStop.isEnabled = false
                }
            }
        }
        if(code=="create"){
            arrayTimer.add(timerAdd)
        }else if(code=="refresh"){
            arrayTimer[positionTimer] = timerAdd
        }
    }

    fun refreshItems(tempTimers: ArrayList<Item>){
        dataTimer.clear()
        dataTimer.addAll(tempTimers.map{it.copy()})
        for(i in 0..dataTimer.size-1){
            adapterTimer.notifyItemChanged(i,"TEXT_CHANGED")
        }
    }
    fun visibleGone(){
        binding.btStart.visibility = View.GONE
        binding.btClear.visibility = View.GONE
        binding.btFullStop.visibility = View.VISIBLE
        binding.btStop.visibility = View.VISIBLE
    }
    fun visibleShow(){
        binding.btStart.visibility = View.VISIBLE
        binding.btClear.visibility = View.VISIBLE
        binding.btStop.visibility = View.GONE
        binding.btFullStop.visibility = View.GONE
    }
}