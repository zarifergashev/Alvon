package com.ergashev_zarifjon.macho_man_pro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.commons.changeDrawableColor
import com.ergashev_zarifjon.macho_man_pro.model.EditType
import com.ergashev_zarifjon.macho_man_pro.model.EditTypeList
import kotlinx.android.synthetic.main.ei_edit_type_row.view.*


class EditTypeAdapter(private val clickListener: ((EditType) -> Unit)) :
    ListAdapter<EditType, EditTypeAdapter.ViewHolder>(EditType.DIFF_UTIL) {
    private var mInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mInflater == null)
            mInflater = LayoutInflater.from(parent.context)
        val view = mInflater!!.inflate(R.layout.ei_edit_type_row, null, false)
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
            item?.background?.let { itemView.ei_edit_type_row_container.setBackgroundResource(it) }
            itemView.ei_edit_type_row_tv_save.text = item?.type_name
            /* val myOptions = RequestOptions()
                 .override(50, 50)*/

            item?.let {
                if (it.type_code == EditTypeList.FILTERS) {
                    itemView.ei_edit_type_row_iv_type.setImageDrawable(
                        itemView.context.changeDrawableColor(
                            it.type_img,
                            R.color.white
                        )
                    )
                } else
                    Glide.with(itemView.context)
                        .load(it.type_img)
                        //  .apply(myOptions)
                        .into(itemView.ei_edit_type_row_iv_type)
            }
        }
    }
}