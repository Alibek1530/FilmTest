package uz.ali.filmtest.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MoviesPagerAdapter(
    private val listFragment: List<Fragment>,
    fr: Fragment
) : FragmentStateAdapter(fr) {
    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}