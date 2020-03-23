package com.ergashev_zarifjon.macho_man_pro.commons


sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading :Result<Nothing>()

}


val  Result<*>.successed
    get() = this is Result.Success && data!=null
