package com.example.jetpackexercise.Data.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* 통신을 위한 BaseService 구현
*  Retrofit: Rest client library
*  okHttp: for http network */
class BaseService {
    fun getClient(baseUrl: String): Retrofit? = Retrofit.Builder()
        .baseUrl(baseUrl) .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build() }


