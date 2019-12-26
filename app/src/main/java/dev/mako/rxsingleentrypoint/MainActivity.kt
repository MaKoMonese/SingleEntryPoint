package dev.mako.rxsingleentrypoint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.mako.rxsingleentrypoint.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

}
