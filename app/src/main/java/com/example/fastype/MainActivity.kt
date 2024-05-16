package com.example.fastype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fastype.ui.theme.FastypeTheme
import kotlin.time.*

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.net.Uri
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.ui.text.input.KeyboardType


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
//import androidx.navigation.NavController

import com.example.fastype.data.Templist
import com.example.fastype.data.typelist

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FastypeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    Navi()
                }
            }
        }
    }
}
//Realm
//Firebase sprawdz czy darmowe
@Composable
fun Navi(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainE"){
        composable("mainE"){
            main(navController = navController)
        }
        composable("statE"){
            statscreen(navController = navController)
        }
        composable("listE"){
            listscreen(navController = navController)
        }
        composable("TypeE") {
            var plchlis: typelist = typelist("Placeholder")
            typescreen(navController = navController, curlis = plchlis)
        }
    }
}
@Composable
fun main(modifier: Modifier = Modifier, navController: NavController){
    LazyColumn{
        item { Title("FASTYPE")}
        item {Button1("Teksty",onClick = {
            navController.navigate("listE")
        })}
        item {Button1("Opcje",onClick = {})}
        item {Button1("Statystyki",onClick = {
            navController.navigate("statE")
        })}
    }
}
@Composable
fun statscreen(modifier: Modifier = Modifier.padding(5.dp), navController: NavController){
    LazyColumn{
        item { Title("Statystyki")}
        item { statshow()}
        item {Button2("RESET",onClick = {})}
        item {Button2("Menu",onClick = {
            navController.navigate("mainE")
        })}
    }
}
@Composable
fun listscreen(modifier: Modifier = Modifier.padding(5.dp), navController: NavController){
    val lisoflis = Templist.listlist
    LazyColumn{
        item { Title("Listy")}
        item { listshow(navController = navController, lis = lisoflis[0])}
        item { listshow(navController = navController, lis = lisoflis[1])}
        item {Button2("Menu",onClick = {
            navController.navigate("mainE")
        })}
    }
}
@Composable
fun typescreen(modifier: Modifier = Modifier, navController: NavController, curlis: typelist){
    LazyColumn{
        item { Title(curlis.nazwa)}
        item { Typer(navController = navController,nexttext = curlis.zawartosc)}
    }
}
@OptIn(ExperimentalTime::class)
@Composable
fun Typer(modifier: Modifier = Modifier, navController: NavController, nexttext: String) {
    // BasicTextField with mutable state to store the value
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val aState = remember { mutableStateOf(0) }
    val errcount = remember { mutableStateOf(0) }
    val ntxt = remember { mutableStateOf(nexttext) }
    var convs : String = ""
    //val timeSource = TimeSource.Monotonic
    //val mark = timeSource.markNow()
    //var mareal = mark.elapsedNow()
    val currentDateTime: java.util.Date = java.util.Date()
    var currentTimestamp: Long = currentDateTime.time
    Column(modifier = modifier) {
        Text(text = "Postęp: ${aState.value}",
            fontSize = 26.sp)
        Text(text = "Błędy: ${errcount.value}",
            fontSize = 26.sp,
            color = Color.Red
        )
        Text(text = "Czas: ${currentTimestamp}")
        BasicTextField(
            value = textState.value,
            onValueChange = {
                //currentTimestamp = currentDateTime.time
                //mareal = mark.elapsedNow()
                //textState.value = it
                convs = it.text.takeLast(1)//textState.value.text.toString().take(1)
                //textState.value = TextFieldValue(convs)
                if(convs==nexttext.takeLast(nexttext.length-aState.value).take(1)) {
                    aState.value++
                    if(aState.value < nexttext.length){
                        ntxt.value = nexttext.takeLast(nexttext.length-aState.value)
                    }
                    else{
                        navController.navigate("listE")
                    }
                }
                else{
                    errcount.value++
                }
            }
        )
        Text(text = ntxt.value)
    }
}
/*fun Typer(modifier: Modifier = Modifier, nexttext: String) {
    Text(text = "tekst")
}*/
@Composable
fun Title(tekst : String,modifier: Modifier = Modifier){
    Card(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = tekst,fontSize = 40.sp,modifier = modifier
            .fillMaxSize()
            .padding(30.dp)
            , textAlign = TextAlign.Center,)
    }
}
@Composable
fun statshow(modifier: Modifier = Modifier) {
    Text(text = "tekst")
    Text(text = "Statystyki")
    Text(text = "Przykładowe")
    Text(text = "Listy ukończone")
    Text(text = "Próby")
    Text(text = "Wszystkich błedów")
}
@Composable
fun listshow(modifier: Modifier = Modifier, navController: NavController, lis : typelist){//tylko do prezentacji pomysłu
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(Color.Yellow)
            .height(80.dp)
            .clickable
            {
                navController.navigate("TypeE")
            }
    ) {
        //lewygorny
        Text(text = lis.nazwa,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            fontSize = 26.sp
        )
        //prawygorny
        Text(
            text = lis.len.toString(),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            fontSize = 26.sp
        )
        //lewydolny
        Text(
            text = lis.ukoncz.toString(),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
    }
}
@Composable
fun Button1(tekst : String,onClick: () -> Unit,modifier: Modifier = Modifier) {
    Button(onClick = { onClick() },modifier = modifier.padding(30.dp)) {
        Text(tekst,fontSize = 20.sp,modifier = modifier
            .fillMaxSize()
            .padding(15.dp), textAlign = TextAlign.Center)
    }
}
@Composable
fun Button2(tekst : String,onClick: () -> Unit,modifier: Modifier = Modifier) {
    Button(onClick = { onClick() },modifier = modifier.padding(20.dp)) {
        Text(tekst,fontSize = 20.sp,modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
            , textAlign = TextAlign.Center)
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FastypeTheme {
        //Greeting("Android")
    }
}