package com.example.cityweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cityweatherapp.data.RetrofitClient
import com.example.cityweatherapp.data.WeatherServiceInterface
import com.example.cityweatherapp.model.WeatherResponse
import com.example.cityweatherapp.util.Utilities
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var weatherResponse : WeatherResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  Connecting with API
        connectWithAPI()
    }

    private fun connectWithAPI() {
        val retrofit = RetrofitClient.getRetrofitClient()
        val service = retrofit.create(WeatherServiceInterface::class.java)
        service.getWeatherResponse("London", Utilities.apiKey).enqueue(
                object : Callback<WeatherResponse>{
                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        Log.d("failure", t.message)
                        Toast.makeText(this@MainActivity,
                                "Something went wrong!", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                       if(response.isSuccessful) {
                           weatherResponse = response.body()
                           cityNameText.text = weatherResponse?.weather?.get(0)?.main ?: ""
                       }
                        else {
                           Log.d("failure", response.code().toString())
                           Toast.makeText(this@MainActivity,
                                   "The city entered does not exist!", Toast.LENGTH_LONG).show()
                       }

                    }

                }
        )
    }
}
