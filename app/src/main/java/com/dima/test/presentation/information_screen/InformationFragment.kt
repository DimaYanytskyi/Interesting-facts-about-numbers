package com.dima.test.presentation.information_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dima.test.R
import com.dima.test.databinding.FragmentInformationBinding

class InformationFragment : Fragment(R.layout.fragment_information) {

    private lateinit var binding: FragmentInformationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInformationBinding.bind(view)

        val number = requireArguments().getInt("number")
        val fact = requireArguments().getString("fact")

        binding.apply {
            this.number.text = number.toString()
            description.text = fact
            back.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}