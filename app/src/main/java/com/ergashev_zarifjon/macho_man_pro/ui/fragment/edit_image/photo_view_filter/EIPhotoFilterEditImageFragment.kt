package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar

import androidx.lifecycle.ViewModelProviders

import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.ServiceLocator
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.EIBaseFragment
import kotlinx.android.synthetic.main.fragment_edit_image.*


class EIPhotoFilterEditImageFragment : EIBaseFragment(), IEIPhotFilter,
    SeekBar.OnSeekBarChangeListener {

    override fun getOriginalImage(doneAction: (Bitmap) -> Unit) {
        listener?.readTempImage(false) {
            if (it != null) {
                doneAction.invoke(it)
            }
        }
    }

    override fun getContexts(): Context = activity!!


    private val factory by lazy {
        EIPhotoFilterViewModelFactory()
    }

    private val viewmodel by lazy {
        ViewModelProviders.of(activity!!, factory).get(EIPhotoFilterViewModel::class.java).apply {
            initView(this@EIPhotoFilterEditImageFragment)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_edit_image, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // keeping brightness value b/w -100 / +100
        seekbar_brightness.max = 200
        seekbar_brightness.progress = 100

        // keeping contrast value b/w 1.0 - 3.0
        seekbar_contrast.max = 20
        seekbar_contrast.progress = 0

        // keeping saturation value b/w 0.0 - 3.0
        seekbar_saturation.max = 30
        seekbar_saturation.progress = 10

        seekbar_brightness.setOnSeekBarChangeListener(this)
        seekbar_contrast.setOnSeekBarChangeListener(this)
        seekbar_saturation.setOnSeekBarChangeListener(this)


    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
        var progress = progress
        if (seekBar.id == R.id.seekbar_brightness) {
            // brightness values are b/w -100 to +100
            viewmodel.onBrightnessChanged(progress - 100)
        }

        if (seekBar.id == R.id.seekbar_contrast) {
            // converting int value to float
            // contrast values are b/w 1.0f - 3.0f
            // progress = progress > 10 ? progress : 10;
            progress += 10
            val floatVal = .10f * progress
            viewmodel.onContrastChanged(floatVal)
        }

        if (seekBar.id == R.id.seekbar_saturation) {
            // converting int value to float
            // saturation values are b/w 0.0f - 3.0f
            val floatVal = .10f * progress
            viewmodel.onSaturationChanged(floatVal)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        viewmodel.onEditStarted()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        viewmodel.onEditCompleted()
    }

    fun resetControls() {
        seekbar_brightness.progress = 100
        seekbar_contrast.progress = 0
        seekbar_saturation.progress = 10
    }
}