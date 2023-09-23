package com.noahyip.keysoctest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.noahyip.keysoctest.R
import com.noahyip.keysoctest.apiService.ITuneService
import com.noahyip.keysoctest.model.ITuneSearchResponse


class ITuneSearchResultAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var iTuneSearchResponse: ITuneSearchResponse? = null
    private companion object {
        val VIEW_TYPE_ITEM = 0
        val VIEW_TYPE_LOADING = 1
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Two type of view in recycle view
        //show search result item or loading item
        //show loading image as last item

        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.itune_search_result_item, viewGroup, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.itune_search_result_loading, viewGroup, false)
            LoadingViewHolder(view)
        }

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            iTuneSearchResponse?.let { response ->
                val result = response.results[position]
                viewHolder.ivAlbum.load(result.artworkUrl100) {
                    placeholder(R.mipmap.img_loading)
                }
                viewHolder.tvName.text = result.trackName
                viewHolder.tvArtist.text = result.artistName
                viewHolder.btnPreview.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(response.results[position].previewUrl))
                    context.startActivity(intent)
                }
            }
        }// else if loading view holder, but do not require extra handling for loading view
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        //Show extra item as loading item if response is not null
        return if (iTuneSearchResponse?.hasMore == true) {
            iTuneSearchResponse?.results?.size?.plus(1) ?: 0
        } else {
            iTuneSearchResponse?.results?.size ?: 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        //if is last element, show loading view
        iTuneSearchResponse?.results?.size?.let {
            if (position >= it && iTuneSearchResponse?.hasMore == true) return VIEW_TYPE_LOADING
        }
        return VIEW_TYPE_ITEM
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

    //Empty class for loading view holder
    //No handling required
    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    //Update data set
    @SuppressLint("NotifyDataSetChanged")
    fun setData(iTuneSearchResponse: ITuneSearchResponse?) {
        this.iTuneSearchResponse = iTuneSearchResponse
        notifyDataSetChanged()
    }
}

