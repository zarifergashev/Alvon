package com.ergashev_zarifjon.macho_man_pro.ui.fragment.self_picture

interface IPictureFragment {
    fun checkPermission(function: () -> Unit, fail: () -> Unit, vararg list: String)
    fun checkPermissionAndRequest(function: () -> Unit, fail: () -> Unit, vararg list: String)
    fun btnPermission(visiblity: Int)
    fun rvPicture(visible: Int)
    fun emptyImage(visible: Int)
}