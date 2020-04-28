package ru.samsung.itshool.memandos.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.domain.Mem

class MemsAdapter(
    val mems: Array<Mem>,
    val listener: AdapterInteractionListener
) : RecyclerView.Adapter<MemsAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val MemTitle: TextView
        val imgMem: ImageView

        init {
            MemTitle = v.findViewById(R.id.title_mem)
            imgMem = v.findViewById(R.id.img_mem)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "binding view")
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.representation_mem, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "binding view")
        holder.MemTitle.text = mems[position].title
        Glide.with(holder.itemView)
            .load(mems[position].photoUrl)
            .into(holder.imgMem)
        holder.imgMem.setOnClickListener {
            listener.onItemClick(mems[position])
        }
    }


    @FunctionalInterface
    interface AdapterInteractionListener {
        fun onItemClick(mem: Mem)
    }

    override fun getItemCount() = mems.size

    companion object {
        private val TAG = "MemsAdapter"
    }
}