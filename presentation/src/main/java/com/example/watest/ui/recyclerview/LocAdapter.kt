package com.example.watest.ui.recyclerview

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Landmark

class LocAdapter(
    private val clickLambda: (Int) -> Unit,
    private val likeLambda: (Int, ImageView) -> Unit,
    private var list: List<Landmark>
) : RecyclerView.Adapter<ItemHolder>() {

    private val allItems = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder.create(parent.context, parent, clickLambda, likeLambda)

    override fun onBindViewHolder(holder: ItemHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    fun filter(isChecked: Boolean) {
        var filteredList = mutableListOf<Landmark>()
        if(isChecked) {
            list.forEach { if (it.isFavorite) filteredList.add(it) }
        }else{
            filteredList.addAll(allItems)
        }
        list = filteredList
        notifyDataSetChanged()
    }

}