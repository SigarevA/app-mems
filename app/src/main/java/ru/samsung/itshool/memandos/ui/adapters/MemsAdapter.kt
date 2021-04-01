package ru.samsung.itshool.memandos.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import ru.samsung.itshool.memandos.databinding.RepresentationMemBinding
import ru.samsung.itshool.memandos.domain.Mem

private val TAG = "MemsAdapter"

class MemsAdapter(
    val listener: AdapterInteractionListener
) : ListAdapter<Mem, MemsAdapter.ViewHolder>(MemDiffCallback) {

    private val roundedCorners = RoundedCornersTransformation(
        64,
        0,
        RoundedCornersTransformation.CornerType.TOP
    )

    class ViewHolder(
        private val binding: RepresentationMemBinding,
        private val listener: AdapterInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mem: Mem) {
            binding.titleMem.text = mem.title
            binding.imgMem.setOnClickListener {
                listener.onItemClick(mem)
            }
            binding.share.setOnClickListener {
                listener.onItemShare(mem)
            }
            Glide.with(binding.root.context)
                .load(mem.photoUrl)
                .into(binding.imgMem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "create view holder")
        val binding =
            RepresentationMemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "binding view")
        holder.bind(getItem(position))
    }

    @FunctionalInterface
    interface AdapterInteractionListener {
        fun onItemClick(mem: Mem)
        fun onItemShare(mem : Mem)
    }

    object MemDiffCallback : DiffUtil.ItemCallback<Mem>() {
        override fun areItemsTheSame(oldItem: Mem, newItem: Mem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mem, newItem: Mem): Boolean {
            return oldItem == newItem
        }
    }
}