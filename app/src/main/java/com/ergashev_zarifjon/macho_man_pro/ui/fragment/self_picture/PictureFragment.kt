package com.ergashev_zarifjon.macho_man_pro.ui.fragment.self_picture

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.commons.isOrentationLand
import com.ergashev_zarifjon.macho_man_pro.commons.showMessageDialog
import com.ergashev_zarifjon.macho_man_pro.ui.activity.IMainActivity
import com.ergashev_zarifjon.macho_man_pro.ui.activity.ImageFullScreenActivity
import kotlinx.android.synthetic.main.fragment_picture.*
import kotlinx.android.synthetic.main.fragment_picture.view.*
import kotlinx.android.synthetic.main.picture_list_row.view.*
import java.util.*


class PictureFragment : Fragment(), IPictureFragment {
    override fun btnPermission(visiblity: Int) {
        btnPermission.visibility = visiblity
    }

    override fun rvPicture(visible: Int) {
        rvImage.visibility = visible
    }

    override fun emptyImage(visible: Int) {

    }

    override fun checkPermission(doneFunc: () -> Unit, fail: () -> Unit, vararg list: String) {
        listener?.checkPermission(doneFunc, fail, *list)
    }

    override fun checkPermissionAndRequest(doneFunc: () -> Unit, fail: () -> Unit, vararg list: String) {
        listener?.checkPermissionAndRequest(doneFunc, fail, * list)
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(PictureViewModel::class.java).also {
            it.initView(this)
        }
    }

    private var listener: IMainActivity? = null
    private val adapter by lazy {
        SelfPictureAdapter { item ->
            activity?.let { ImageFullScreenActivity.open(it, item.imageUrl) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_picture, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainActivity) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spanCount = if (view.context.isOrentationLand()) 3 else 2

        val layoutManage = GridLayoutManager(view.context, spanCount)
        view.rvImage.layoutManager = layoutManage
        view.rvImage.adapter = adapter

        listener?.checkPermission({
            view.btnPermission.visibility = View.GONE
            view.rvImage.visibility = View.VISIBLE
        }, {
            view.btnPermission.visibility = View.VISIBLE
            view.rvImage.visibility = View.GONE
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        view.btnPermission.setOnClickListener {
            listener?.checkPermissionAndRequest({
                view.btnPermission.visibility = View.GONE
                view.rvImage.visibility = View.VISIBLE
                viewModel.dowlaondSelfPicture()
            }, {
                view.btnPermission.visibility = View.VISIBLE
                view.rvImage.visibility = View.GONE
                activity?.showMessageDialog(R.string.warning, R.string.you_need_all_permission)
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        view.tb_main_activity_toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        initObjects()
    }

    private fun initObjects() {

        viewModel.pictures.observe(this, androidx.lifecycle.Observer {
            adapter.setData(it.toMutableList())
        })

        viewModel.dowlaondSelfPicture()

    }

    override fun onStart() {
        super.onStart()
        viewModel.dowlaondSelfPicture()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    class SelfPictureAdapter(private val clickListener: (SelfPicture) -> Unit) :
        ListAdapter<SelfPicture, SelfPictureAdapter.ViewHolder>(
            SelfPicture.DIFF_UTILL
        ) {
        private val originalItems = mutableListOf<SelfPicture>()
        fun setData(list: MutableList<SelfPicture>) {
            originalItems.clear()
            originalItems.addAll(list)
            submitList(list)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.picture_list_row, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.onBind(getItem(position))
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private var item: SelfPicture? = null
            fun onBind(i: SelfPicture) {
                this.item = i
                itemView.tv_image_size.text = item?.imageSize
                itemView.setOnClickListener { item?.let { clickListener.invoke(it) } }
                Glide.with(itemView.context).load(item?.imageUrl).into(itemView.iv_self_picture)
            }

        }
    }

companion object{
    fun newInstance():Fragment{
        return PictureFragment()
    }
}
}

data class SelfPicture(val imageUrl: String, val imageSize: String, val createDate: Date) {
    companion object {
        val DIFF_UTILL = object : DiffUtil.ItemCallback<SelfPicture>() {
            override fun areItemsTheSame(oldItem: SelfPicture, newItem: SelfPicture) = oldItem == newItem

            override fun areContentsTheSame(oldItem: SelfPicture, newItem: SelfPicture) =
                oldItem.imageSize == newItem.imageSize && oldItem.imageUrl == newItem.imageUrl
        }
    }
}

