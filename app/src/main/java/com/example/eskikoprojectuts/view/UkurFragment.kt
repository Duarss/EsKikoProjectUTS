package com.example.eskikoprojectuts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eskikoprojectuts.R
import com.example.eskikoprojectuts.databinding.FragmentUkurBinding


class UkurFragment : Fragment() {
    private lateinit var binding: FragmentUkurBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUkurBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }




}