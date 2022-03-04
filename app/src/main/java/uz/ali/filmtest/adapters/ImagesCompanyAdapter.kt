package uz.ali.filmtest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.ali.filmtest.R
import uz.ali.filmtest.contents.Contents
import uz.ali.filmtest.models.ProductionCompany


class ImagesCompanyAdapter(var list: List<ProductionCompany>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_kompany, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val imageLink = Contents.BASE_IMAGE + list[position].logo_path
            holder.company.load(imageLink) {
                crossfade(true)
                crossfade(500)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(iteView: View) : RecyclerView.ViewHolder(iteView) {
        var company: ImageView = iteView.findViewById(R.id.comImages)
    }
}