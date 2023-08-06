package com.huiung.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class MyFunction(
    val name: String = "MyFunction"
)
