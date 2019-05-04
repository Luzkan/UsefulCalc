package com.example.luzkan.collegecalc

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewtonAPI {
    @GET("/simplify/{equation}")
    fun simplifyThis(@Path("equation") equation : String) : Call<NewtonValue>
    @GET("/factor/{equation}")
    fun factorThis(@Path("equation") equation : String) : Call<NewtonValue>
    @GET("/derive/{equation}")
    fun deriveThis(@Path("equation") equation : String) : Call<NewtonValue>
    @GET("/integrate/{equation}")
    fun integrateThis(@Path("equation") equation : String) : Call<NewtonValue>
    @GET("/zeroes/{equation}")
    fun zeroesThis(@Path("equation") equation : String) : Call<NewtonValue>
    @GET("/tangent/{equation}")
    fun tangentThis(@Path("equation") equation : String) : Call<NewtonValue>
    @GET("/area/{equation}")
    fun areaThis(@Path("equation") equation : String) : Call<NewtonValue>
    @GET("/log/{equation}")
    fun logThis(@Path("equation") equation : String) : Call<NewtonValue>
}