package com.gil.phoenixlauncher3

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gil.phoenixlauncher3.databinding.ItemMainAppBinding

class MainAppAdapter(private var data: List<AppData>): RecyclerView.Adapter<MainAppAdapter.MainViewHolder>() {


    interface ItemClickListener{
        fun onItemClick(item: AppData?)
    }

    private var mItemClickListener: ItemClickListener? = null

    inner class MainViewHolder(private val imageBinding:ItemMainAppBinding)
        : RecyclerView.ViewHolder(imageBinding.root) {
        init {
            itemView.isFocusable = true
            itemView.setOnFocusChangeListener { v, hasFocus ->
                val scale = if (hasFocus){
                    Pair(1.2F, 1.2F)
                }
                else{
                    Pair(1F, 1F)
                }
                v.scaleX = scale.first
                v.scaleY = scale.second
            }
        }
            fun bindImage(appData: AppData?){
                itemView.setOnClickListener {
                    mItemClickListener?.onItemClick(appData)
                }
                imageBinding.appData = appData
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainAppBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = data[position]
        holder.bindImage(item)

    }

    override fun getItemCount(): Int {
        return data.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<AppData>){
        this.data = data
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: ItemClickListener) {
        this.mItemClickListener = listener
    }
}