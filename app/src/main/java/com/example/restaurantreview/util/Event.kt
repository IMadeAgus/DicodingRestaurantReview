package com.example.restaurantreview.util

open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
//Seperti yang sudah Anda pahami di bahasa Kotlin, T adalah tipe generic yang bisa digunakan supaya kelas ini dapat membungkus berbagai macam data.
// Data yang dibungkus tersebut kemudian akan dimasukkan ke dalam variabel content.
//ah, fungsi utama dari kelas ini yaitu terdapat pada fungsi getContentIfNotHandled().
// Fungsi tersebut akan memeriksa apakah aksi ini pernah dieksekusi sebelumnya. Caranya yaitu dengan memanipulasi variabel hasBeenHandled.
