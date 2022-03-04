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
import uz.ali.filmtest.models.Cast
import uz.ali.filmtest.ui.detal.DetalsFilmFragment

class ImageActorAdapter(var detalsFilmFragment: DetalsFilmFragment, var model: List<Cast>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_actor_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.tvName.text = model[position].name
            holder.tvName.text = model[position].name

            val imageLink = Contents.BASE_IMAGE + model[position].profile_path
            holder.ivFilm.load(imageLink) {
                crossfade(true)
                crossfade(500)
            }

            holder.itemView.setOnClickListener {
                detalsFilmFragment.setPosId(model[position].id.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.itemNameActor)
        var ivFilm: ImageView = itemView.findViewById(R.id.itemImageActor)
    }
}