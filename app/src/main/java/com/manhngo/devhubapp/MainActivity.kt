package com.manhngo.devhubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.manhngo.devhubapp.ui.components.DevHubTopBar
import com.manhngo.devhubapp.ui.feature.ai.AIScreen
import com.manhngo.devhubapp.ui.feature.git.GitScreen
import com.manhngo.devhubapp.ui.feature.jobs.JobsScreen
import com.manhngo.devhubapp.ui.feature.news.NewsScreen
import com.manhngo.devhubapp.ui.navigation.Screen
import com.manhngo.devhubapp.ui.navigation.bottomNavItems
import com.manhngo.devhubapp.ui.theme.DevHubAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DevHubAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DevHubTopBar()
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        // Subtle horizontal divider at top of bottom bar
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter),
                            thickness = 0.75.dp,
                            color = Color(0xFFE2E8F0)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            bottomNavItems.forEach { screen ->
                                val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                                val primaryBlue = Color(0xFF1976D2)
                                val unselectedGrey = Color(0xFF8E9BAE)
                                val contentColor = if (selected) primaryBlue else unselectedGrey

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Top active indicator sitting directly flush on top border
                                    if (selected) {
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.TopCenter)
                                                .width(36.dp)
                                                .height(2.5.dp)
                                                .background(
                                                    color = primaryBlue,
                                                    shape = RoundedCornerShape(
                                                        bottomStart = 2.dp,
                                                        bottomEnd = 2.dp
                                                    )
                                                )
                                        )
                                    }

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.padding(top = 2.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = screen.icon),
                                            contentDescription = screen.title,
                                            modifier = Modifier.size(24.dp),
                                            tint = contentColor
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = screen.title,
                                            fontSize = 11.sp,
                                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                                            color = contentColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Git.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Git.route) { GitScreen() }
            composable(Screen.News.route) { NewsScreen() }
            composable(Screen.AI.route) { AIScreen() }
            composable(Screen.Jobs.route) { JobsScreen() }
        }
    }
}
