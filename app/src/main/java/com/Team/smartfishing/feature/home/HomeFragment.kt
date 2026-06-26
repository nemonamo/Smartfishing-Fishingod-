package com.Team.smartfishing.feature.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.Team.smartfishing.R
import com.Team.smartfishing.data.remote.HomeCreator
import com.Team.smartfishing.data.remote.HomeResponse
import com.Team.smartfishing.databinding.FragmentHomeBinding
import com.Team.smartfishing.util.vibrationflag
import com.Team.smartfishing.util.vmstate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("binding no")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        clickEvent()
        localDate()
        initNetwork("ultrasonic")
        initNetwork("vibration")
        waitGuest()

        return binding.root
    }


    private val mDelayHandler: Handler by lazy {
        Handler()
    }

    private fun waitGuest() {
        mDelayHandler.postDelayed(::showGuest, 5000) // 10초 후에 showGuest 함수를 실행한다.
    }

    private fun showGuest() {
        // 실제 반복하는 코드를 여기에 적는다
        initNetwork("ultrasonic")
        initNetwork("vibration")
        waitGuest() // 코드 실행뒤에 계속해서 반복하도록 작업한다.
    }

    private fun clickEvent() {
        with(binding) {
            clHomeBtnmanage.setOnClickListener {

                startActivity(
                    Intent(
                        context,
                        NowvmActivity::class.java
                    )
                )


            }
            clHomeBtnsecurity.setOnClickListener {
                startActivity(
                    Intent(
                        context,
                        SecurityActivity::class.java
                    )
                )
            }
            clHomeBtnclean.setOnClickListener {
                startActivity(
                    Intent(
                        context,
                        CleanActivity::class.java
                    )
                )
            }

        }
    }

    private fun localDate() {
        val currentTime: Long = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("MM월dd일", Locale("ko", "KR"))
        binding.tvMainLocaldate.text = dateFormat.format(currentTime)

    }

    private fun initNetwork(container: String) {

        val call: Call<HomeResponse> = HomeCreator.homeService.getContainer(container)
        call.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    when (container) {
                        "ultrasonic" -> {

                        }
                        else -> {

                        }
                    }
                } else Log.d("else", "asd")
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                Log.e("debugTest", "error:(${t.message})")
            }
        })
    }






}