package uz.ali.filmtest.ui.inf

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import uz.ali.filmtest.adapters.AdapterImagesPager
import uz.ali.filmtest.adapters.VideosAdapter
import uz.ali.filmtest.contents.Contents.idKino
import uz.ali.filmtest.databinding.FragmentInfoActorBinding
import uz.ali.filmtest.models.*
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class InfoActorFragment : Fragment() {
    lateinit var viewModel: InfoActorViewModel
    lateinit var binding: FragmentInfoActorBinding

    var currentPage = 0
    lateinit var timer: Timer
    lateinit var modelImagesUser: ModelImagesUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoActorBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idUser = arguments?.getString("idUser").toString()

        viewModel = ViewModelProvider(this).get(InfoActorViewModel::class.java)
        viewModel.getActorFilm(idUser)
        viewModel.getUserImages(idUser)
        viewModel.getUserData(idUser)

        viewModel.getActorFilm.observe(this, {
            setRecycler(it)
        })
        viewModel.getUserImages.observe(this, {
            modelImagesUser = it
            setViewPager(it)
        })
        viewModel.getUserData.observe(this, {
            setData(it)
        })
        setTimer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClick()
    }

    fun setData(user: ModelUserData) {
        binding.tvTitleTool.text = user.name

        if (!user.name.isNullOrEmpty()){
            binding.name.text = user.name
            binding.name1.visibility=View.VISIBLE
        }
        if (!user.birthday.isNullOrEmpty()){
            binding.birthday.text = getDateFormat(user.birthday)
            binding.birthday1.visibility=View.VISIBLE
        }
        if (!user.place_of_birth.isNullOrEmpty()){
            binding.placeOfBirth.text = user.place_of_birth
            binding.placeOfBirth1.visibility=View.VISIBLE
        }
        if (!user.biography.isNullOrEmpty()){
            binding.biography.text = user.biography
            binding.biography1.visibility=View.VISIBLE
        }
    }


    fun getDateFormat(calendarDate: String?): String {
        if (calendarDate != null && calendarDate.toString().length > 0) {
            val spf = SimpleDateFormat("yyyy-MM-dd", Locale("EN"))
            val newDate: Date = spf.parse(calendarDate)
            val spf1 = SimpleDateFormat("dd MMMM yyyy", Locale("EN"))
            return spf1.format(newDate)
        }
        return ""
    }

    fun setViewPager(model: ModelImagesUser) {
        val adapter = AdapterImagesPager(model)

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
            if (currentPage == modelImagesUser?.profiles?.size) {
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

    fun setRecycler(model: ModelActorFilm) {
        binding.recyclerVideos.apply {
            adapter = VideosAdapter(this@InfoActorFragment, getList(model))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    fun getList(list: ModelActorFilm): List<CastX> {
        val modelList = arrayListOf<CastX>()
        list.cast.forEach {
            if (it.poster_path != null) {
                modelList.add(it)
            }
        }
        return modelList
    }

    fun setPosIdViewos(id: String) {
        idKino = id
        findNavController().popBackStack()
    }

    fun setOnClick() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }
}