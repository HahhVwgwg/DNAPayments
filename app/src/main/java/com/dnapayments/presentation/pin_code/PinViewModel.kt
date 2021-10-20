package com.dnapayments.presentation.pin_code

import android.content.res.Resources
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.R
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PinViewModel(private val prefsAuth: PrefsAuth) : BaseViewModel() {
    val widgetEnable = ObservableBoolean(true)

    //  Biometric
    val isBiometric = ObservableBoolean(false)
    val isFaceId = ObservableBoolean(false)
    val forgot = ObservableBoolean(false)

    //  Loader
    val isLoader = MutableLiveData<Boolean>()

    //  Click listener for PinCodeFragment
    val clickListener = MutableLiveData<Int>()

    //  Dots in PinCodeFragment
    var dots = ObservableArrayList<Boolean>().apply {
        for (i in 0..3) add(false)
    }
    var pinTitle = ObservableField("")

    //  Drop login or password
    val dropLoginOrPassword = MutableLiveData<Boolean>()

    private var isPinCodeExist = false
    private var pin: String = ""
    private var pinRepeat: String = ""
    private var resources: Resources? = null

    fun launchBiometric() = viewModelScope.launch {
        delay(1000)
        enterPin()
        clickListener.value = 20
    }

    fun setPin() {
        isPinCodeExist = false
    }

    fun enterPin() {
        try {
            isPinCodeExist = prefsAuth.isPinCodeExist()
            if (isPinCodeExist) {
                //  Load pin code
                prefsAuth.loadPin()?.let { pin = it }
                //  If pin empty, set false
                if (pin.length < 4) isPinCodeExist = false
            }
        } catch (e: Exception) {

        }
    }

    //  Click action
    fun onClickItem(btnIndex: Int) {
        when (btnIndex) {
            //  Click -> digits
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> if (isPinCodeExist) enterPinCode(btnIndex) else createPinCode(
                btnIndex
            )
            //  Click -> biometric data
            20 -> clickListener.value = btnIndex
            //  Click -> backspace
            11 -> backspace()
        }
    }

    private fun backspace() {
        if (isPinCodeExist) {
            if (pinRepeat.isNotEmpty()) {
                val z = pinRepeat.length - 1
                pinRepeat = pinRepeat.dropLast(1)
                dots[z] = false
            }
        } else {
            if (pinRepeat.isNotEmpty()) {
                val z = pinRepeat.length - 1
                pinRepeat = pinRepeat.dropLast(1)
                dots[z] = false
                if (pinRepeat.isEmpty()) {
                    for (i in 0..3) dots[i] = true
                    resources?.let {
                        pinTitle.set(it.getString(R.string.enter_pin_code))
                    }
                }
            } else if (pin.isNotEmpty()) {
                val z = pin.length - 1
                pin = pin.dropLast(1)
                dots[z] = false
                println()
            }
        }
    }

    private fun enterPinCode(pinCode: Int) {
        if (pinRepeat.length < 4) {
            //  Set digit to variable
            pinRepeat += pinCode.toString()
            //  Change dot[position] background
            dots[pinRepeat.length - 1] = true
            //  Check if pin equal
            if (pinRepeat.length == 4) {
                //  Good -> equal
                if (pin == pinRepeat) {
                    clickListener.value = 200
                    widgetEnable.set(false)
                }
                //  Bad -> not equal
                else {
                    forgot.set(true)
                    clickListener.value = 400
                    for (i in 0..3) dots[i] = false
                    pinRepeat = ""
                }
            }
        }
    }

    private fun createPinCode(pinCode: Int) {
        //  First time, choice and save pin code
        if (pin.length == 4) {
            if (pinRepeat.length < 4) {
                pinRepeat += pinCode.toString()
                dots[pinRepeat.length - 1] = true
                if (pinRepeat.length == 4) {
                    widgetEnable.set(false)
                    if (pin == pinRepeat) {
                        prefsAuth.savePin(pin)
                        clickListener.value = 200
                    } else {
                        error.value = R.string.code_not_same
                        clickListener.value = 400
                        clearDots()
                        resources?.let {
                            pinTitle.set(it.getString(R.string.enter_pin_code))
                        }
                        widgetEnable.set(true)
                    }
                }
            }
        } else {
            pin += pinCode.toString()
            dots[pin.length - 1] = true
            if (pin.length == 4) {
                for (i in 0..3) dots[i] = false
                resources?.let {
                    pinTitle.set(it.getString(R.string.repeat_pin_code))
                }
            }
        }
    }

    private fun clearDots() {
        for (i in 0..3) dots[i] = false
        if (!isPinCodeExist) pin = ""
        pinRepeat = ""
        clickListener.value = -1
    }

    fun updateChangeText(resources: Resources, type: Int) {
        this.resources = resources
        pinTitle.set(resources.getString(if (type != 1 && type != 3) R.string.create_pin_code else R.string.enter_pin_code))
    }
}