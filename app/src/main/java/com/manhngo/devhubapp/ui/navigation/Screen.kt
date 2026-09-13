package com.manhngo.devhubapp.ui.navigation

import androidx.annotation.DrawableRes
import com.manhngo.devhubapp.R

sealed class Screen(
    val route: String,
    val title: String,
    @get:DrawableRes val icon: Int
) {
    object Git : Screen("git", "Git", R.drawable.ic_git)
    object News : Screen("news", "News", R.drawable.ic_news)
    object AI : Screen("ai", "AI", R.drawable.ic_ai)
    object Jobs : Screen("jobs", "Jobs", R.drawable.ic_jobs)
}

val bottomNavItems = listOf(
    Screen.Git,
    Screen.News,
    Screen.AI,
    Screen.Jobs
)
