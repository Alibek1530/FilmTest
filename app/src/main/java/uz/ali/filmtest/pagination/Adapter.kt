package uz.ali.filmtest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import uz.ali.filmtest.contents.Contents
import uz.ali.filmtest.databinding.ItemFilmsLayoutBinding
import uz.ali.filmtest.ui.homelist.FilmsListFragment
import uz.ali.filmtest.models.ResultModel

class Adapter(val filmsListFragment: FilmsListFragment) : PagingDataAdapter<ResultModel,
        Adapter.ImageViewHolder>(diffCallback) {

    inner class ImageViewHolder(
        val binding: ItemFilmsLayoutBinding
    ) : ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ResultModel>() {
            override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemFilmsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val model = getItem(position)

        holder.binding.apply {
            holder.itemView.apply {
                val imageLink = Contents.BASE_IMAGE+model?.poster_path
                itemImage.load(imageLink) {
                    crossfade(true)
                    crossfade(600)
                }
                itemName.text=model?.original_title
            }

            holder.itemView.setOnClickListener {
                filmsListFragment.setPosClick(model?.id.toString())
            }
        }
    }
}