package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.ServiceLocator
import com.ergashev_zarifjon.macho_man_pro.commons.showMessageDialog
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.ProgresDialog
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.EIBaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.ei_photo_filter.*
import kotlinx.android.synthetic.main.ei_photo_filter.view.*
import kotlinx.android.synthetic.main.f_content_main.*
import java.io.File
import java.util.ArrayList

class EIPhotFilter : EIBaseFragment(), IEIPhotFilter {

    init {
        System.loadLibrary("NativeImageProcessor")
    }

    override fun getContexts(): Context = activity!!

    private val progresDialog by lazy {
        ProgresDialog()
    }

    override fun getOriginalImage(doneAction: (Bitmap) -> Unit) {
        listener?.readTempImage(withProgress = false) { it?.let { it1 -> doneAction.invoke(it1) } }
    }

    companion object {
        val ARG_TYPE: String = "ARG_TYPE"
        fun newInstance(type: String) = EIPhotFilter().also {
            it.arguments = Bundle().also {
                it.putString(ARG_TYPE, type)
            }
        }
    }

    var originalImage: Bitmap? = null
    var filteredImage: Bitmap? = null
    var finalImage: Bitmap? = null

    private val filtersListFragment: EIPhotoFiltersListFragment by lazy { EIPhotoFiltersListFragment() }

    private val eIPhotoFilterEditImageFragment: EIPhotoFilterEditImageFragment by lazy {
        EIPhotoFilterEditImageFragment()
    }

    var brightnessFinal = 0
    var saturationFinal = 1.0f
    var contrastFinal = 1.0f


    private val factory by lazy {
        EIPhotoFilterViewModelFactory()
    }

    private val viewmodel by lazy {
        ViewModelProviders.of(activity!!, factory).get(EIPhotoFilterViewModel::class.java).apply {
            this.initView(this@EIPhotFilter)
        }
    }

    private val adapter by lazy {
        ViewPagerAdapter(fragmentManager!!).apply {
            this.addFragment(filtersListFragment, getString(R.string.tab_filters))
            this.addFragment(eIPhotoFilterEditImageFragment, getString(R.string.tab_edit))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.ei_photo_filter, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initObject()
    }

    private fun initObject() {
        viewmodel.image.observe(this, Observer {
            image_preview.setImageBitmap(it)
        })

        viewmodel.progress.observe(this, Observer {
            if (it) {
                showProgress()
            } else {
                hideProgress()
            }
        })

        viewmodel.errorMessage.observe(this, Observer {
            activity?.showMessageDialog(
                R.string.error,
                message = it,
                positiveBtnText = R.string.ok,
                positiveBtnAction = {})
        })

        listener?.readTempImage(withProgress = false) {
            it?.let { it1 -> viewmodel.setOriginalImage(it1) }
        }
    }

    fun showProgress() {
        if (progresDialog.isAdded) {
            return
        }
        fragmentManager?.let { progresDialog.show(it, progresDialog.tag) }
    }

    fun hideProgress() {
        if (progresDialog.isAdded) progresDialog.dialog?.dismiss()
    }


    private fun initViews(view: View) {
        view.ei_wear_iv_done.setOnClickListener { saveImageToGallery() }
        view.ei_wear_iv_back.setOnClickListener {
            context?.showMessageDialog(
                R.string.warning,
                R.string.are_you_sure_want_to_exit,
                positiveBtnText = R.string.yes,
                positiveBtnAction = {
                    listener?.onBackPress()
                },
                negativBtnText = R.string.no,
                negativBtnAction = {

                }
            )
        }
        viewpager.adapter = adapter
        adapter.notifyDataSetChanged()
        tabs.setupWithViewPager(viewpager)
    }

    override fun onStart() {
        super.onStart()
        loadImage()
    }

    /**
     * Resets image edit controls to normal when new filter
     * is selected
     */
    private fun resetControls() {
        eIPhotoFilterEditImageFragment.resetControls()
        brightnessFinal = 0
        saturationFinal = 1.0f
        contrastFinal = 1.0f
    }

    @SuppressLint("WrongConstant")
    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    // load the default image from assets on app launch
    private fun loadImage() {
        listener?.readTempImage(withProgress = false) { image ->
            image?.let {
                originalImage = it
                viewmodel.setOriginalImage(it)
                filteredImage = originalImage!!.copy(Bitmap.Config.ARGB_8888, true)
                finalImage = originalImage!!.copy(Bitmap.Config.ARGB_8888, true)
                image_preview.setImageBitmap(originalImage)
            }
        }
    }


    private fun saveImageToGallery() {
        viewmodel.image.value?.let {
            showProgress()
            listener?.saveTempImage(it) { path ->
                hideProgress()
                if (!TextUtils.isEmpty(path)) {
                    listener?.onBackPress()
                } else {
                    val snackbar = Snackbar.make(
                        coordinator_layout,
                        "Unable to change image!",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                }
            }
        }
    }
}