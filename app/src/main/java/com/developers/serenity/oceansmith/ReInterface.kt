package com.developers.serenity.oceansmith

import retrofit2.http.GET

interface ReInterface {

    @GET("species")
    suspend fun getData() : List<DataClass>
}