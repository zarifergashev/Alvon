package com.ergashev_zarifjon.macho_man_pro.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.AboutFragment
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.main_fragment.MainFragment
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.self_picture.PictureFragment

class MainActivityFragemntPagerAdapter(fragmentManager: FragmentManager):FragmentStatePagerAdapter(fragmentManager){
    override fun getItem(position: Int): Fragment {
        return when(position){
0->MainFragment.newInstance()
            1->PictureFragment.newInstance()
            2->AboutFragment.newInstance()
            else -> MainFragment.newInstance()
        }
    }

    override fun getCount(): Int = 3


}
