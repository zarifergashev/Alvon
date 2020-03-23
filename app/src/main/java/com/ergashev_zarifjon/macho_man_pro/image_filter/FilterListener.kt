package com.ergashev_zarifjon.macho_man_pro.image_filter

import ja.burhanrashid52.photoeditor.PhotoFilter


interface FilterListener {
    fun onFilterSelected(photoFilter: PhotoFilter)
}