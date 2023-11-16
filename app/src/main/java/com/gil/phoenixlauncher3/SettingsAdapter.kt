package com.gil.phoenixlauncher3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gil.phoenixlauncher3.databinding.ItemSettingsBinding

class SettingsAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onClick(adapterPosition: Int)
    }

    companion object{
        const val ITEM_SETTINGS = 0
        const val ITEM_NETWORK = 1
        const val ITEM_DISPLAY_SETTINGS= 2
        const val ITEM_INSTALLED_APP = 3
        private val settingsList = listOf(ITEM_SETTINGS, ITEM_NETWORK, ITEM_DISPLAY_SETTINGS, ITEM_INSTALLED_APP)
    }

    inner class ViewHolder(private val binding:ItemSettingsBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            itemView.isFocusable = true
            itemView.setOnFocusChangeListener { v, hasFocus ->
                val scale = if (hasFocus){
                    Pair(1.1F, 1.1F)
                }
                else{
                    Pair(1F, 1F)
                }
                v.scaleX = scale.first
                v.scaleY = scale.second
            }
        }
        fun bindItem(){
            binding.icon = when(adapterPosition){
                ITEM_SETTINGS ->{
                    ContextCompat.getDrawable(binding.settingsAppIcon.context, R.drawable.ic_settings)
                }
                ITEM_NETWORK ->{
                    ContextCompat.getDrawable(binding.settingsAppIcon.context, R.drawable.ic_network)
                }
                ITEM_DISPLAY_SETTINGS ->{
                    ContextCompat.getDrawable(binding.settingsAppIcon.context, R.drawable.ic_launcher_home)
                }
                ITEM_INSTALLED_APP ->{
                    ContextCompat.getDrawable(binding.settingsAppIcon.context, R.drawable.ic_apps)
                }
                else -> {
                    null
                }
            }
            binding.text =  when(adapterPosition){
                ITEM_SETTINGS ->{
                    itemView.context.resources.getString(R.string.settings)
                }
                ITEM_NETWORK ->{
                    itemView.context.resources.getString(R.string.network_settings)
                }
                ITEM_DISPLAY_SETTINGS ->{
                    itemView.context.resources.getString(R.string.launcher_settings)
                }
                ITEM_INSTALLED_APP ->{
                    itemView.context.resources.getString(R.string.installed_app)
                }
                else -> {
                    null
                }
            }

            itemView.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSettingsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, adapterPosition: Int) {
        holder.bindItem()
    }

    override fun getItemCount(): Int {
       return settingsList.size
    }
}