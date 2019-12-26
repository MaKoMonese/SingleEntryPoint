package dev.mako.rxsingleentrypoint.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import dev.mako.rxsingleentrypoint.R
import dev.mako.rxsingleentrypoint.ui.main.MainViewModel.ButtonType
import dev.mako.rxsingleentrypoint.ui.main.MainViewModel.MainNavigation
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private val navController by lazy { NavHostFragment.findNavController(this) }

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

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
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.navigation.observe(this, Observer(::onNavigation))
    }

    private fun onNavigation(navigation: MainNavigation) {
        when (navigation) {
            MainNavigation.FragmentOne -> openFragmentOne()
            MainNavigation.FragmentTwo -> openFragmentTwo()
            MainNavigation.FragmentThree -> openFragmentThree()
        }
    }

    private fun setupListeners() {
        btn_rx_fragment1.setOnClickListener {
            viewModel.onButtonClickedRx(ButtonType.FRAGMENT_ONE)
        }
        btn_rx_fragment2.setOnClickListener {
            viewModel.onButtonClickedRx(ButtonType.FRAGMENT_TWO)
        }
        btn_rx_fragment3.setOnClickListener {
            viewModel.onButtonClickedRx(ButtonType.FRAGMENT_THREE)
        }

        btn_coroutine_fragment1.setOnClickListener {
            viewModel.onButtonClicked(ButtonType.FRAGMENT_ONE)
        }
        btn_coroutine_fragment2.setOnClickListener {
            viewModel.onButtonClicked(ButtonType.FRAGMENT_TWO)
        }
        btn_coroutine_fragment3.setOnClickListener {
            viewModel.onButtonClicked(ButtonType.FRAGMENT_ONE)
        }
    }

    private fun openFragmentThree() {
        val opts = NavOptions.Builder()
            .setEnterAnim(android.R.anim.slide_in_left)
            .setExitAnim(android.R.anim.slide_out_right)
            .build()
        navController.navigate(R.id.action_fragment_three, null, opts)
    }

    private fun openFragmentTwo() {
        val opts = NavOptions.Builder()
            .setEnterAnim(android.R.anim.slide_in_left)
            .setExitAnim(android.R.anim.slide_out_right)
            .build()
        navController.navigate(R.id.action_fragment_two, null, opts)
    }

    private fun openFragmentOne() {
        val opts = NavOptions.Builder()
            .setEnterAnim(android.R.anim.slide_in_left)
            .setExitAnim(android.R.anim.slide_out_right)
            .build()
        navController.navigate(R.id.action_fragment_one, null, opts)
    }
}
