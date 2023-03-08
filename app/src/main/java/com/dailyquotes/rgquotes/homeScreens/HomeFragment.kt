package com.dailyquotes.rgquotes.homeScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dailyquotes.rgquotes.data.DataViewModel
import com.dailyquotes.rgquotes.data.Status
import com.dailyquotes.rgquotes.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Fragment that will contain the quote of the day
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: DataViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding?.apply {


            viewLifecycleOwner.lifecycleScope.launch{
                withContext(Dispatchers.IO) {
                    viewModel.getQuote()

                }
            }
            refresh.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        viewModel.getQuote()
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.status.collectLatest {
                        when (it) {
                            is Status.Loading<*> -> {

                                viewModel.clearUpdate()
                            }
                            is Status.Success<*> -> {
                                if(it.data != null){
                                    val fullquote = it.data[0].quote + " - " + it.data[0].author
                                    quote.text = fullquote
                                }

                                viewModel.clearUpdate()
                            }
                            is Status.Error<*> -> {
                                if(it.message != null){
                                    Toast.makeText(
                                        requireContext().applicationContext,
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                viewModel.clearUpdate()
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
        return binding?.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}