package com.example.restaurantreview.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Pada latihan ini, Anda membuat sebuah class yang berfungsi untuk membuat dan mengkonfigurasi Retrofit dengan nama ApiConfig
class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://restaurant-api.dicoding.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return  retrofit.create(ApiService::class.java)
        }
    }
}
/*Kelas ini akan membuat kode Anda menjadi lebih efektif karena Anda tidak perlu membuat konfigurasi
Retrofit baru setiap kali membutuhkannya, tetapi cukup memanggil fungsi yang ada di dalam class ini saja.

Saat menggunakan logging interceptor untuk aplikasi yang sudah dipublikasikan, pastikan kembali pesan log hanya akan tampil pada mode debug.

val loggingInterceptor = if(BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}
*/

