package com.developers.serenity.oceansmith

class MoreClass {

    private var title: String? = null
    private var img: String? = "na"

    fun getTitles(): String {
        return title.toString()
    }

    fun setTitles(title: String) {
        this.title = title
    }

    fun getImgs(): String {
        return img.toString()
    }

    fun setImgs(img: String) {
        this.img = img
    }

}