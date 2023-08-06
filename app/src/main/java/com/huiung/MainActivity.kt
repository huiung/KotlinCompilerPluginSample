package com.huiung

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huiung.annotations.MyFunction
import com.huiung.KotlinCompilerPluginSample.R

class MainActivity : AppCompatActivity() {

    private val myInterface = object : MyInterface {

        @MyFunction(name = "myInterface onClick function")
        override fun onClick() {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
        myInterface.onClick()
    }

    @MyFunction(name = "test function")
    fun test() {
        println("함수의 시작")

        val a = 1
        val b = 2

        println("함수의 끝")
    }
}