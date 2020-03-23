package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image


import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.brush_view.IPhotoSaveHelper
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.PropertiesBSFragment
import kotlinx.android.synthetic.main.brush_fragment.*
import kotlinx.android.synthetic.main.brush_fragment.view.*


class BrushFragment : EIBaseFragment(), PropertiesBSFragment.Properties {

    override fun onColorChanged(colorCode: Int) {

    }

    override fun onOpacityChanged(opacity: Int) {

    }

    override fun onBrushSizeChanged(brushSize: Int) {
    }


    private var imageBitmap: Bitmap? = null
    private var saveHelper: IPhotoSaveHelper? = null
    private var isBrushVisible = true
    fun setImageBitmap(bitmap: Bitmap) {
        this.imageBitmap = bitmap
    }

    private val mPropertiesBSFragment by lazy {
        PropertiesBSFragment().also {
            it.setPropertiesChangeListener(this)
        }
    }

    fun setSaveHelper(saveHelper: IPhotoSaveHelper) {
        this.saveHelper = saveHelper
    }

    private val mConstraintSet = ConstraintSet()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.brush_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

    }


    /*   fun change_color() {
           val bitmap = (ei_photo_editor.drawable as BitmapDrawable).bitmap
           val O = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)

           for (i in 0 until bitmap.width) {
               for (j in 0 until bitmap.height) {
                   val p = bitmap.getPixel(i, j)
                   var b = Color.blue(p)

                   val x = 0
                   val y = 0
                   b += 150
                   O.setPixel(i, j, Color.argb(Color.alpha(p), x, y, b))
               }
           }
           ei_photo_editor.setImageBitmap(O)
       }*/
    var bitmap: BitmapDrawable? = null

    fun change_color() {
        bitmap = (ei_photo_editor.drawable as BitmapDrawable)
        bitmap?.setColorFilter(ei_photo_editor.context.resources.getColor(R.color.test), PorterDuff.Mode.MULTIPLY)
        ei_photo_editor.setImageDrawable(bitmap)
        //  ei_photo_editor.invalidate()
    }

    private fun initViews(view: View) {

        view.ei_photo_editor.setImageBitmap(imageBitmap)
        bitmap = view.ei_photo_editor.drawable as BitmapDrawable?
        view.ei_photo_editor.setOnClickListener { change_color() }
        fragmentManager?.beginTransaction()
            ?.add(R.id.toolContainer, mPropertiesBSFragment)
            ?.commit()
        showBrush(isBrushVisible)
        iv_done.setOnClickListener {
            saveHelper?.imageSave(bitmap?.bitmap)
            listener?.onBackPress()

        }
    }


    private fun showBrush(isVisible: Boolean) {
        mConstraintSet.clone(rootView)
        if (isVisible) {
            toolContainer.visibility = View.VISIBLE
            mConstraintSet.clear(toolContainer.id, ConstraintSet.START)
            mConstraintSet.connect(
                toolContainer.id, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.START
            )
            mConstraintSet.connect(
                toolContainer.id, ConstraintSet.END,
                ConstraintSet.PARENT_ID, ConstraintSet.END
            )
        } else {
            mConstraintSet.connect(
                toolContainer.id, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.END
            )
            mConstraintSet.clear(toolContainer.getId(), ConstraintSet.END)
        }
        val changeBounds = ChangeBounds()
        changeBounds.duration = 350
        changeBounds.interpolator = AnticipateOvershootInterpolator(1.0f)
        TransitionManager.beginDelayedTransition(rootView, changeBounds)
        mConstraintSet.applyTo(rootView)
        isBrushVisible = !isBrushVisible

    }

    companion object {
        fun newInstance(image: Bitmap, saveHelper: IPhotoSaveHelper): Fragment {
            val fragment = BrushFragment()
            fragment.setImageBitmap(image)
            fragment.setSaveHelper(saveHelper)
            return fragment
        }


    }
}