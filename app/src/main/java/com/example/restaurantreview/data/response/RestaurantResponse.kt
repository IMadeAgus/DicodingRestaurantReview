package com.example.restaurantreview.data.response

import com.google.gson.annotations.SerializedName

//Retrofit Model
//Dengan menggunakan RoboPojoGenerator, Anda telah membuat class model yang berfungsi untuk menampung data dari JSON.

data class RestaurantResponse(

	// Kemudian untuk menandai sebuah variabel terhubung dengan data JSON, gunakan annotation @SerializedName seperti berikut:
	@field:SerializedName("restaurant")
	val restaurant: Restaurant? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Restaurant(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,

	@field:SerializedName("pictureId")
	val pictureId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class CustomerReviewsItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("review")
	val review: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class PostReviewResponse(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
