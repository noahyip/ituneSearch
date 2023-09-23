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
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.noahyip.keysoctest.R
import com.noahyip.keysoctest.model.ITuneSearchResponse

class ITuneSearchResultAdapter (private val context: Context) :
    RecyclerView.Adapter<ITuneSearchResultAdapter.ViewHolder>() {

    private var iTuneSearchResponse: ITuneSearchResponse? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_album: ImageView
        val tv_name: TextView
        val tv_artist: TextView

        init {
            iv_album = view.findViewById(R.id.iv_album)
            tv_name = view.findViewById(R.id.tv_name)
            tv_artist = view.findViewById(R.id.tv_artist)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.itune_search_result_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        iTuneSearchResponse?.let { response ->
            val result = response.results[position]
            viewHolder.iv_album.load(result.artworkUrl100) {
                placeholder(R.mipmap.img_loading)
            }
            viewHolder.tv_name.text = result.trackName
            viewHolder.tv_artist.text = result.artistName
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = iTuneSearchResponse?.resultCount?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(iTuneSearchResponse: ITuneSearchResponse) {
        this.iTuneSearchResponse = iTuneSearchResponse
        notifyDataSetChanged()
    }
}

