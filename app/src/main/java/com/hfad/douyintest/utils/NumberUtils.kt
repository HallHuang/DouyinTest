package com.hfad.douyintest.utils

import java.text.DecimalFormat

object NumberUtils {

    fun numberFilter(number: Int): String {
        return if (number > 9999 && number <= 999999) {  //数字上万，小于百万，保留一位小数点
            val df2 = DecimalFormat("##.#")
            val format = df2.format((number.toDouble() / 10000))
            format + "万"
        } else if (number > 999999 && number < 99999999) {  //百万到千万不保留小数点
            (number / 10000).toString() + "万"
        } else if (number > 99999999) { //上亿
            val df2 = DecimalFormat("##.#")
            val format = df2.format(number.toDouble() / 100000000)
            format + "亿"
        } else {
            number.toString() + ""
        }
    }

}