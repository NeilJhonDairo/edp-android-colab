package com.example.businesscard

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BusinessCard()

            }
        }
    }
}



@Composable
fun BusinessCard() {


    val visible = remember {
        mutableStateOf(false)

    }


    LaunchedEffect(Unit) {
        visible.value = true

    }



    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(

                Brush.linearGradient(

                    colors = listOf(

                        Color(0xFF090B1A),
                        Color(0xFF16213E),
                        Color(0xFF533483)

                    )

                )

            ),

        contentAlignment = Alignment.Center

    ) {




        Box(

            modifier = Modifier
                .size(260.dp)
                .offset(
                    x = (-120).dp,
                    y = (-250).dp
                )
                .clip(CircleShape)
                .background(
                    Color(0x445C6BC0)
                )

        )


        Box(

            modifier = Modifier
                .size(200.dp)
                .offset(
                    x = 130.dp,
                    y = 250.dp
                )
                .clip(CircleShape)
                .background(
                    Color(0x445E35B1)
                )

        )



        AnimatedVisibility(

            visible = visible.value,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = {300}

            )

        ) {



            Card(

                modifier = Modifier
                    .padding(22.dp)

                    .fillMaxWidth(),


                shape = RoundedCornerShape(35.dp),


                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF11131C)

                ),


                elevation = CardDefaults.cardElevation(

                    20.dp

                )

            ) {



                Column(

                    modifier = Modifier
                        .padding(35.dp),

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {



                    Image(

                        painter = painterResource(
                            id = R.drawable.Dairo

                        ),

                        contentDescription = "Profile",

                        modifier = Modifier

                            .size(155.dp)
                            .clip(CircleShape)
                            .border(

                                5.dp,
                                Color(0xFFECEFF1),
                                CircleShape

                            )

                    )



                    Spacer(

                        modifier = Modifier.height(20.dp)
                    )



                    Text(

                        text = "Neil Jhon Dairo",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White

                    )



                    Spacer(

                        modifier = Modifier.height(8.dp)

                    )



                    Text(

                        text = "Information Technology Student",
                        fontSize = 17.sp,
                        color = Color(0xFFB0BEC5)

                    )



                    Spacer(

                        modifier = Modifier.height(25.dp)

                    )



                    Text(

                        text = "ANDROID DEVELOPER",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF90CAF9)

                    )



                    Spacer(

                        modifier = Modifier.height(30.dp)

                    )



                    ContactRow(

                        icon = "☎",
                        text = "+63 912 345 6789"

                    )



                    Spacer(

                        modifier = Modifier.height(15.dp)

                    )



                    ContactRow(

                        icon = "✉",
                        text = "neiljhondairo@gmail.com"

                    )

                }

            }

        }

    }

}





@Composable
fun ContactRow(

    icon: String,
    text: String

) {


    Row(

        modifier = Modifier

            .fillMaxWidth()

            .background(

                Color(0xFF20263A),

                RoundedCornerShape(20.dp)

            )

            .padding(16.dp),


        verticalAlignment = Alignment.CenterVertically

    ) {


        Text(

            text = icon,
            fontSize = 30.sp,
            color = Color(0xFF90CAF9)

        )


        Spacer(

            modifier = Modifier.width(15.dp)

        )


        Text(

            text = text,
            fontSize = 17.sp,
            color = Color.White

        )

    }

}





@Preview(

    showBackground = true,
    showSystemUi = true

)

@Composable
fun BusinessCardPreview() {

    BusinessCard()

}