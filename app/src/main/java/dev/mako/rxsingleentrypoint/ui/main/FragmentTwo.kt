package dev.mako.rxsingleentrypoint.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.mako.rxsingleentrypoint.R

class FragmentTwo : Fragment() {

    companion object {
        fun newInstance() = FragmentTwo()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_two, container, false)
    }
}
