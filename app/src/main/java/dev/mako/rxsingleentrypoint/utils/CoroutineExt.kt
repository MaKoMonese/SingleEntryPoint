package dev.mako.rxsingleentrypoint.utils

import android.annotation.SuppressLint
import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

fun <T> Flow<T>.throttleFist(windowDuration: Long): Flow<T> = flow {
    var windowStartTime = System.currentTimeMillis()
    var emitted = false
    collect { value ->
        val currentTime = System.currentTimeMillis()
        val delta = currentTime - windowStartTime
        if (delta >= windowDuration) {
            windowStartTime += delta
            emitted = false
        }
        if (!emitted) {
            emit(value)
            emitted = true
        }
    }
}

const val CLICK_DEBOUNCE_DELAY = 400L

@SuppressLint("CheckResult")
fun View.debouncedClick(executeBlock: () -> Unit): Disposable {
    return clicks()
        .debounce(CLICK_DEBOUNCE_DELAY, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
        .subscribe({
            executeBlock.invoke()
        }, {/* todo handle an error here */ })
}