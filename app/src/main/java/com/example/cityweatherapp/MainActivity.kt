package com.example.cityweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.cityweatherapp.data.RetrofitClient
import com.example.cityweatherapp.data.WeatherServiceInterface
import com.example.cityweatherapp.model.WeatherResponse
import com.example.cityweatherapp.util.Utilities
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.ceil

class MainActivity : AppCompatActivity() {
    var weatherResponse : WeatherResponse? = null
    var city : String? = null
    lateinit var cityTextView : TextView
    lateinit var weatherTextView : TextView
    lateinit var tempTextView : TextView
    lateinit var weatherImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityTextView = cityNameText
        weatherImageView = weatherImage
        weatherTextView = weatherText
        tempTextView = tempText

        // Caching retrieved data
        //weatherTextView.text = weatherResponse?.weather?.get(0)?.main ?: ""
        //tempTextView.text = (weatherResponse?.main?.temp?.minus(273)).toString()
        //if(tempTextView.text == "null") tempTextView.text = ""


        //  Connecting with API
        val service = connectWithAPI()
        floatingActionButton.setOnClickListener{sendRequest(service, cityNameEditText.text.toString().trim())}

    }


    private fun connectWithAPI() : WeatherServiceInterface {
        val retrofit = RetrofitClient.getRetrofitClient()
        val service = retrofit.create(WeatherServiceInterface::class.java)
        return service
    }

    private fun sendRequest(service : WeatherServiceInterface, city: String) {
        service.getWeatherResponse(city, Utilities.apiKey).enqueue(
            object : Callback<WeatherResponse>{
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.d("failure", t.message)
                    Toast.makeText(this@MainActivity,
                        "Something went wrong!", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if(response.isSuccessful) {
                        this@MainActivity.weatherResponse = response.body()

                        weatherTextView.text = weatherResponse?.weather?.get(0)?.main
                        tempTextView.text = "${(weatherResponse?.main?.temp?.minus(273))?.toInt()} \u2103"
                        cityTextView.text = city.capitalize()
                        val icon =  weatherResponse?.weather?.get(0)?.icon
                        val imageUri = "https://openweathermap.org/img/wn/$icon@2x.png"
                        Picasso.get().load(imageUri).into(weatherImageView)
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
