package com.example.luzkan.collegecalc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.toast
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newton.now.sh")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateDisplay("")
    }

    private val operationList: MutableList<String> = arrayListOf()
    private val numberCache: MutableList<String> = arrayListOf()

    private fun makeString(list: List<String>, joiner: String = ""): String {
        if (list.isEmpty()) return ""
        return list.reduce { r, s -> r + joiner + s }
    }

    private fun clearCache() {
        numberCache.clear()
        operationList.clear()
    }

    fun deleteClick(view: View) {
        val cut = makeString(numberCache).dropLast(1)
        numberCache.clear()
        numberCache.add(cut)
        updateDisplay(cut)
    }

    private fun updateDisplay(mainDisplayString: String) {
        val fullCalculationString = makeString(operationList, " ")
        val fullCalculationTextView = findViewById<TextView>(R.id.fullCalculationText)
        fullCalculationTextView.text = fullCalculationString
        val mainTextView = findViewById<TextView>(R.id.textView)
        mainTextView.text = mainDisplayString
    }

    fun resetClick(view: View) {
        clearCache()
        updateDisplay("")
    }

    fun negateNumber(view: View) {
        if (numberCache.isNotEmpty()) {
            if (numberCache.first().equals("-")) {
                numberCache.removeAt(0)
            } else numberCache.add(0, "-")
        } else numberCache.add("-")

        val numberString = makeString(numberCache)
        updateDisplay(numberString)
    }

    fun numberClick(view: View) {
        val button = view as Button
        val numberString = button.text

        numberCache.add(numberString.toString())
        val text = makeString(numberCache)
        updateDisplay(text)
    }


    // Functions below are for the results of the input
    private fun readInput(): String {
        // Add the last input before calculating
        operationList.add(makeString(numberCache))

        // Convert to String and clean afterwards
        val inputToCalculate = makeString(operationList)
        numberCache.clear()

        return inputToCalculate
    }


    private fun requestNewton(callnewton: Call<NewtonValue>){
        callnewton.enqueue( object : Callback<NewtonValue> {
            override fun onFailure(call: Call<NewtonValue>, t: Throwable) {
                toast("Couldn't connect to the server.")
            }

            override fun onResponse(call: Call<NewtonValue>, response: Response<NewtonValue>) {
                val body = response.body()
                updateDisplay("= " + body!!.result)
            }
        })
    }

    fun equalsClick(view: View) {
        val newton = retrofit.create(NewtonAPI::class.java)
        val callnewton = newton.simplifyThis(readInput())
        requestNewton(callnewton)
    }

    fun factorClick(view: View) {
        val newton = retrofit.create(NewtonAPI::class.java)
        val callnewton = newton.factorThis(readInput())
        requestNewton(callnewton)
    }

    fun deriveClick(view: View) {
        val newton = retrofit.create(NewtonAPI::class.java)
        val callnewton = newton.deriveThis(readInput())
        requestNewton(callnewton)
    }

    fun integrateClick(view: View) {
        val newton = retrofit.create(NewtonAPI::class.java)
        val callnewton = newton.integrateThis(readInput())
        requestNewton(callnewton)
    }

    fun zeroesClick(view: View) {
        val newton = retrofit.create(NewtonAPI::class.java)
        val callnewton = newton.zeroesThis(readInput())
        requestNewton(callnewton)
    }

    fun findtangentClick(view: View) {
        val newton = retrofit.create(NewtonAPI::class.java)
        val callnewton = newton.tangentThis(readInput())
        requestNewton(callnewton)
    }

    fun areaClick(view: View) {
        val newton = retrofit.create(NewtonAPI::class.java)
        val callnewton = newton.areaThis(readInput())
        requestNewton(callnewton)
    }

    fun logClick(view: View) {
        val newton = retrofit.create(NewtonAPI::class.java)
        val callnewton = newton.logThis(readInput())
        requestNewton(callnewton)
    }
}