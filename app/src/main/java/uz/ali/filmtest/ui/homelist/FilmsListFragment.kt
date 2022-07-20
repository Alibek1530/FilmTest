package uz.ali.filmtest.ui.homelist

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.ali.filmtest.Adapter
import uz.ali.filmtest.R
import uz.ali.filmtest.databinding.FragmentPopularBinding
import uz.ali.filmtest.ui.HomeFragment

@AndroidEntryPoint
class FilmsListFragment(val homeFragment: HomeFragment, var type: Int) : Fragment() {

    private lateinit var adapterr: Adapter
    private lateinit var viewModel: HomeViewModel

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
 //   private val viewModel:HomeViewModel by viewModels() // endi shuni ishlataman


    lateinit var binding: FragmentPopularBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java) // shuni urniga

        adapterr = Adapter(this)

        setReflash()

        binding.popularRec.apply {
            adapter = adapterr
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        if (type == 1) {
            lifecycleScope.launch {
                viewModel.listPopular.collectLatest {
                    adapterr.submitData(it)
                    Log.d("dsads", "load: 444")
                }
            }
        }
        if (type == 2) {
            lifecycleScope.launch {
                viewModel.listTopRated.collectLatest {
                    adapterr.submitData(it)
                    Log.d("dsads", "load: 4441")
                }
            }
        }
        if (type == 3) {
            lifecycleScope.launch {
                viewModel.listUpcoming.collectLatest {
                    adapterr.submitData(it)
                    Log.d("dsads", "load: 44422")
                }
            }
        }
    }

    fun setReflash() {
        handler = Handler()

        adapterr.addLoadStateListener {
            binding.reflesh.isRefreshing = true
        }
        adapterr.addOnPagesUpdatedListener {
            binding.reflesh.isRefreshing = false
        }

        binding.reflesh.setColorSchemeResources(
            R.color.purple_500
        )

        binding.reflesh.setOnRefreshListener {
            runnable = Runnable {
                binding.reflesh.isRefreshing = false
            }
            handler.postDelayed(
                runnable, 3000.toLong()
            )
        }
    }

    fun setPosClick(idFilm: String) {
        homeFragment.setPosId(idFilm)
    }

    override fun onStop() {
        super.onStop()
        binding.reflesh.isRefreshing = false
    }
}