package com.example.customtimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.customtimer.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding
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
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

}