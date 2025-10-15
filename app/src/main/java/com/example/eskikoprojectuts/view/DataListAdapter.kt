package com.example.eskikoprojectuts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eskikoprojectuts.databinding.DataListItemBinding
import com.example.eskikoprojectuts.model.Anak

class DataListAdapter (val dataList: ArrayList<Anak>)
    :RecyclerView.Adapter<DataListAdapter.DataViewHolder>()
{
    class DataViewHolder(var binding: DataListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = DataListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount() = dataList.size


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.binding.txtUsia.text = dataList[position].usia.toString()
        holder.binding.txtHeight.text = dataList[position].height.toString()
        holder.binding.txtWeight.text = dataList[position].weight.toString()


    }

    fun updateDataList(newDataList: ArrayList<Anak>) {
        dataList.clear()
        dataList.addAll(newDataList)
        notifyDataSetChanged()
    }
}