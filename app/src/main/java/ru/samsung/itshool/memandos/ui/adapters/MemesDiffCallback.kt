package ru.samsung.itshool.memandos.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.samsung.itshool.memandos.domain.Mem

class MemesDiffCallback(val  newListMemes : List<Mem>,
                        val  oldListMems : List<Mem>)
    : DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListMems[oldItemPosition].id == newListMemes[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldListMems.size
    }

    override fun getNewListSize(): Int {
        return newListMemes.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListMems[oldItemPosition].equals(newListMemes[newItemPosition])
    }


}