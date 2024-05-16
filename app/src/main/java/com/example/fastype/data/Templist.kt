package com.example.fastype.data
import com.example.fastype.data.typelist
object Templist {
    val listlist = mutableListOf<typelist>()

    fun gen(){
        // New Year
        listlist.add(typelist("przykład 1"))
        listlist.add(typelist("przykład 1","Czerwone jabłuszko, przekrojone na krzyż"))
    }
    init {
        gen()
    }
}