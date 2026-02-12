package com.example.customtimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.customtimer.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


class BottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding
    private val dataModel:DataModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val activity = requireActivity()
        binding.apply {

            NumberPicHour.minValue = 0
            NumberPicHour.maxValue = 23

            NumberPicMinutes.minValue = 0
            NumberPicMinutes.maxValue = 59

            NumberPicSecond.minValue = 0
            NumberPicSecond.maxValue = 59

            btFragCreate.setOnClickListener {
                val time = NumberPicHour.value.hours+NumberPicSecond.value.seconds+NumberPicMinutes.value.minutes
                dataModel.item.value = Item(time)
                dismiss()
            }
            btFragCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

}