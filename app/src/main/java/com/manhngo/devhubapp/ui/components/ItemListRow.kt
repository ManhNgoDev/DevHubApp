package com.manhngo.devhubapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemListRow(
    icon: Painter,
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
) {
    ItemListRow(
        title = title,
        subTitle = subTitle,
        modifier = modifier,
        showDivider = showDivider,
        icon = {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    )
}

@Composable
fun ItemListRow(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    icon: @Composable () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1F2328)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subTitle,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF656D76)
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFE5E7EB)
            )
        }
    }
}