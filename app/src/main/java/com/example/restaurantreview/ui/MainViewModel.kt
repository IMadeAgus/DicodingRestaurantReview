package com.example.restaurantreview.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantreview.data.response.CustomerReviewsItem
import com.example.restaurantreview.data.response.PostReviewResponse
import com.example.restaurantreview.data.response.Restaurant
import com.example.restaurantreview.data.response.RestaurantResponse
import com.example.restaurantreview.data.retrofit.ApiConfig
import com.example.restaurantreview.ui.MainActivity.Companion
import com.example.restaurantreview.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {


    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview
    //nilah yang disebut dengan encapsulation pada LiveData, yaitu dengan membuat data yang bertipe MutableLiveData menjadi private (_listReview)
    // dan yang bertipe LiveData menjadi public (listReview). Cara ini disebut dengan backing property. Dengan begitu
    // Anda dapat mencegah variabel yang bertipe MutableLiveData diubah dari luar class. Karena memang seharusnya hanya ViewModel-lah yang dapat mengubah data.


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    //Terdapat LiveData dan MutableLiveData. Lalu apa bedanya? Keduanya sebenarnya mirip,
    // namun bedanya MutableLiveData bisa kita ubah value-nya, sedangkan LiveData bersifat read-only


    //Untuk menggunakan kelas event handler cukup mudah. Sesuai namanya, Anda hanya perlu membungkus (wrap) data yang ingin dijadikan single event.
    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText
    //Anda dapat membungkusnya dengan format Event<TipeData> seperti contoh di atas.

    companion object{
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        _isLoading.value = true

        //Request API
        //Setelah persiapan Retrofit sudah selesai, Anda dapat memanggil endpoint yang ada pada API Service seperti berikut:

        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object : Callback<RestaurantResponse> {
            //Di sini kita menggunakan fungsi enqueue untuk menjalankan request secara asynchronous di background.
            //Kemudian, hasilnya terdapat dua callback, yakni onResponse ketika ada respon, dan onFailure ketika gagal.

            override fun onResponse(call: Call<RestaurantResponse>, response: Response<RestaurantResponse>) {
                _isLoading.value = false

                if(response.isSuccessful) {
                    _restaurant.value = response.body()?.restaurant
                    _listReview.value = response.body()?.restaurant?.customerReviews

                }  else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            /* Seperti yang Anda lihat, di sini kita tidak perlu melakukan parsing lagi, karena data yang didapat sudah berupa POJO.
             Proses parsing dilakukan secara otomatis oleh Retrofit
            dengan menggunakan kode .addConverterFactory(GsonConverterFactory.create())
            di bagian ApiConfig dan anotasi SerializedName pada masing-masing POJO. */


            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
        fun postReview(review: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID,"Dicoding", review)

        client.enqueue(object  : Callback<PostReviewResponse> {
            override fun onResponse(call: Call<PostReviewResponse>, response: Response<PostReviewResponse>) {
              _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listReview.value = response.body()?.customerReviews

                    //Kemudian untuk memasukkan nilai ke dalam variabel tersebut, Anda harus menginisialisasi Event dengan
                    // constructor pesan yang ingin dijadikan sebagai content seperti ini untuk menyesuaikan tipenya.
                    _snackbarText.value = Event(response.body()?.message.toString())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}