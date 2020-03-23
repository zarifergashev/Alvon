package com.ergashev_zarifjon.macho_man_pro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.model.EditType
import kotlinx.android.synthetic.main.ei_edit_sub_type_row.view.*


class EditSubTypeAdapter(private val clickListener: ((EditType) -> Unit)) :
    ListAdapter<EditType, EditSubTypeAdapter.ViewHolder>(EditType.DIFF_UTIL) {
    private var mInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mInflater == null)
            mInflater = LayoutInflater.from(parent.context)
        val view = mInflater!!.inflate(R.layout.ei_edit_sub_type_row, null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var item: EditType? = null
        fun onBind(i: EditType) {
            item = i
            itemView.ei_edit_type_row_container.setOnClickListener { item?.let { item -> clickListener.invoke(item) } }
            item?.background?.let { if (it != -1) itemView.ei_edit_type_row_container.setBackgroundResource(it) }

            item?.let {
                if (it.type_name.isNotEmpty())
                    itemView.ei_edit_type_row_tv_save.text = it.type_name
                else {
                    itemView.ei_edit_type_row_tv_save.visibility = View.GONE
                }
            }
           /* val myOptions = RequestOptions()
                .override(50, 50)*/
            item?.type_img?.let {
                Glide.with(itemView.context)
                    .load(it)
                    //.apply(myOptions)
                    .into(itemView.ei_edit_type_row_iv_type)
            }
        }
    }
}