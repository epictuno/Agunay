package com.project.agunay.adapter.kotlin.screens.shopscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.agunay.DarkPurple
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomButtons
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.ShopElement
import com.project.agunay.adapter.kotlin.components.WalkQuizTopBar
import com.project.agunay.domain.ShopItem

@Composable
fun ShopScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            WalkQuizTopBar(
                text = R.string.shop_button,
                icon = R.drawable.shop,
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(DarkPurple)
        ) {
            AvailablePointsCard(0)
            ShopElementGrid(listOf())
            BottomButtons(navController)
            BottomText()
        }
    }
}

@Composable
fun ShopElementGrid(elementList: List<ShopItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ) {
        items(8) {item ->
            ShopElement(
                image = R.drawable.placeholder,
                name = "Revelador dinámico",
                rightButtonText = "Comprar",
                onRightButtonClick = {}
            )
        }
    }
    //Implementar que los elementos ShopElement se creen a partir de una lista de ShopItem
}

@Composable
fun AvailablePointsCard(points: Int) {
    Card {
        Text(
            text = stringResource(R.string.available_points, points),
            fontSize = 20.sp,
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
@Preview
fun ShopElementPreview() {
    ShopElement(
        image = R.drawable.placeholder,
        name = "Revelador dinámico",
        rightButtonText = "Comprar",
        onRightButtonClick = {}
    )
}

@Composable
@Preview
fun ShopScreenPreview() {
    ShopScreen(rememberNavController())
}