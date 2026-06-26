package com.Team.smartfishing.feature.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.Team.smartfishing.data.remote.HomeCreator
import com.Team.smartfishing.data.remote.HomeResponse
import com.Team.smartfishing.util.vmstate
import com.Team.smartfishing.R
import com.Team.smartfishing.databinding.ActivityCleanBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CleanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCleanBinding


    var dataname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)
        binding = ActivityCleanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNetwork("dust")
        initNetwork("insideimg")
        waitGuest()

        binding.ivCleanBtnback.setOnClickListener { finish() }
        binding.button.setOnClickListener {

        }
    }


    private val mDelayHandler: Handler by lazy {
        Handler()
    }

    private fun waitGuest() {
        mDelayHandler.postDelayed(::showGuest, 5000) // 10초 후에 showGuest 함수를 실행한다.
    }

    private fun showGuest() {
        // 실제 반복하는 코드를 여기에 적는다
        initNetwork("dust")

        waitGuest() // 코드 실행뒤에 계속해서 반복하도록 작업한다.
    }

    private fun initNetwork(container: String) {

        val call: Call<HomeResponse> = HomeCreator.homeService.getContainer(container)
        call.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    when (container) {
                        "dust" -> {
                            data?.let { GetData(it.con) }
                        }
                        else -> {
                            dataname = data?.con.toString()
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


    private fun GetData(dust: String) {
        binding.tvCleanNumofdust.text = dust
        if (vmstate == 0)
            binding.ivCleanVm.setImageDrawable(resources.getDrawable(R.drawable.vending_blu, null))
        else if (vmstate == 1)
            binding.ivCleanVm.setImageDrawable(resources.getDrawable(R.drawable.vending_pup, null))
        else if (vmstate == 2)
            binding.ivCleanVm.setImageDrawable(resources.getDrawable(R.drawable.vending_red, null))
    }

    private fun GetDatatwo(base64: String) {

        val encodeByte = Base64.decode(dataname, Base64.NO_WRAP)
        val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        //서버에서 다 못받음
        binding.ivCleanImg.setImageBitmap(bitmap)

    }

}
