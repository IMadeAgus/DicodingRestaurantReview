package com.example.restaurantreview.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

import com.example.restaurantreview.data.response.CustomerReviewsItem
import com.example.restaurantreview.data.response.PostReviewResponse
import com.example.restaurantreview.data.response.Restaurant
import com.example.restaurantreview.data.response.RestaurantResponse
import com.example.restaurantreview.data.retrofit.ApiConfig
import com.example.restaurantreview.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Menggunakna lib ktx
    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        // Dihapus karena menggunakan kotlin ktx

        mainViewModel.restaurant.observe(this,{ restaurant ->
            setRestaurantData(restaurant)
        })

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)


        //Lalu bagaimana cara mendapatkan value dari LiveData yang ada pada kelas ViewModel. Perhatikan metode observe berikut:
        mainViewModel.listReview.observe(this) { consumerReviews ->
            setReviewData(consumerReviews)
        }
        //Perlu Anda ketahui bahwa customerReviews adalah custom variabel untuk hasil LiveData yang ada di ViewModel.
        // Jika Anda tidak menuliskan customerReviews-> maka variabel default-nya adalah it.
        //customerReviews akan selalu diperbarui secara realtime sesuai dengan perubahan yang ada di kelas ViewModel.
        // Contohnya yaitu saat Anda menambahkan data review baru,
        // alih alih memanggil fungsi findRestaurant lagi, Anda cukup mengobservasi LiveData tersebut untuk selalu mendapatkan data terbaru.
        // Lebih lanjut, proses ini dilakukan secara asynchronous sehingga tidak akan mengganggu interaksi UI Anda.



        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this) {
            //Lalu untuk mengambil data tersebut, cukup panggil fungsi getContentIfNotHandled seperti ini.
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        //Mengirim review
        binding.btnSend.setOnClickListener { view ->
            mainViewModel.postReview(binding.edReview.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setRestaurantData(restaurant: Restaurant) {
        binding.tvTitle.text = restaurant.name
        binding.tvDescription.text = restaurant.description
        Glide.with(this)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(binding.ivPicture)
    }

    private fun setReviewData(consumerReviews: List<CustomerReviewsItem>) {
        val adapter = ReviewAdapter()
        adapter.submitList(consumerReviews)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}