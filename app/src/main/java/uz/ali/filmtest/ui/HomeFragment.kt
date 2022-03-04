package uz.ali.filmtest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import uz.ali.filmtest.R
import uz.ali.filmtest.adapters.MoviesPagerAdapter
import uz.ali.filmtest.databinding.FragmentHomeBinding
import uz.ali.filmtest.ui.homelist.FilmsListFragment

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var popularFragment: FilmsListFragment = FilmsListFragment(this, 1)
    private var topRatedFragment: FilmsListFragment = FilmsListFragment(this, 2)
    private var upcomingFragment: FilmsListFragment = FilmsListFragment(this, 3)

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPagerFilms.adapter = MoviesPagerAdapter(
            arrayListOf(popularFragment, topRatedFragment, upcomingFragment),
            this
        )
        val listTab = arrayListOf("Popular", "Top Rated", "Upcoming")
        TabLayoutMediator(binding.tabLayoutNames, binding.viewPagerFilms) { tab, position ->
            tab.text = listTab[position]
        }.attach()
    }

    fun setPosId(filmId: String) {
        val bundle = Bundle()
        bundle.putString("id", filmId)
        findNavController().navigate(R.id.action_homeFragment_to_detalsFilmFragment, bundle)
    }
}