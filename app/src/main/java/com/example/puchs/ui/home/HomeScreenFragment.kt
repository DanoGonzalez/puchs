package com.example.puchs.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.puchs.R
import com.example.puchs.core.Result
import com.example.puchs.core.hide
import com.example.puchs.core.show
import com.example.puchs.data.remote.home.HomeScreenDataSource
import com.example.puchs.databinding.FragmentHomeScreenBinding
import com.example.puchs.domain.home.HomeScreenRepoImpl
import com.example.puchs.presentation.home.HomeScreenViewModel
import com.example.puchs.presentation.home.HomeScreenViewModelFactory
import com.example.puchs.ui.home.adapter.HomeScreenAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(
            HomeScreenRepoImpl(
                HomeScreenDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.latestPosts.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.show()
                        }

                        is Result.Success -> {
                            binding.progressBar.hide()
                            if(result.data.isEmpty()) {
                                binding.emptyContainer.show()
                                return@collect
                            }else{
                                binding.emptyContainer.hide()
                            }
                            binding.rvHome.adapter = HomeScreenAdapter(result.data)
                        }

                        is Result.Failure -> {
                            binding.progressBar.hide()
                            Toast.makeText(
                                requireContext(),
                                "Ocurrio un error: ${result.exception}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}