package com.ergashev_zarifjon.macho_man_pro.model

import android.content.Context
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.model.EditTypeList.Companion.BODY_BODY
import ja.burhanrashid52.photoeditor.PhotoFilter
import kotlin.random.Random

class EditTypeList {

    companion object {
        val BODY: String = "BODY"
        val MACHO: String = "MACHO"
        val WEAR: String = "WEAR"
        val ACCESSIOR: String = "ACCESSIOR"
        val BLUR: String = "BLUR"
        val FILTERS: String = "FILTERS "
        val SHARE: String = "SHARE"

        val BODY_PACKS: String = "BODY_PACKS"
        val BODY_BODY: String = "BODY_BODY"
        val BODY_CHEST: String = "BODY_CHEST"
        val BODY_ARMS: String = "BODY_ARMS"

        val MACHO_HAIR: String = "MACHO_HAIR"
        val MACHO_BEARD: String = "MACHO_BEARD"
        val MACHO_MUSTACHE: String = "MACHO_MUSTACHE"
        val MACHO_EYEBROWS: String = "MACHO_EYEBROWS"
        val MACHO_EYE: String = "MACHO_EYE"

        val WEAR_PARTY = "WEAR_PARTY"
        val WEAR_WEDDING = "WEAR_WEDDING"
        val WEAR_TRADITIONAL = "WEAR_TRADITIONAL"
        val WEAR_WESTMEN = "WEAR_WESTMEN"

        val ACCESSIOR_TATTOO = "ACCESSIOR_TATTOO"
        val ACCESSIOR_GLASS = "ACCESSIOR_GLASS"
        val ACCESSIOR_TIE = "ACCESSIOR_TIE"
        val ACCESSIOR_CAP = "ACCESSIOR_CAP"
        val ACCESSIOR_SCARF = "ACCESSIOR_SCARF"


        val BLUR_1 = "BLUR_1"
        val BLUR_2 = "BLUR_2"
        val BLUR_3 = "BLUR_3"
        val BLUR_4 = "BLUR_4"
        val BLUR_5 = "BLUR_5"
        val BLUR_6 = "BLUR_6"
        val BLUR_7 = "BLUR_7"
        val BLUR_8 = "BLUR_8"
        val BLUR_9 = "BLUR_9"
        val BLUR_10 = "BLUR_10"


        private val BG = listOf(
            R.drawable.ei_bg_1, R.drawable.ei_bg_2, R.drawable.ei_bg_3, R.drawable.ei_bg_4,
            R.drawable.ei_bg_5, R.drawable.ei_bg_6, R.drawable.ei_bg_7, R.drawable.ei_bg_8
        )

        private val TYPES: Map<String, Pair<Int, Int>> = mapOf(
            Pair(second = Pair(R.string.body, R.drawable.img_body), first = BODY),
            Pair(second = Pair(R.string.macho, R.drawable.img_macho), first = MACHO),
            Pair(second = Pair(R.string.wear, R.drawable.img_wear), first = WEAR),
            Pair(second = Pair(R.string.accessories, R.drawable.ic_accecuare), first = ACCESSIOR),
            Pair(second = Pair(R.string.blur, R.drawable.ic_blur_2), first = BLUR),
            Pair(second = Pair(R.string.filters, R.drawable.ic_face_filter), first = FILTERS),
            Pair(second = Pair(R.string.share, R.drawable.img_share), first = SHARE)
        )

        private val FILTER_BLUR_MEMU_ITEMS: Map<String, Pair<Int, Int>> = mapOf(
            Pair(second = Pair(-1, -1), first = BLUR_1),
            Pair(second = Pair(-1, -1), first = BLUR_2),
            Pair(second = Pair(-1, -1), first = BLUR_3),
            Pair(second = Pair(-1, -1), first = BLUR_4),
            // Pair(second = Pair(-1-1), first = BLUR_5),
            Pair(second = Pair(-1, -1), first = BLUR_6),
            Pair(second = Pair(-1, -1), first = BLUR_7),
            Pair(second = Pair(-1, -1), first = BLUR_8),
            Pair(second = Pair(-1, -1), first = BLUR_9),
            Pair(second = Pair(-1, -1), first = BLUR_10)
        )

        val FILTER_PHOTO_EDITOR_ITEMS: Map<String, Pair<String, PhotoFilter>> = mapOf(

            Pair(
                first = PhotoFilter.NONE.name,
                second = Pair("filters/original.jpg", PhotoFilter.NONE)
            ),
            Pair(
                first = PhotoFilter.AUTO_FIX.name,
                second = Pair("filters/auto_fix.png", PhotoFilter.AUTO_FIX)
            ),
            Pair(
                first = PhotoFilter.BRIGHTNESS.name,
                second = Pair("filters/brightness.png", PhotoFilter.BRIGHTNESS)
            ),
            Pair(
                first = PhotoFilter.CONTRAST.name,
                second = Pair("filters/contrast.png", PhotoFilter.CONTRAST)
            ),
            Pair(
                first = PhotoFilter.DOCUMENTARY.name,
                second = Pair("filters/documentary.png", PhotoFilter.DOCUMENTARY)
            ),
            Pair(
                first = PhotoFilter.DUE_TONE.name,
                second = Pair("filters/dual_tone.png", PhotoFilter.DUE_TONE)
            ),
            Pair(
                first = PhotoFilter.FILL_LIGHT.name,
                second = Pair("filters/fill_light.png", PhotoFilter.FILL_LIGHT)
            ),
            Pair(
                first = PhotoFilter.FISH_EYE.name,
                second = Pair("filters/fish_eye.png", PhotoFilter.FISH_EYE)
            ),
            Pair(
                first = PhotoFilter.GRAIN.name,
                second = Pair("filters/grain.png", PhotoFilter.GRAIN)
            ),
            Pair(
                first = PhotoFilter.GRAY_SCALE.name,
                second = Pair("filters/gray_scale.png", PhotoFilter.GRAY_SCALE)
            ),
            Pair(
                first = PhotoFilter.LOMISH.name,
                second = Pair("filters/lomish.png", PhotoFilter.LOMISH)
            ),
            Pair(
                first = PhotoFilter.NEGATIVE.name,
                second = Pair("filters/negative.png", PhotoFilter.NEGATIVE)
            ),
            Pair(
                first = PhotoFilter.POSTERIZE.name,
                second = Pair("filters/posterize.png", PhotoFilter.POSTERIZE)
            ),
            Pair(
                first = PhotoFilter.SATURATE.name,
                second = Pair("filters/saturate.png", PhotoFilter.SATURATE)
            ),
            Pair(
                first = PhotoFilter.SEPIA.name,
                second = Pair("filters/sepia.png", PhotoFilter.SEPIA)
            ),
            Pair(
                first = PhotoFilter.SHARPEN.name,
                second = Pair("filters/sharpen.png", PhotoFilter.SHARPEN)
            ),
            Pair(
                first = PhotoFilter.TEMPERATURE.name,
                second = Pair("filters/temprature.png", PhotoFilter.TEMPERATURE)
            ),
            Pair(
                first = PhotoFilter.TINT.name,
                second = Pair("filters/tint.png", PhotoFilter.TINT)
            ),
            Pair(
                first = PhotoFilter.VIGNETTE.name,
                second = Pair("filters/vignette.png", PhotoFilter.VIGNETTE)
            ),
            Pair(
                first = PhotoFilter.CROSS_PROCESS.name,
                second = Pair("filters/cross_process.png", PhotoFilter.CROSS_PROCESS)
            ),
            Pair(
                first = PhotoFilter.BLACK_WHITE.name,
                second = Pair("filters/b_n_w.png", PhotoFilter.BLACK_WHITE)
            ),
            Pair(
                first = PhotoFilter.FLIP_HORIZONTAL.name,
                second = Pair("filters/flip_horizental.png", PhotoFilter.FLIP_HORIZONTAL)
            ),
            Pair(
                first = PhotoFilter.FLIP_VERTICAL.name,
                second = Pair("filters/flip_vertical.png", PhotoFilter.FLIP_VERTICAL)
            ),
            Pair(
                first = PhotoFilter.ROTATE.name,
                second = Pair("filters/rotate.png", PhotoFilter.ROTATE)
            )
        )

        private val BODY_MEMU: Map<String, Pair<Int, Int>> = mapOf(
            Pair(second = Pair(R.string.packs, R.drawable.icon_packs), first = BODY_PACKS),
            Pair(second = Pair(R.string.body, R.drawable.icon_body), first = BODY_BODY),
            Pair(second = Pair(R.string.chest, R.drawable.icon_chest), first = BODY_CHEST),
            Pair(second = Pair(R.string.arms, R.drawable.icon_arms), first = BODY_ARMS)
        )
        private val MACHO_MENU: Map<String, Pair<Int, Int>> = mapOf(
            Pair(second = Pair(R.string.hair, R.drawable.icon_hair), first = MACHO_HAIR),
            Pair(second = Pair(R.string.beard, R.drawable.icon_beard), first = MACHO_BEARD),
            Pair(
                second = Pair(R.string.mustache, R.drawable.icon_mustache),
                first = MACHO_MUSTACHE
            ),
            Pair(second = Pair(R.string.eyebrows, R.drawable.eyebrow), first = MACHO_EYEBROWS),
            Pair(second = Pair(R.string.eye, R.drawable.icon_eye), first = MACHO_EYE)
        )
        private val WEAR_MENU: Map<String, Pair<Int, Int>> = mapOf(
            Pair(second = Pair(R.string.party, R.drawable.icon_suits_party), first = WEAR_PARTY),
            Pair(
                second = Pair(R.string.wedding, R.drawable.icon_suits_wedding),
                first = WEAR_WEDDING
            ),
            Pair(
                second = Pair(R.string.tarditional, R.drawable.icon_suits_traditional),
                first = WEAR_TRADITIONAL
            ),
            Pair(
                second = Pair(R.string.western, R.drawable.icon_suits_western),
                first = WEAR_WESTMEN
            )
        )
        private val ACCESSIOR_MENU: Map<String, Pair<Int, Int>> = mapOf(
            Pair(second = Pair(R.string.tattoo, R.drawable.icon_tattoo), first = ACCESSIOR_TATTOO),
            Pair(second = Pair(R.string.glass, R.drawable.icon_sunglass), first = ACCESSIOR_GLASS),
            Pair(second = Pair(R.string.tie, R.drawable.icon_tie), first = ACCESSIOR_TIE),
            Pair(second = Pair(R.string.cap, R.drawable.icon_cap), first = ACCESSIOR_CAP),
            Pair(second = Pair(R.string.scarf, R.drawable.icon_scarf), first = ACCESSIOR_SCARF)
        )

        private val randomAccess by lazy {
            Random(TYPES.size)
        }

        private val BODY_PACKS_MEMU: List<Int> = mutableListOf(
            R.drawable.image_04abs_01,
            R.drawable.image_04abs_02,
            R.drawable.image_04abs_03,
            R.drawable.image_04abs_04,
            R.drawable.image_04abs_05,
            R.drawable.image_04abs_06,
            R.drawable.image_04abs_07,
            R.drawable.image_04abs_08,
            R.drawable.image_06abs_01,
            R.drawable.image_06abs_02,
            R.drawable.image_06abs_03,
            R.drawable.image_06abs_04,
            R.drawable.image_06abs_05,
            R.drawable.image_06abs_06,
            R.drawable.image_06abs_07,
            R.drawable.image_06abs_08,
            R.drawable.image_08abs_01,
            R.drawable.image_08abs_02,
            R.drawable.image_08abs_03,
            R.drawable.image_08abs_04,
            R.drawable.image_08abs_05,
            R.drawable.image_08abs_06,
            R.drawable.image_08abs_07,
            R.drawable.image_08abs_08,
            R.drawable.image_10abs_01,
            R.drawable.image_10abs_02,
            R.drawable.image_10abs_03,
            R.drawable.image_10abs_04,
            R.drawable.image_10abs_05,
            R.drawable.image_10abs_06,
            R.drawable.image_10abs_07,
            R.drawable.image_10abs_08,
            R.drawable.image_10abs_09,
            R.drawable.image_10abs_10,
            R.drawable.image_10abs_11,
            R.drawable.image_10abs_12
        )
        private val BODY_BODY_MEMU: List<Int> = mutableListOf(
            R.drawable.image_body_dark_01,
            R.drawable.image_body_dark_02,
            R.drawable.image_body_dark_03,
            R.drawable.image_body_dark_04,
            R.drawable.image_body_dark_05,
            R.drawable.image_body_dark_06,
            R.drawable.image_body_dark_07,
            R.drawable.image_body_dark_08,
            R.drawable.image_body_thin_01,
            R.drawable.image_body_thin_02,
            R.drawable.image_body_thin_03,
            R.drawable.image_body_thin_04,
            R.drawable.image_body_thin_05,
            R.drawable.image_body_thin_06,
            R.drawable.image_body_thin_07,
            R.drawable.image_body_manly_01,
            R.drawable.image_body_manly_01,
            R.drawable.image_body_manly_02,
            R.drawable.image_body_manly_03,
            R.drawable.image_body_manly_04,
            R.drawable.image_body_manly_05,
            R.drawable.image_body_manly_06,
            R.drawable.image_body_manly_07,
            R.drawable.image_body_manly_08,
            R.drawable.image_body_manly_09,
            R.drawable.image_body_white_01,
            R.drawable.image_body_white_02,
            R.drawable.image_body_white_03,
            R.drawable.image_body_white_04,
            R.drawable.image_body_white_05,
            R.drawable.image_body_white_06,
            R.drawable.image_body_white_07,
            R.drawable.image_body_white_08,
            R.drawable.image_body_wrestler_01,
            R.drawable.image_body_wrestler_02,
            R.drawable.image_body_wrestler_03,
            R.drawable.image_body_wrestler_04,
            R.drawable.image_body_wrestler_05,
            R.drawable.image_body_wrestler_06,
            R.drawable.image_body_wrestler_07,
            R.drawable.image_body_wrestler_08,
            R.drawable.image_body_wrestler_09
        )
        private val BODY_CHEST_MEMU: List<Int> = mutableListOf(
            R.drawable.image_chest_01,
            R.drawable.image_chest_02,
            R.drawable.image_chest_03,
            R.drawable.image_chest_04,
            R.drawable.image_chest_05,
            R.drawable.image_chest_06,
            R.drawable.image_chest_07,
            R.drawable.image_chest_08,
            R.drawable.image_chest_09,
            R.drawable.image_chest_010,
            R.drawable.image_chest_011,
            R.drawable.image_chest_012,
            R.drawable.image_chest_013,
            R.drawable.image_chest_014,
            R.drawable.image_chest_015,
            R.drawable.image_chest_016,
            R.drawable.image_chest_017,
            R.drawable.image_chest_018
        )
        private val BODY_ARMS_MEMU: List<Int> = mutableListOf(
            R.drawable.image_arms_01,
            R.drawable.image_arms_02,
            R.drawable.image_arms_03,
            R.drawable.image_arms_04,
            R.drawable.image_arms_05,
            R.drawable.image_arms_06,
            R.drawable.image_arms_07,
            R.drawable.image_arms_08,
            R.drawable.image_arms_09,
            R.drawable.image_arms_010,
            R.drawable.image_arms_011,
            R.drawable.image_arms_012,
            R.drawable.image_arms_013,
            R.drawable.image_arms_014,
            R.drawable.image_arms_015,
            R.drawable.image_arms_016,
            R.drawable.image_arms_017,
            R.drawable.image_arms_018
        )

        private val MACHO_HAIR_MEMU: List<Int> = mutableListOf(
            R.drawable.image_hair_curls_01,
            R.drawable.image_hair_curls_02,
            R.drawable.image_hair_curls_03,
            R.drawable.image_hair_curls_04,
            R.drawable.image_hair_curls_05,
            R.drawable.image_hair_designer_01,
            R.drawable.image_hair_designer_02,
            R.drawable.image_hair_designer_03,
            R.drawable.image_hair_designer_04,
            R.drawable.image_hair_designer_05,
            R.drawable.image_hair_party_01,
            R.drawable.image_hair_party_02,
            R.drawable.image_hair_party_03,
            R.drawable.image_hair_party_04,
            R.drawable.image_hair_party_05,
            R.drawable.image_hair_party_06,
            R.drawable.image_hair_spiky_01,
            R.drawable.image_hair_spiky_02,
            R.drawable.image_hair_spiky_03,
            R.drawable.image_hair_spiky_04,
            R.drawable.image_hair_spiky_05,
            R.drawable.image_hair_spiky_06
        )
        private val MACHO_BEAR_MENU: List<Int> = mutableListOf(
            R.drawable.image_beard_designer_01,
            R.drawable.image_beard_designer_02,
            R.drawable.image_beard_designer_03,
            R.drawable.image_beard_designer_04,
            R.drawable.image_beard_designer_05,
            R.drawable.image_beard_party_01,
            R.drawable.image_beard_party_02,
            R.drawable.image_beard_party_03,
            R.drawable.image_beard_party_04,
            R.drawable.image_beard_party_05,
            R.drawable.image_beard_party_06,
            R.drawable.image_beard_party_07,
            R.drawable.image_beard_party_08,
            R.drawable.image_beard_traditional_01,
            R.drawable.image_beard_traditional_02,
            R.drawable.image_beard_traditional_03,
            R.drawable.image_beard_traditional_04,
            R.drawable.image_beard_traditional_05,
            R.drawable.image_beard_trendy_01,
            R.drawable.image_beard_trendy_02,
            R.drawable.image_beard_trendy_03,
            R.drawable.image_beard_trendy_04,
            R.drawable.image_beard_trendy_05,
            R.drawable.image_beard_trendy_06
        )
        private val MACHO_MUSTACHE_MENU: List<Int> = mutableListOf(
            R.drawable.image_mustache_chevron_01,
            R.drawable.image_mustache_chevron_02,
            R.drawable.image_mustache_chevron_03,
            R.drawable.image_mustache_chevron_04,
            R.drawable.image_mustache_chevron_05,
            R.drawable.image_mustache_chevron_06,
            R.drawable.image_mustache_deigner_01,
            R.drawable.image_mustache_deigner_02,
            R.drawable.image_mustache_deigner_03,
            R.drawable.image_mustache_deigner_04,
            R.drawable.image_mustache_deigner_05,
            R.drawable.image_mustache_deigner_06,
            R.drawable.image_mustache_deigner_07,
            R.drawable.image_mustache_deigner_08,
            R.drawable.image_mustache_deigner_09,
            R.drawable.image_mustache_horse_01,
            R.drawable.image_mustache_horse_02,
            R.drawable.image_mustache_horse_03,
            R.drawable.image_mustache_horse_04,
            R.drawable.image_mustache_horse_05,
            R.drawable.image_mustache_horse_06,
            R.drawable.image_mustache_royal_01,
            R.drawable.image_mustache_royal_02,
            R.drawable.image_mustache_royal_03,
            R.drawable.image_mustache_royal_04,
            R.drawable.image_mustache_royal_05,
            R.drawable.image_mustache_royal_06,
            R.drawable.image_mustache_traditional_01,
            R.drawable.image_mustache_traditional_02,
            R.drawable.image_mustache_traditional_03,
            R.drawable.image_mustache_traditional_04
        )
        private val MACHO_EYEBROWS_MENU: List<Int> = mutableListOf(
            R.drawable.eyebrow,
            R.drawable.eyebrow1,
            R.drawable.eyebrow2,
            R.drawable.eyebrow3,
            R.drawable.eyebrow4,
            R.drawable.eyebrow5,
            R.drawable.eyebrow6,
            R.drawable.eyebrow7,
            R.drawable.eyebrow8,
            R.drawable.eyebrow9,
            R.drawable.eyebrow10,
            R.drawable.eyebrow11,
            R.drawable.eyebrow12,
            R.drawable.eyebrow13,
            R.drawable.eyebrow14
        )
        private val MACHO_EYE_MENU: List<Int> = mutableListOf(
            R.drawable.lens01,
            R.drawable.lens02,
            R.drawable.lens03,
            R.drawable.lens04,
            R.drawable.lens05,
            R.drawable.lens06,
            R.drawable.lens07,
            R.drawable.lens08,
            R.drawable.lens09,
            R.drawable.lens10,
            R.drawable.lens11
        )

        private val WEAR_PARTY_MENU: List<Int> = mutableListOf(
            R.drawable.img_men_suit_party01,
            R.drawable.img_men_suit_party02,
            R.drawable.img_men_suit_party03,
            R.drawable.img_men_suit_party04,
            R.drawable.img_men_suit_party05,
            R.drawable.img_men_suit_party06
        )
        private val WEAR_WEDDING_MENU: List<Int> = mutableListOf(
            R.drawable.img_men_suit_wedding01,
            R.drawable.img_men_suit_wedding02,
            R.drawable.img_men_suit_wedding03,
            R.drawable.img_men_suit_wedding04,
            R.drawable.img_men_suit_wedding05,
            R.drawable.img_men_suit_wedding06,
            R.drawable.img_men_suit_wedding07,
            R.drawable.img_men_suit_wedding08
        )
        private val WEAR_TRADITIONAL_MENU: List<Int> = mutableListOf(
            R.drawable.img_men_suit_traditional01,
            R.drawable.img_men_suit_traditional02,
            R.drawable.img_men_suit_traditional03,
            R.drawable.img_men_suit_traditional04,
            R.drawable.img_men_suit_traditional05,
            R.drawable.img_men_suit_traditional06,
            R.drawable.img_men_suit_traditional07
        )
        private val WEAR_WESTMEN_MENU: List<Int> = mutableListOf(
            R.drawable.img_men_suit_western01,
            R.drawable.img_men_suit_western02,
            R.drawable.img_men_suit_western03,
            R.drawable.img_men_suit_western04,
            R.drawable.img_men_suit_western05,
            R.drawable.img_men_suit_western06,
            R.drawable.img_men_suit_western07,
            R.drawable.img_men_suit_western08
        )

        private val ACCESSIOR_TATTOO_MENU: List<Int> = mutableListOf(
            R.drawable.image_tatto_01,
            R.drawable.image_tatto_02,
            R.drawable.image_tatto_03,
            R.drawable.image_tatto_04,
            R.drawable.image_tatto_05,
            R.drawable.image_tatto_06,
            R.drawable.image_tatto_07,
            R.drawable.image_tatto_08,
            R.drawable.image_tatto_09,
            R.drawable.image_tatto_010,
            R.drawable.image_tatto_011,
            R.drawable.image_tatto_012,
            R.drawable.image_tatto_013,
            R.drawable.image_tatto_014,
            R.drawable.image_tatto_015,
            R.drawable.image_tatto_016,
            R.drawable.image_tatto_017,
            R.drawable.image_tatto_018,
            R.drawable.image_tatto_019,
            R.drawable.image_tatto_020,
            R.drawable.image_tatto_021,
            R.drawable.image_tatto_022,
            R.drawable.image_tatto_023,
            R.drawable.image_tatto_024,
            R.drawable.image_tatto_024,
            R.drawable.image_tatto_025,
            R.drawable.image_tatto_026,
            R.drawable.image_tatto_027,
            R.drawable.image_tatto_028,
            R.drawable.image_tatto_029,
            R.drawable.image_tatto_030,
            R.drawable.image_tatto_031,
            R.drawable.image_tatto_032,
            R.drawable.image_tatto_033,
            R.drawable.image_tatto_034,
            R.drawable.image_tatto_035,
            R.drawable.image_tatto_036,
            R.drawable.image_tatto_037,
            R.drawable.image_tatto_038,
            R.drawable.image_tatto_039,
            R.drawable.image_tatto_040
        )
        private val ACCESSIOR_GLASS_MENU: List<Int> = mutableListOf(
            R.drawable.image_sun_glass_01,
            R.drawable.image_sun_glass_02,
            R.drawable.image_sun_glass_03,
            R.drawable.image_sun_glass_04,
            R.drawable.image_sun_glass_05,
            R.drawable.image_sun_glass_06,
            R.drawable.image_sun_glass_07,
            R.drawable.image_sun_glass_08,
            R.drawable.image_sun_glass_09,
            R.drawable.image_sun_glass_010,
            R.drawable.image_sun_glass_011,
            R.drawable.image_sun_glass_012,
            R.drawable.image_sun_glass_013,
            R.drawable.image_sun_glass_014,
            R.drawable.image_sun_glass_015,
            R.drawable.image_sun_glass_016,
            R.drawable.image_sun_glass_017,
            R.drawable.image_sun_glass_018,
            R.drawable.image_sun_glass_019

        )
        private val ACCESSIOR_TIE_MENU: List<Int> = mutableListOf(
            R.drawable.image_tie_01,
            R.drawable.image_tie_02,
            R.drawable.image_tie_03,
            R.drawable.image_tie_04,
            R.drawable.image_tie_05,
            R.drawable.image_tie_06,
            R.drawable.image_tie_07,
            R.drawable.image_tie_08,
            R.drawable.image_tie_09,
            R.drawable.image_tie_010,
            R.drawable.image_tie_011,
            R.drawable.image_tie_012,
            R.drawable.image_tie_013,
            R.drawable.image_tie_014,
            R.drawable.image_tie_015,
            R.drawable.image_tie_016,
            R.drawable.image_tie_017,
            R.drawable.image_tie_018,
            R.drawable.image_tie_019,
            R.drawable.image_tie_020,
            R.drawable.image_tie_021
        )
        private val ACCESSIOR_CAP_MENU: List<Int> = mutableListOf(
            R.drawable.image_cap_01,
            R.drawable.image_cap_02,
            R.drawable.image_cap_03,
            R.drawable.image_cap_04,
            R.drawable.image_cap_05,
            R.drawable.image_cap_06,
            R.drawable.image_cap_07,
            R.drawable.image_cap_08,
            R.drawable.image_cap_09,
            R.drawable.image_cap_010,
            R.drawable.image_cap_011,
            R.drawable.image_cap_012,
            R.drawable.image_cap_013,
            R.drawable.image_cap_014,
            R.drawable.image_cap_015,
            R.drawable.image_cap_016,
            R.drawable.image_cap_017,
            R.drawable.image_cap_018,
            R.drawable.image_cap_019,
            R.drawable.image_cap_020,
            R.drawable.image_cap_021,
            R.drawable.image_cap_022,
            R.drawable.image_cap_023,
            R.drawable.image_cap_024
        )
        private val ACCESSIOR_SCARF_MENU: List<Int> = mutableListOf(
            R.drawable.image_scarf_01,
            R.drawable.image_scarf_02,
            R.drawable.image_scarf_03,
            R.drawable.image_scarf_04,
            R.drawable.image_scarf_05,
            R.drawable.image_scarf_06,
            R.drawable.image_scarf_07,
            R.drawable.image_scarf_08,
            R.drawable.image_scarf_09,
            R.drawable.image_scarf_010,
            R.drawable.image_scarf_011,
            R.drawable.image_scarf_013,
            R.drawable.image_scarf_014

        )
        private var editTypeLIst: MutableList<EditType>? = null

        fun getEditTypeList(context: Context): MutableList<EditType> {
            if (editTypeLIst == null) {
                var fistBg = randomAccess.nextInt(TYPES.size - 1)
                val result = mutableListOf<EditType>()
                TYPES.keys.forEach { typeKey ->
                    if (fistBg >= BG.size)
                        fistBg = 0
                    TYPES[typeKey]?.let { typeName ->
                        result.add(
                            EditType(
                                type_code = typeKey,
                                type_name = context.resources.getString(typeName.first),
                                type_img = typeName.second,
                                background = BG[fistBg]
                            )
                        )
                        fistBg++
                    }

                    editTypeLIst = result
                }

            }
            return editTypeLIst!!
        }

        private var subTaskList: MutableList<EditType>? = null

        fun getSubEditTypeList(parentType: String, context: Context): MutableList<EditType> {
            var background = false
            val type = when (parentType) {
                BODY -> BODY_MEMU
                WEAR -> WEAR_MENU
                MACHO -> MACHO_MENU
                ACCESSIOR -> ACCESSIOR_MENU
                BLUR -> {
                    background = true
                    FILTER_BLUR_MEMU_ITEMS
                }

                else -> emptyMap()
            }
            val result = mutableListOf<EditType>()
            if (!background) {
                type.keys.forEach { typeKey ->
                    type[typeKey]?.let { typeName ->
                        result.add(
                            EditType(
                                type_code = typeKey,
                                type_name = if (typeName.first != -1) context.resources.getString(
                                    typeName.first
                                ) else "",
                                type_img = typeName.second,
                                background = -1
                            )
                        )
                    }
                }
            } else {
                if (subTaskList == null) {
                    var fistBg = randomAccess.nextInt(type.size - 1)
                    type.keys.forEach { typeKey ->
                        if (fistBg >= BG.size)
                            fistBg = 0
                        type[typeKey]?.let { typeName ->
                            result.add(
                                EditType(
                                    type_code = typeKey,
                                    type_name = if (typeName.first != -1) context.resources.getString(
                                        typeName.first
                                    ) else "",
                                    type_img = typeName.second,
                                    background = BG[fistBg]
                                )
                            )
                            fistBg++
                        }
                    }
                    subTaskList = result
                } else {
                    subTaskList?.toList()?.let { result.addAll(it) }
                }
            }
            return result
        }

        fun getSubEditImageTypeList(parentType: String, context: Context): MutableList<EditType> {

            val type = when (parentType) {

                BODY_ARMS -> BODY_ARMS_MEMU
                BODY_CHEST -> BODY_CHEST_MEMU
                BODY_BODY -> BODY_BODY_MEMU
                BODY_PACKS -> BODY_PACKS_MEMU

                MACHO_HAIR -> MACHO_HAIR_MEMU
                MACHO_BEARD -> MACHO_BEAR_MENU
                MACHO_MUSTACHE -> MACHO_MUSTACHE_MENU
                MACHO_EYEBROWS -> MACHO_EYEBROWS_MENU
                MACHO_EYE -> MACHO_EYE_MENU

                WEAR_PARTY -> WEAR_PARTY_MENU
                WEAR_WEDDING -> WEAR_WEDDING_MENU
                WEAR_TRADITIONAL -> WEAR_TRADITIONAL_MENU
                WEAR_WESTMEN -> WEAR_WESTMEN_MENU

                ACCESSIOR_TATTOO -> ACCESSIOR_TATTOO_MENU
                ACCESSIOR_GLASS -> ACCESSIOR_GLASS_MENU
                ACCESSIOR_TIE -> ACCESSIOR_TIE_MENU
                ACCESSIOR_CAP -> ACCESSIOR_CAP_MENU
                ACCESSIOR_SCARF -> ACCESSIOR_SCARF_MENU
                else -> emptyList()
            }
            val result = mutableListOf<EditType>()
            type.forEachIndexed { index, i ->
                result.add(
                    EditType(
                        type_code = index.toString(),
                        type_name = "",
                        type_img = i,
                        background = -1
                    )
                )
            }
            return result
        }

    }
}