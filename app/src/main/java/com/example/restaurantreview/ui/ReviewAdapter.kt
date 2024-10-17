package com.example.restaurantreview.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantreview.data.response.CustomerReviewsItem
import com.example.restaurantreview.databinding.ItemReviewBinding

//Setelah mendapatkan data dari API, langkah selanjutnya yaitu menampilkan data pada RecyclerView seperti biasanya.
// Perbedaannya, di sini Anda menggunakan ListAdapter yang merupakan ekstensi dari RecyclerView.Adapter.

//Jika dibandingkan dengan RecyclerView.Adapter biasa, perbedaannya adalah Anda tidak perlu meng-override fungsi getItemCount() lagi.
// Hal ini karena list data sudah diketahui melalui type parameter pertama pada ListAdapter.



class ReviewAdapter : ListAdapter<CustomerReviewsItem, ReviewAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: CustomerReviewsItem){
            binding.tvItem.text = "${review.review}\n- ${review.name}"
        }
    }

    companion object {
        //Data tersebut didapatkan melalui fungsi submitList() yang ada pada MainActivity.
        // Dengan begitu, Anda juga tidak perlu mengirim data secara manual menggunakan parameter maupun setter lagi, cukup menggunakan submitList saja.

        //Hal tersebut bisa terimplementasikan karena adanya DiffUtil yang berguna untuk memeriksa apakah suatu data masih sama atau tidak.

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CustomerReviewsItem>() {
            override fun areItemsTheSame(
                oldItem: CustomerReviewsItem,
                newItem: CustomerReviewsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CustomerReviewsItem,
                newItem: CustomerReviewsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    //areItemsTheSame() digunakan untuk memeriksa id atau key yang unik. Biasanya digunakan untuk mengetahui
    // apakah ada perubahan posisi dan penambahan/pengurangan data. Sedangkan areContentsTheSame()
    // digunakan untuk memeriksa apakah konten dari dua item sama atau tidak. Fungsi ini digunakan
    // untuk mengetahui ada pembaruan konten pada suatu item.
        
    }
}