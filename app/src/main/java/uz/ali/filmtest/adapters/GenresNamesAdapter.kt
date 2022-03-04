package uz.ali.filmtest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.ali.filmtest.R
import uz.ali.filmtest.models.Genre


class GenresNamesAdapter(var list: List<Genre>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_genres, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.tvName.text = list[position].name
        }
    }

    override fun getItemCount(): Int {
      return list.size
    }

    inner class MyViewHolder(iteView: View) : RecyclerView.ViewHolder(iteView) {
        var tvName: TextView = iteView.findViewById(R.id.titleName)
    }
}