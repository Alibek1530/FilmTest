package uz.ali.filmtest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.ali.filmtest.R
import uz.ali.filmtest.contents.Contents
import uz.ali.filmtest.models.CastX
import uz.ali.filmtest.ui.inf.InfoActorFragment

class VideosAdapter(var infoActorFragment: InfoActorFragment, var model: List<CastX>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video_actor_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.tvName.text = model[position].title

            val imageLink = Contents.BASE_IMAGE + model[position].poster_path
            holder.ivFilm.load(imageLink) {
                crossfade(true)
                crossfade(500)
            }
            holder.itemView.setOnClickListener {
                infoActorFragment.setPosIdViewos(model[position].id.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.itemNameVideo)
        var ivFilm: ImageView = itemView.findViewById(R.id.itemImageVideo)
    }
}