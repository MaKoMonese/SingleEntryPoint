package dev.mako.rxsingleentrypoint.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.mako.rxsingleentrypoint.SingleLiveEvent
import dev.mako.rxsingleentrypoint.utils.throttleFist
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit

private const val CLICK_EMITTER_THROTTLE_DURATION = 1000L

class MainViewModel : ViewModel() {
    private val _navigation = SingleLiveEvent<MainNavigation>()
    val navigation: LiveData<MainNavigation> = _navigation

    private val buttonClickEmitterRx by lazy {
        PublishSubject.create<ButtonType>().apply {
            throttleFirst(CLICK_EMITTER_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::processButtonClick, { /* handle error here */ })
        }
    }

    private val parentJob = Job()
    private val channel = BroadcastChannel<ButtonType>(3)
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            coroutineScope.launch(Dispatchers.Main) {
                /* handle error here */
            }

            GlobalScope.launch { throwable.printStackTrace() }
        }
    private val coroutineScope = CoroutineScope(
        Dispatchers.Main + parentJob +
                coroutineExceptionHandler
    )

    private fun observe() {
        coroutineScope.launch {
            channel
                .asFlow()
                .throttleFist(CLICK_EMITTER_THROTTLE_DURATION)
                .collect {
                    processButtonClick(it)
                }
        }
    }

    init {
        observe()
    }

    fun onButtonClickedRx(type: ButtonType) {
        buttonClickEmitterRx.onNext(type)
    }

    fun onButtonClicked(type: ButtonType) {
        coroutineScope.launch {
            channel.send(type)
        }
    }

    private fun processButtonClick(type: ButtonType) {
        when (type) {
            ButtonType.FRAGMENT_ONE -> navigateToFragmentOne()
            ButtonType.FRAGMENT_TWO -> navigateToFragmentTwo()
            ButtonType.FRAGMENT_THREE -> navigateToFragmentThree()
        }
    }

    private fun navigateToFragmentOne() {
        _navigation.postValue(MainNavigation.FragmentOne)
    }

    private fun navigateToFragmentTwo() {
        _navigation.postValue(MainNavigation.FragmentTwo)
    }

    private fun navigateToFragmentThree() {
        _navigation.postValue(MainNavigation.FragmentThree)
    }

    sealed class MainNavigation {
        object FragmentOne : MainNavigation()
        object FragmentTwo : MainNavigation()
        object FragmentThree : MainNavigation()
    }

    enum class ButtonType {
        FRAGMENT_ONE,
        FRAGMENT_TWO,
        FRAGMENT_THREE
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}
