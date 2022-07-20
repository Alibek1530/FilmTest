package uz.ali.filmtest.ui.detal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.youtube.player.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uz.ali.filmtest.contents.Contents.idKino
import uz.ali.filmtest.databinding.FragmentDetalsFilmBinding
import java.text.SimpleDateFormat
import java.util.*
import uz.ali.filmtest.R
import uz.ali.filmtest.YoutubeActivity
import uz.ali.filmtest.adapters.AdapteVideosPager
import uz.ali.filmtest.adapters.GenresNamesAdapter
import uz.ali.filmtest.adapters.ImageActorAdapter
import uz.ali.filmtest.adapters.ImagesCompanyAdapter
import uz.ali.filmtest.models.*
import java.time.Duration
import java.time.LocalTime


@AndroidEntryPoint
class DetalsFilmFragment : Fragment() {
    lateinit var binding: FragmentDetalsFilmBinding
    lateinit var viewModel: DetalsViewModel
    var currentPage = 0
    lateinit var timer: Timer
    lateinit var modelVideo: ModelVideo
    var size = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetalsFilmBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reflesh.isRefreshing = true
        setReflash()

        if (idKino != "0") {
            viewModel.getVideos(idKino)
            viewModel.getInfoFilm(idKino)
            viewModel.getInfoActor(idKino)
            viewModel.getVideosImages(idKino)
            currentPage = 0
            binding.reflesh.isRefreshing = true
        }


        viewModel.getInfoFilm.observe(this, {
            setPageData(it)
        })
        viewModel.getInfoActor.observe(this, {
            setRecycler(it)
            binding.reflesh.isRefreshing = false
        })
        viewModel.getVideos.observe(this, {
            Log.d("123454321", "it.results.size: ${it.results.size}")
            modelVideo = it
        })
        viewModel.getVideosImages.observe(this, {
            Log.d("123454321", "it.backdrops.size: ${it.backdrops.size}")
            // setViewPager(getListImagesVideos(it))
            size = it.backdrops.size
            setViewPager(it.backdrops)
        })
        setOnClick()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetalsViewModel::class.java)

        val idFilm = arguments?.getString("id").toString()

        viewModel.getVideos(idFilm)
        viewModel.getInfoFilm(idFilm)
        viewModel.getInfoActor(idFilm)
        viewModel.getVideosImages(idFilm)

        setTimer()
    }

    fun sef(){
        val l=LinkedList<String>()
        l.add("dsds")
        l.add("dsds")

    }

    fun setViewPager(model: List<Backdrop>) {
        val adapter = AdapteVideosPager(this, model)

        binding.VPager.adapter = adapter
        binding.tabLayout.setViewPager(binding.VPager)

        binding.VPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun setTimer() {
        val handler = Handler()
        val update = Runnable {
            if (currentPage == size) {
                currentPage = 0
            }
            binding.VPager.setCurrentItem(currentPage++, true)
        }
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 2500L, 1500L)

    }

    fun setRecycler(model: ModelInfoActor) {
        binding.recyclerImages.apply {
            adapter = ImageActorAdapter(this@DetalsFilmFragment, getList(model))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    fun getList(list: ModelInfoActor): List<Cast> {
        val modelList = arrayListOf<Cast>()
        list.cast.forEach {
            if (!it.profile_path.isNullOrEmpty()) {
                modelList.add(it)
            }
        }
        return modelList
    }

    fun setPageData(model: ModelDetals) {
        binding.tvTitleTool.text = model.original_title
        binding.filmName.text = model.original_title
        binding.filmDes.text = model.overview
        val ava = (model.vote_average * 10).toInt()
        binding.progress.progress = ava
        binding.filAva.text = "${ava}%"
        binding.filmRelease.text = getDateFormat(model.release_date)
        binding.tvTime.text = getTimeFormat(model.runtime)

        binding.recyclerGenres.apply {
            adapter = GenresNamesAdapter(model.genres)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        binding.recyclerCompany.apply {
            adapter = ImagesCompanyAdapter(getDataImage(model.production_companies))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        binding.isVis1.visibility = View.VISIBLE
        binding.isVis2.visibility = View.VISIBLE

    }

    fun getTimeFormat(time: Int): String {
        val d = Duration.ofMinutes(time.toLong())
        val hackUseOfClockAsDuration: LocalTime = LocalTime.MIN.plus(d)
        val output: String = hackUseOfClockAsDuration.toString()
        return output
    }

    fun getDataImage(list: List<ProductionCompany>): List<ProductionCompany> {
        val a = arrayListOf<ProductionCompany>()
        list.forEach {
            if (it.logo_path != null) {
                a.add(it)
            }
        }
        return a
    }

    fun getDateFormat(calendarDate: String): String {
        val spf = SimpleDateFormat("yyyy-MM-dd", Locale("EN"))
        val newDate: Date = spf.parse(calendarDate)
        val spf1 = SimpleDateFormat("dd MMMM yyyy", Locale("EN"))
        return spf1.format(newDate)
    }

    fun setReflash() {
        val handler = Handler()
        binding.reflesh.setColorSchemeResources(
            R.color.purple_500
        )

        binding.reflesh.setOnRefreshListener {
            val runnable = Runnable {
                binding.reflesh.isRefreshing = false
            }
            handler.postDelayed(
                runnable, 2000.toLong()
            )
        }
    }


    fun setPosId(userId: String) {
        val bundle = Bundle()
        bundle.putString("idUser", userId)
        findNavController().navigate(
            R.id.action_detalsFilmFragment_to_infoActorFragment,
            bundle
        )
    }

    fun setPosIdImages(pos: Int) {
        val l = modelVideo.results.size
        if (l > pos) {
            val i = Intent(context, YoutubeActivity::class.java)
            i.putExtra("idVideo", modelVideo.results[pos].key)
            startActivity(i)
        }else{
            Toast.makeText(context, "Not Video", Toast.LENGTH_SHORT).show()
        }
    }

    fun setOnClick() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onStop() {
        super.onStop()
        idKino = "0"
    }
}