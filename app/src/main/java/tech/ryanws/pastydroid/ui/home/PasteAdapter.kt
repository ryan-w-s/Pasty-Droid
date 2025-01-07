package tech.ryanws.pastydroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tech.ryanws.pastydroid.databinding.ItemPasteBinding
import tech.ryanws.pastydroid.utils.Paste

class PasteAdapter : RecyclerView.Adapter<PasteAdapter.PasteViewHolder>() {
    private var pastes = listOf<Paste>()
    
    class PasteViewHolder(private val binding: ItemPasteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(paste: Paste) {
            binding.textPasteId.text = "Paste #${paste.id}"
            binding.textPasteContent.text = paste.content
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasteViewHolder {
        val binding = ItemPasteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PasteViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: PasteViewHolder, position: Int) {
        holder.bind(pastes[position])
        
        // Load more when reaching the end
        if (position == pastes.size - 1) {
            onLoadMore?.invoke()
        }
    }
    
    override fun getItemCount() = pastes.size
    
    var onLoadMore: (() -> Unit)? = null
    
    fun updatePastes(newPastes: List<Paste>) {
        pastes = newPastes
        notifyDataSetChanged()
    }
} 