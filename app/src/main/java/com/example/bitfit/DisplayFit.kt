package com.example.bitfit

import android.support.annotation.Keep
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class DisplayFit(
    val food: String? = null,
    val calories: Double? = null
) : java.io.Serializable