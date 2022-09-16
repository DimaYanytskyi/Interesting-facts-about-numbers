package com.dima.test.presentation.main_screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.dima.test.R
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dima.test.databinding.FragmentMainBinding
import com.dima.test.domain.model.NumberInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.numberInput.addTextChangedListener {
            if(it.toString().isNotEmpty()) {
                viewModel.setNumberInputText(it.toString().replace("-", "0").toInt())
            }
        }

        binding.getFact.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.fetchInfoByNumber()
        }

        binding.getRandomFact.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.fetchInfoByRandomNumber()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            showToast(it)
        }

        viewModel.numberInfo.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            if(it.found){
                openInfoFragment(it)
            } else if(!it.found && it.number != -1) {
                showToast("Can't find facts about this number")
            }
        }

        viewModel.listOfRecent.observe(viewLifecycleOwner) {
            val adapter = RecentAdapter(it.reversed()){ info ->
                openInfoFragment(info)
            }
            binding.recentViews.apply {
                layoutManager = LinearLayoutManager(requireContext())
                this.adapter = adapter
            }
        }
    }

    private fun showToast(text: String){
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    private fun openInfoFragment(info: NumberInfo){
        findNavController()
            .navigate(
                R.id.action_mainFragment_to_informationFragment,
                bundleOf(
                    "number" to info.number,
                    "fact" to info.fact
                )
            )
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch(Dispatchers.IO){ viewModel.setRecent() }
    }
}