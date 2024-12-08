package com.project.agunay.adapter.kotlin.screens.shopscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.agunay.DarkPurple
import com.project.agunay.R
import com.project.agunay.adapter.kotlin.components.BottomButtons
import com.project.agunay.adapter.kotlin.components.BottomText
import com.project.agunay.adapter.kotlin.components.ShopElement
import com.project.agunay.adapter.kotlin.components.WalkQuizTopBar
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.domain.ShopItem
import com.project.agunay.domain.User
import kotlin.reflect.KFunction2

@Composable
fun ShopScreen(navController: NavHostController, currentUser: CurrentUser, navBackStackEntry: NavBackStackEntry) {
    val viewModel: ShopScreenVM = viewModel()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()
    val shopItems by viewModel.shopItems.observeAsState(emptyList())
    val user by viewModel.currentUser.observeAsState()

    Scaffold(
        topBar = {
            WalkQuizTopBar(
                text = R.string.shop,
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
            AvailablePointsCard(points = user?.points ?: 0, isLoading = isLoading)
            ShopElementGrid(shopItems,currentUser, viewModel::buyShopItem)
            BottomButtons(navController)
            BottomText()
        }
    }

    if (error != null) {
        ErrorDialog(error = error!!, onDismiss = { viewModel.cleanErrors() })
    }
}

@Composable
fun ErrorDialog(error: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.error_title))
        },
        text = {
            Text(text = error)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.close_dialog))
            }
        }
    )
}

@Composable
fun ShopElementGrid(elementList: List<ShopItem>,currentUser: CurrentUser, onBuyClick: KFunction2<ShopItem, User, Unit>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ) {
        items(elementList) { item ->
            ShopElement(
                shopItem = item,
                onRightButtonClick = { currentUser.getUser()?.let { onBuyClick(item, it) } }
            )
        }
    }
}

@Composable
fun AvailablePointsCard(points: Int, isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    } else {
        Card {
            Text(
                text = stringResource(R.string.available_points, points),
                fontSize = 20.sp,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}

@Composable
@Preview
fun ShopElementPreview() {
    ShopElement(
        shopItem = ShopItem(),
        onRightButtonClick = {}
    )
}

@Composable
@Preview
fun ShopScreenPreview() {
    ShopScreen(
        rememberNavController(), CurrentUser(),
        navBackStackEntry = TODO(),
    )
}