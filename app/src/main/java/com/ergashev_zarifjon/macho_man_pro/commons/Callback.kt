package com.ergashev_zarifjon.macho_man_pro.commons

import android.view.View
import androidx.annotation.NonNull

interface Callback {
    fun onStateChanged(@NonNull bottomSheet: View, newState: Int): Boolean

    fun onSlide(@NonNull bottomSheet: View, slideOffset: Float): Boolean
}
