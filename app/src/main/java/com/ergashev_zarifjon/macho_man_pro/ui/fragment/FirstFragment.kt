package com.ergashev_zarifjon.macho_man_pro.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.adapter.MainActivityFragemntPagerAdapter
import com.ergashev_zarifjon.macho_man_pro.ui.activity.IMainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.first_fragment.*
import kotlinx.android.synthetic.main.first_fragment.view.*
import kotlinx.android.synthetic.main.first_fragment.view.vp_fragment_container

class FirstFragment:Fragment(){

    private var listener: IMainActivity? = null

    private val  adapter by lazy {
        fragmentManager?.let { MainActivityFragemntPagerAdapter(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =inflater.inflate(R.layout.first_fragment,container,false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainActivity) {
            listener = context
        } else {
            throw RuntimeException("$context must implement com.example.alvon.ui.activity.IMainActivity")
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.main_fragment -> {
                vp_fragment_container.setCurrentItem(0,true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.my_picture_fragment -> {
                vp_fragment_container.setCurrentItem(1,true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.about_fragment -> {
vp_fragment_container.setCurrentItem(2,true)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private var  prevMenuItem:MenuItem?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.vp_fragment_container.adapter=adapter
        view.vp_fragment_container.currentItem = 0

        view.bn_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
view.vp_fragment_container.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
    override fun onPageScrollStateChanged(state: Int) {


    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if(prevMenuItem!=null){
            prevMenuItem!!.isChecked = false
        }else{
            bn_nav_view.menu.getItem(0).isChecked = false
        }


        bn_nav_view.menu.getItem(position).isChecked = true
        prevMenuItem=bn_nav_view.menu.getItem(position)
    }
})
    }

fun onBackPressed(){
    if (vp_fragment_container.currentItem==0){
        listener?.finish()
    }else{
        bn_nav_view.selectedItemId=R.id.main_fragment
    }

}

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}