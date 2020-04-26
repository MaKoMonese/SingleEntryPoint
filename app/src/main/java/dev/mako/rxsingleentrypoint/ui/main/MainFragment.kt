package dev.mako.rxsingleentrypoint.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import dev.mako.rxsingleentrypoint.R
import dev.mako.rxsingleentrypoint.ui.main.MainViewModel.ButtonType
import dev.mako.rxsingleentrypoint.ui.main.MainViewModel.MainNavigation
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private val navController by lazy { NavHostFragment.findNavController(this) }

    private val viewModel  by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel.navigation.observe(requireActivity(), Observer(::onNavigation))
    }

    private fun onNavigation(navigation: MainNavigation) {
        when (navigation) {
            MainNavigation.FragmentOne -> openFragmentOne()
            MainNavigation.FragmentTwo -> openFragmentTwo()
            MainNavigation.FragmentThree -> openFragmentThree()
        }
    }

    private fun setupListeners() {
        setupListenersForRx()
        setupListenersForCorutines()
    }

    private fun setupListenersForCorutines() {
        btn_coroutine_fragment1.setOnClickListener {
            viewModel.onButtonClicked(ButtonType.FRAGMENT_ONE)
        }
        btn_coroutine_fragment2.setOnClickListener {
            viewModel.onButtonClicked(ButtonType.FRAGMENT_TWO)
        }
        btn_coroutine_fragment3.setOnClickListener {
            viewModel.onButtonClicked(ButtonType.FRAGMENT_THREE)
        }
    }

    private fun setupListenersForRx() {
        btn_rx_fragment1.setOnClickListener {
            viewModel.onButtonClickedRx(ButtonType.FRAGMENT_ONE)
        }
        btn_rx_fragment2.setOnClickListener {
            viewModel.onButtonClickedRx(ButtonType.FRAGMENT_TWO)
        }
        btn_rx_fragment3.setOnClickListener {
            viewModel.onButtonClickedRx(ButtonType.FRAGMENT_THREE)
        }
    }

    private fun openFragmentThree() {
        navController.navigate(R.id.fragment_three)
    }

    private fun openFragmentTwo() {
        navController.navigate(R.id.fragment_two)
    }

    private fun openFragmentOne() {
        navController.navigate(R.id.fragment_one)
    }
}
