package com.manhngo.devhubapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manhngo.devhubapp.R

@Composable
fun DevHubTopBar(
    modifier: Modifier = Modifier,
    username: String = "guest",
    isOnline: Boolean = true,
    onUserClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {
    Surface(
        color = Color.White,
        shadowElevation = 0.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Logo + "DevHub"
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onLogoClick
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_app),
                        contentDescription = "DevHub Logo",
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "DevHub",
                        color = Color(0xFF0F172A),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Right: Status dot + username + Avatar circle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onUserClick
                    )
                ) {
                    // Green status dot
                    if (isOnline) {
                        Box(
                            modifier = Modifier
                                .size(6.5.dp)
                                .background(
                                    color = Color(0xFF16A34A),
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }

                    Text(
                        text = username,
                        color = Color(0xFF5B718E),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    // Grey avatar circle with letter
                    val avatarInitial = username.firstOrNull()?.uppercase() ?: "G"
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                color = Color(0xFF8E9BAE),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = avatarInitial,
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Bottom subtle divider
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.75.dp,
                color = Color(0xFFE2E8F0)
            )
        }
    }
}
