package com.noahyip.keysoctest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.gson.Gson
import com.noahyip.keysoctest.R
import com.noahyip.keysoctest.model.ITuneSearchResult
import com.noahyip.keysoctest.utils.SharePreferenceUtils


class ITuneFavouriteResultAdapter(
    private val context: Context,
    var favouriteResult: MutableMap<String, *>?
) :
    RecyclerView.Adapter<ITuneFavouriteResultAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.itune_search_result_item, viewGroup, false)
        return ItemViewHolder(view)

    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        favouriteResult?.toList()?.let {
            val result = Gson().fromJson(it.get(position).second.toString(), ITuneSearchResult::class.java)
            viewHolder.ivAlbum.load(result.artworkUrl100) {
                placeholder(R.mipmap.img_loading)
            }
            viewHolder.tvName.text = result.trackName
            viewHolder.tvArtist.text = result.artistName
            viewHolder.btnPreview.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(result.previewUrl))
                context.startActivity(intent)
            }
            viewHolder.ivLove.setOnClickListener {
                SharePreferenceUtils.removeFavourite(context, result.trackId.toString())
                favouriteResult = SharePreferenceUtils.getAllFavourite(context)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return favouriteResult?.size ?: 0
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAlbum: ImageView
        val tvName: TextView
        val tvArtist: TextView
        val btnPreview: AppCompatButton
        val ivLove: ImageView

        init {
            ivAlbum = view.findViewById(R.id.iv_album)
            tvName = view.findViewById(R.id.tv_name)
            tvArtist = view.findViewById(R.id.tv_artist)
            btnPreview = view.findViewById(R.id.btn_preview)
            ivLove = view.findViewById(R.id.iv_love)
        }
    }
}

