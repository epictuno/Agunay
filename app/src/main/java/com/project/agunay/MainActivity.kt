package com.project.agunay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.agunay.ui.theme.AgunayTheme
val DarkPurple = Color(0xFF290B32)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgunayTheme {
                Background()
            }
        }
    }
}
@Composable
fun Background(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(DarkPurple),
        color = Color.Transparent
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AgunayTheme {
        Background()
    }
}