package com.noahyip.searchgallery.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noahyip.keysoctest.R


class ITuneFilterAdapter(private val context: Context, val filterList: ArrayList<String>) :
    RecyclerView.Adapter<ITuneFilterAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    var selectItem = 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvOption: TextView

        init {
            tvOption = view.findViewById(R.id.tv_option)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.itune_filter_item, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvOption.text = filterList[position]
        viewHolder.tvOption.setOnClickListener {
            onClickListener?.let {
                onClickListener!!.onClick(position)
            }
        }
        if (position == selectItem) {
            viewHolder.tvOption.background = context.getDrawable(R.drawable.bkg_button)
        } else {
            viewHolder.tvOption.background = context.getDrawable(R.drawable.bkg_filter_button)
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun getItemCount() = filterList.size
}

