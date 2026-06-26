package com.Team.smartfishing.feature.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.Team.smartfishing.data.remote.HomeCreator
import com.Team.smartfishing.data.remote.HomeResponse
import com.Team.smartfishing.R
import com.Team.smartfishing.databinding.ActivityNowvmBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NowvmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNowvmBinding
    val ultrasonic = "ultrasonic"
    val loadcell = "loadcell"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nowvm)
        binding = ActivityNowvmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNetwork(ultrasonic)
        initNetwork(loadcell)
        waitGuest()
        clickEvent()
    }

    private val mDelayHandler: Handler by lazy {
        Handler()
    }

    private fun waitGuest() {
        mDelayHandler.postDelayed(::showGuest, 5000) // 10초 후에 showGuest 함수를 실행한다.
    }

    private fun showGuest() {
        // 실제 반복하는 코드를 여기에 적는다
        initNetwork(ultrasonic)
        initNetwork(loadcell)

        waitGuest() // 코드 실행뒤에 계속해서 반복하도록 작업한다.
    }

    private fun clickEvent() {
        binding.ivNowvmBtnback.setOnClickListener { finish() }
    }

    private fun initNetwork(container: String) {

        val call: Call<HomeResponse> = HomeCreator.homeService.getContainer(container)
        call.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    when (container) {
                        ultrasonic -> {
                            data?.let { GetData(it.con) }
                        }
                        else -> {
                            data?.let { GetDatatwo(it.con) }
                        }
                    }

                } else Log.d("else", "asd")
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                Log.e("debugTest", "error:(${t.message})")
            }
        })
    }


    private fun GetData(container: String) {
        InventoryData(container.toInt())
        SellingData(container.toInt())
        binding.tvNowvmNumofInventory.text = container

    }

    private fun GetDatatwo(container: String) {

        binding.tvNowvmNumofCoinbox.text = container
    }

    private fun InventoryData(can: Int) {

        if (can == 7 || can == 6 || can == 5) {
            binding.ivNomvmVm.setImageDrawable(
                resources.getDrawable(R.drawable.vending_blu, null)
            )
        } else if (can == 4) {
            binding.ivNomvmVm.setImageDrawable(
                resources.getDrawable(R.drawable.vending_pup, null)
            )
        } else if (can == 3 || can == 2 || can == 1 || can == 0) {
            binding.ivNomvmVm.setImageDrawable(
                resources.getDrawable(R.drawable.vending_red, null)
            )
        }
    }

    private fun SellingData(sellingcan: Int) {
        var can_default: Int = 7
        binding.tvNowvmNumofSelling.text = (can_default - sellingcan).toString()
    }
}