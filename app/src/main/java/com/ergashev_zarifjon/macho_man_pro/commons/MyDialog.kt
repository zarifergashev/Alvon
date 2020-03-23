package com.ergashev_zarifjon.macho_man_pro.commons

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ergashev_zarifjon.macho_man_pro.R
import kotlinx.android.synthetic.main.my_dialog_fragment.view.*
import kotlinx.android.synthetic.main.my_dialog_fragment_adapter_row.view.*
import java.util.*


class MyDialog {

    class Builder {

        private var mTitle: Any? = null
        private var mContent: View? = null
        private var mOptionIcons: MutableList<Any?> = mutableListOf()
        private var mOptionIcons2: MutableList<Any?> = mutableListOf()
        private var mOptionNames: MutableList<Any?> = mutableListOf()
        private var mOptionCommands: MutableList<Command?> = mutableListOf()
        private var callback: Callback? = null

        fun title(@NonNull title: CharSequence): Builder {
            if (this.mTitle != null) {
                throw DialogError("Repeat the same")
            }
            this.mTitle = title
            return this
        }

        fun title(@StringRes resId: Int): Builder {
            if (this.mTitle != null) {
                throw DialogError("Repeat the same")
            }
            this.mTitle = resId
            return this
        }


        fun contentView(@NonNull contentView: View): Builder {
            if (this.mOptionNames != null) {
                throw DialogError.Unsupported()
            }
            this.mContent = contentView
            return this
        }

        private fun optionPrivate(
            iconLeft: Any?,
            iconRight: Any?,
            name: Any?,
            command: Command?
        ): Builder {
            if (this.mContent != null) {
                throw DialogError.Unsupported()
            }
            if (this.mOptionNames == null) {
                this.mOptionIcons = ArrayList()
                this.mOptionNames = ArrayList()
                this.mOptionCommands = ArrayList()
            }
            this.mOptionIcons.add(iconLeft)
            this.mOptionIcons2.add(iconRight)
            this.mOptionNames.add(name)
            this.mOptionCommands.add(command)
            return this
        }

        fun option(iconLeft: Any, iconRight: Any, name: CharSequence, command: Command): Builder {
            return optionPrivate(iconLeft, iconRight, name, command)
        }

        fun option(name: CharSequence, command: Command): Builder {
            return optionPrivate(
                EMPTY_ICON,
                EMPTY_ICON, name, command)
        }

        fun option(iconLeft: Any, iconRight: Any, stringId: Int, command: Command): Builder {
            return optionPrivate(iconLeft, iconRight, stringId, command)
        }

        fun option(stringId: Int, command: Command): Builder {
            return optionPrivate(
                EMPTY_ICON,
                EMPTY_ICON, stringId, command)
        }

        fun <T> option(values: Collection<T>, command: MyCommandFacade<T>): Builder {
            for (`val` in values) {
                optionPrivate(command.getIconLeft(`val`),
                    command.getIconRight(`val`),
                    command.getName(`val`),
                    Command { command.apply(`val`) })
            }
            return this
        }

        fun <T> option(values: Array<T>, command: MyCommandFacade<T>): Builder {
            return option(listOf(*values), command)
        }

        fun callback(callback: Callback): Builder {
            if (this.callback != null) {
                throw DialogError("Repeat the same")
            }
            this.callback = callback
            return this
        }

        fun show(activity: FragmentActivity) {
            MyDialogFragment.show(
                activity.supportFragmentManager,
                mTitle,
                mOptionIcons,
                mOptionIcons2,
                mOptionNames,
                mOptionCommands
            )
        }

        companion object {
            private val EMPTY_ICON = Any()
        }
    }
}

class MyDialogFragment : DialogFragment() {

    var adapter: MyDialogFragmentAdapter? = null
    var title: Any? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (adapter != null)
            view.rv_dialog.adapter = adapter
        title?.let { title -> dialog?.setTitle(view.context.getText(title)) }
    }

    companion object {
        const val MY_DIALOG = "com.example.smartupmenu.my_dialog"
        fun show(
            fragmentManager: FragmentManager,
            title: Any?,
            mOptionIcons: MutableList<Any?>,
            mOption2Icons: MutableList<Any?>,
            mOptionNames: MutableList<Any?>,
            mOptionClickAction: MutableList<Command?>
        ) {
            val f = MyDialogFragment()
            f.adapter = MyDialogFragmentAdapter(
                mOptionIcons,
                mOption2Icons,
                mOptionNames,
                mOptionClickAction,
                f
            )
            f.title = title
            f.show(fragmentManager,
                MY_DIALOG
            )
        }
    }
}

class MyDialogFragmentAdapter(
    private val mOptionIcons: MutableList<Any?>,
    private val mOption2Icons: MutableList<Any?>,
    private val mOptionNames: MutableList<Any?>,
    private val mOptionClickAction: MutableList<Command?>,
    private val dialogFragment: MyDialogFragment
) : RecyclerView.Adapter<MyDialogFragmentAdapter.ViewHolder>(),
    View.OnClickListener {

    override fun onClick(v: View) {
        val tag = v.tag
        if (tag != null && tag is Command) {
            tag.apply()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val dialog = dialogFragment.dialog
            if (dialog != null && dialog.isShowing) {
                dialog.dismiss()
            }
        }, 500)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_dialog_fragment_adapter_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =
        if (mOptionNames.size == mOptionClickAction.size && mOptionNames.size == mOptionIcons.size) mOptionNames.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(this)
        holder.itemView.tag = mOptionClickAction[position]
        val icon = mOptionIcons[position]
        holder.itemView.iv_icon.visibility = if (icon != null) View.VISIBLE else View.GONE
        if (icon != null) {
            when (icon) {
                is Bitmap -> holder.itemView.iv_icon.setImageBitmap(icon)
                is Drawable -> holder.itemView.iv_icon.setImageDrawable(icon)
                is Int -> if (icon != -1) holder.itemView.iv_icon.setImageResource(icon)
                else -> Glide.with(holder.itemView.context).load(icon).into(holder.itemView.iv_icon)
            }
        }
        val icon2 = mOption2Icons[position]
        holder.itemView.iv_second_icon.visibility =
            if (icon2 != null) View.VISIBLE else View.GONE
        if (icon2 != null) {
            when (icon2) {
                is Bitmap -> holder.itemView.iv_second_icon.setImageBitmap(icon2)
                is Drawable -> holder.itemView.iv_second_icon.setImageDrawable(icon2)
                is Int -> if (icon2 != -1) holder.itemView.iv_second_icon.setImageResource(icon2)
                else -> Glide.with(holder.itemView.context).load(icon2).into(holder.itemView.iv_icon)
            }
        }
        val text = mOptionNames[position]
        holder.itemView.tv_text.text = text?.let { holder.itemView.context.getText(it) }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

interface MyCommandFacade<T> : CommandDialogFacade<T> {
    fun getIconLeft(`val`: T): Any?
    fun getIconRight(`val`: T): Any?
}

interface CommandDialogFacade<T> {

    @NonNull
    fun getName(`val`: T): CharSequence

    fun apply(`val`: T)

}

class DialogError : RuntimeException {

    constructor()

    constructor(detailMessage: String) : super(detailMessage)

    constructor(detailMessage: String, throwable: Throwable) : super(detailMessage, throwable)

    constructor(throwable: Throwable) : super(throwable)

    companion object {

        fun Unsupported(): DialogError {
            return DialogError("Unsupported")
        }

        fun NullPointer(): DialogError {
            return DialogError("NULL pointer")
        }

        fun checkNull(vararg args: Any) {
            for (a in args) {
                if (a == null) {
                    throw NullPointer()
                }
            }
        }

        fun Required(): DialogError {
            return DialogError("Required")
        }
    }

}
