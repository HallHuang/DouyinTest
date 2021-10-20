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
import com.hfad.douyintest.home.beans.VideoBean

class VideoListAdapter(val mContext: Context, val mDatas: ArrayList<VideoBean> ): RecyclerView.Adapter<VideoListAdapter.ItemVideoViewHolder>() {

    class ItemVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivCover:ImageView
        var ivHead: ImageView
        var tvIcon: TextView
        var tvDistance: TextView
        var tvContent: TextView

        init {
            ivCover = itemView.findViewById(R.id.iv_cover) as ImageView
            ivHead = itemView.findViewById(R.id.iv_head) as ImageView
            tvIcon = itemView.findViewById(R.id.icon_location) as TextView
            tvDistance = itemView.findViewById(R.id.tv_distance) as TextView
            tvContent = itemView.findViewById(R.id.tv_content) as TextView
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
        holder.tvIcon.typeface = Typeface.createFromAsset(mContext.assets, "iconfont.ttf")
        holder.tvIcon.setText(R.string.icon_location_fill)
        holder.tvDistance.setText(""+videoBean.distance + "km")
        holder.ivHead.setImageResource(videoBean.userBean.head)
        holder.tvContent.setText(videoBean.content)

    }

    override fun getItemCount(): Int {
        return mDatas.size
    }


}


