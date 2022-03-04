package uz.ali.filmtest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import coil.load
import uz.ali.filmtest.R
import uz.ali.filmtest.contents.Contents
import uz.ali.filmtest.models.ModelImagesUser

class AdapterImagesPager(var model: ModelImagesUser) :
    PagerAdapter() {
    lateinit var vieww: View
    lateinit var image: ImageView

    override fun getCount(): Int {
        return model.profiles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        vieww = LayoutInflater.from(container.context).inflate(R.layout.item_p_image, null)
        image = vieww.findViewById(R.id.itemImage)

        setImageOpen(position)

        container.addView(vieww)
        return vieww
    }

    fun setImageOpen(position: Int) {
        val imageLink = Contents.BASE_IMAGE + model.profiles[position].file_path
        image.load(imageLink) {
            crossfade(true)
            crossfade(500)
        }

        vieww.setOnClickListener {

        }
    }
}
