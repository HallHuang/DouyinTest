package com.hfad.douyintest.home.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hfad.douyintest.R
import com.hfad.douyintest.databinding.ItemViewLocalVideoBinding
import com.hfad.douyintest.home.beans.VideoBean

class VideoListAdapter(val mContext: Context, val mDatas: ArrayList<VideoBean> ): RecyclerView.Adapter<VideoListAdapter.ItemVideoViewHolder>() {

    private lateinit var onVideoItemClickListener: OnVideoItemClickListener

    class ItemVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivCover:ImageView
        var ivHead: ImageView
        var tvIcon: TextView
        var tvDistance: TextView
        var tvContent: TextView

        init {
            val binding = ItemViewLocalVideoBinding.bind(itemView)
            ivCover = binding.ivCover
            ivHead = binding.ivHead
            tvIcon = binding.iconLocation
            tvDistance = binding.tvDistance
            tvContent = binding.tvContent
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemVideoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_local_video, parent, false)
        return ItemVideoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemVideoViewHolder, position: Int) {
        val videoBean = mDatas[position]
        holder.ivCover.setImageResource(videoBean.coverRes)
        holder.tvDistance.setText(""+videoBean.distance + "km")
        holder.ivHead.setImageResource(videoBean.userBean.head)
        holder.tvContent.setText(videoBean.content)

        holder.itemView.setOnClickListener {
            onVideoItemClickListener.OnVideoItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    fun setOnVideoItemClickListener(onVideoItemClickListener: OnVideoItemClickListener){
        this.onVideoItemClickListener = onVideoItemClickListener
    }

    interface OnVideoItemClickListener{
        fun OnVideoItemClick(position: Int)
    }
}


