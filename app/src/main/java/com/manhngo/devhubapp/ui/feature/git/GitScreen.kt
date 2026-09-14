package com.manhngo.devhubapp.ui.feature.git

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.manhngo.devhubapp.R

@Composable
fun GitScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            modifier = Modifier.size(60.dp),
            painter = painterResource(R.drawable.logo_github),
            contentDescription = "Github Logo",
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            "Kết nối tới Github của bạn",
            fontWeight = FontWeight(700),
            lineHeight = 28.sp,
            fontSize = 20.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            "Đăng nhập để xem Repo riêng tư, theo dõi Issues và Pull Requests cá nhân",
            fontSize = 15.sp,
            lineHeight = 22.4.sp,
            color = Color(0xFF656D76),
            fontWeight = FontWeight(400),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 295.dp)
                .background(color = Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xffD0D7DE),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                "SAU KHI ĐĂNG NHẬP BẠN CÓ THỂ",
                fontSize = 15.sp,
                lineHeight = 16.sp,
                color = Color(0xff656D76),
                fontWeight = FontWeight(600)
            )

            Spacer(modifier = Modifier.height(15.dp))


        }
    }
}