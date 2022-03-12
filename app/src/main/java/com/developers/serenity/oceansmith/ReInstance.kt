package com.developers.serenity.oceansmith

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReInstance {

    private val reInterface : ReInterface

    companion object {
        const val baseUrl = "https://www.fishwatch.gov/api/"
    }

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        reInterface = retrofit.create(ReInterface::class.java)

    }

    suspend fun getData() : List<DataClass>{
        return reInterface.getData()
    }
}