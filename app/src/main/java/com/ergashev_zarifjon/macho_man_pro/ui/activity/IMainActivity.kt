package com.ergashev_zarifjon.macho_man_pro.ui.activity

import android.content.Intent
import androidx.fragment.app.Fragment

interface IMainActivity {

    fun startActivitys(intet: Intent)

    fun replaceFragment(containerView: Int, fragment: Fragment)

    fun addFragment(containerView: Int, fragment: Fragment)

    fun replaceFragmentBackStack(containerView: Int, fragment: Fragment, tag: String? = null)

    fun addFragmentBackStack(containerView: Int, fragment: Fragment, tag: String? = null)

    fun showToast(message: String)

    fun back()

    fun choseFile()

    fun openCamera()

    fun onBackPressed()

    fun checkPermission(doneFunc: () -> Unit, fail: () -> Unit, vararg list: String)

    fun checkPermissionAndRequest(doneFunc: () -> Unit, fail: () -> Unit, vararg list: String)

    fun finish()

}