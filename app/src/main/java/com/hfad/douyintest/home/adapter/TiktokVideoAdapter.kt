package com.hfad.douyintest.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.hfad.douyintest.R
import com.hfad.douyintest.databinding.ItemTiktokViewBinding
import com.hfad.douyintest.home.adapter.TiktokVideoAdapter.*
import com.hfad.douyintest.home.beans.VideoBean
import com.hfad.douyintest.home.view.InteractView
import com.hfad.douyintest.home.view.PauseAndLikeView

class TiktokVideoAdapter(val context: Context, val datas: List<VideoBean>): RecyclerView.Adapter<TiktokViewHolder>() {

    class TiktokViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var viewInteract: InteractView
        var viewPauseLike: PauseAndLikeView
        var rlContainer: RelativeLayout
        var ivPlay: ImageView
        var ivCoverVideo: ImageView

        init {
            val binding = ItemTiktokViewBinding.bind(itemView)
            ivCoverVideo = binding.ivCoverVideo
            ivPlay = binding.ivPlay
            rlContainer = binding.rlContainer
            viewPauseLike = binding.viewPauseLike
            viewInteract = binding.viewInteract
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiktokViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tiktok_view, parent, false)
        return TiktokViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TiktokViewHolder, position: Int) {
        val videoBean = datas[position]
        holder.viewInteract.setVideoInfoData(videoBean)
        holder.ivCoverVideo.setImageResource(videoBean.coverRes)
    }

    override fun getItemCount(): Int = datas.size

}