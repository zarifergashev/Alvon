package com.ergashev_zarifjon.macho_man_pro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterItemMenu
import com.ergashev_zarifjon.macho_man_pro.model.FilterEditType
import kotlinx.android.synthetic.main.ei_image_filter_row.view.*


class EditImageFilterAdapter(private val clickListener: ((FilterItemMenu) -> Unit)) :
    ListAdapter<FilterItemMenu, EditImageFilterAdapter.ViewHolder>(FilterItemMenu.DIFF_UTIL) {
    private var mInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mInflater == null)
            mInflater = LayoutInflater.from(parent.context)
        val view = mInflater!!.inflate(R.layout.ei_image_filter_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var item: FilterItemMenu? = null
        fun onBind(i: FilterItemMenu) {
            item = i
            itemView.ei_edit_type_row_container.setOnClickListener {
                item?.let { item ->
                    clickListener.invoke(
                        item
                    )
                }
            }
            //item?.background?.let { if (it != -1) itemView.ei_edit_type_row_container.setBackgroundResource(it) }

            /* val myOptions = RequestOptions()
                 .override(50, 50)*/
            item?.imgUrl?.let {
                Glide.with(itemView.context)
                    .load(it)
                    //.apply(myOptions)
                    .into(itemView.iv_edit_type_row)
            }
        }
    }
}