package com.kradwan.codegeneartormvvmsample.presentation.start_feature.start

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.kradwan.codegeneartormvvmsample.databinding.FragmentHomeBinding
import com.kradwan.codegeneartormvvmsample.presentation._base.ViewBindingFragment
import com.kradwan.codegeneartormvvmsample.presentation.start_feature.state.TestStateEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartMainFragment : ViewBindingFragment<FragmentHomeBinding>() {


    private val viewModel: StartMainViewModel by viewModels()

    override fun setup() = with(binding) {
        vm = viewModel
        lifecycleOwner = this@StartMainFragment
        initViews()


        binding.btnHome.setOnClickListener {
            viewModel.setStateEvent(TestStateEvent.MakeToast("Hello "))
        }
    }

    private fun initViews() {
        subscribeObservers()


        binding.tvLabel.setOnClickListener {
            // TODO: Make Your Action Here
            Toast.makeText(requireActivity(), "Make Your Action Here", Toast.LENGTH_LONG).show()
        }
    }


    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            dataState.data?.let {
                Log.d("DDDD", "DataState data: ${it}")

                it.list?.let {
                    // TODO : Response of GetList Event
                }

                it.toast?.let {
                    Toast.makeText(requireActivity(), "Toast: ${it}", Toast.LENGTH_LONG).show()
                }

            }
            dataState.loading?.let {
                Log.d("DDDD", "DataState loading: ${it}")
            }

            dataState.error?.let {
                Log.d("DDDD", "DataState Error: ${it}")
            }
        }

        viewModel.navigateScreen.observe(requireActivity()) {
            try {
                val action: NavDirections = it as NavDirections
                NavHostFragment.findNavController(this).navigate(action)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override val _bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate
}