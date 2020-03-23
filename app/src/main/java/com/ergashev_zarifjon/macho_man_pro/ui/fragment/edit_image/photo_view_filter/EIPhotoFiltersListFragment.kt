package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.ServiceLocator
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.EIBaseFragment
import com.zomato.photofilters.imageprocessors.Filter
import kotlinx.android.synthetic.main.fragment_filters_list.*


class EIPhotoFiltersListFragment : EIBaseFragment(),
    ThumbnailCallback, IEIPhotFilter {

    override fun getOriginalImage(doneAction: (Bitmap) -> Unit) {
        listener?.readTempImage(withProgress = false) {
            if (it != null) {
                doneAction.invoke(it)
            }
        }
    }

    override fun getContexts(): Context = activity!!


    private val mAdapter: ThumbnailsAdapter by lazy {
        ThumbnailsAdapter(
            //thumbnailItemList,
            this
        )
    }

    private val thumbnailItemList: MutableList<ThumbnailItem> = mutableListOf()

    private val factory by lazy {
        EIPhotoFilterViewModelFactory(
        )
    }

    private val viewmodel by lazy {
        ViewModelProviders.of(activity!!, factory).get(EIPhotoFilterViewModel::class.java).apply {
            initView(this@EIPhotoFiltersListFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_filters_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObjects()
    }

    private fun initViews() {
        recycler_view.itemAnimator = DefaultItemAnimator()
        val space = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 8f,
            resources.displayMetrics
        ).toInt()
        recycler_view.addItemDecoration(
            SpacesItemDecoration(
                space
            )
        )
        recycler_view.adapter = mAdapter
    }

    private fun initObjects() {
        viewmodel.filterList.observe(this, Observer {
            if (it.isNotEmpty())
                viewmodel.onFilterSelected(it.first().filter)
            mAdapter.submitList(it)

        })
        viewmodel.dowloandList()
    }

    override fun onThumbnailClick(filter: Filter) {
        viewmodel.onFilterSelected(filter)
    }

}