package com.hfad.douyintest.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hfad.douyintest.R
import com.hfad.douyintest.databinding.ItemViewLocalVideoBinding
import com.hfad.douyintest.home.beans.VideoBean

class VideoListAdapter(private val mContext: Context, private val mDatas: ArrayList<VideoBean> ): RecyclerView.Adapter<VideoListAdapter.ItemVideoViewHolder>() {

    private lateinit var onVideoItemClickListener: OnVideoItemClickListener

    class ItemVideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var ivCover:ImageView
        var ivHead: ImageView
        var tvDistance: TextView
        var tvContent: TextView

        init{
            val binding = ItemViewLocalVideoBinding.bind(itemView)
            ivCover = binding.ivCover
            ivHead = binding.ivHead
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemVideoViewHolder, position: Int) {
        val videoBean = mDatas[position]
        holder.ivCover.setImageResource(videoBean.coverRes)
        holder.tvDistance.text = ""+videoBean.distance + "km"
        holder.ivHead.setImageResource(videoBean.userBean.head)
        holder.tvContent.text = videoBean.content

        holder.itemView.setOnClickListener {
            onVideoItemClickListener.onVideoItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    fun setOnVideoItemClickListener(onVideoItemClickListener: OnVideoItemClickListener){
        this.onVideoItemClickListener = onVideoItemClickListener
    }

    interface OnVideoItemClickListener{
        fun onVideoItemClick(position: Int)
    }
}


