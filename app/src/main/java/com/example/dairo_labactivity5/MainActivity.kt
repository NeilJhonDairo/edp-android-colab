package com.example.dairo_labactivity5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dairo_labactivity5.ui.theme.LabActivity5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LabActivity5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ReactiveScreen()
                }
            }
        }
    }
}

@Composable
fun ReactiveScreen() {

    var count by remember { mutableStateOf(0) }
    var name by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (name.isBlank()) "Hello, stranger!" else "Hello, $name!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Count: $count",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(onClick = { count-- }) {
                Text("−")
            }

            Button(onClick = { count = 0 }) {
                Text("Reset")
            }

            Button(onClick = { count++ }) {
                Text("+")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReactiveScreen() {
    LabActivity5Theme {
        ReactiveScreen()
    }
}