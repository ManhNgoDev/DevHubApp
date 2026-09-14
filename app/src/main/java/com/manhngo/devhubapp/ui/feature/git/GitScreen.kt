package com.manhngo.devhubapp.ui.feature.git

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manhngo.devhubapp.R
import com.manhngo.devhubapp.data.remote.dto.GitHubUserDto
import com.manhngo.devhubapp.ui.components.ItemListRow
import com.manhngo.devhubapp.util.constant.EnvConfig

@Composable
fun GitScreen(
    viewModel: GitViewModel? = null
) {
    if (viewModel == null) {
        GitUnauthenticatedContent(onLoginClick = {})
        return
    }

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    when (val state = uiState) {
        is GitUiState.Loading -> {
            GitLoadingContent()
        }
        is GitUiState.Error -> {
            GitErrorContent(
                message = state.message,
                onRetry = { viewModel.checkAuthStatus() }
            )
        }
        is GitUiState.Success -> {
            GitProfileContent(
                user = state.user,
                onLogout = { viewModel.logout() },
                onOpenProfile = {
                    val url = state.user.htmlUrl ?: "https://github.com/${state.user.login}"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            )
        }
        is GitUiState.Unauthenticated -> {
            GitUnauthenticatedContent(
                onLoginClick = {
                    val authUrl = "https://github.com/login/oauth/authorize?client_id=${EnvConfig.GITHUB_CLIENT_ID}&scope=repo,user"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun GitLoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF1976D2),
                modifier = Modifier.size(44.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Đang kết nối GitHub...",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF656D76)
            )
        }
    }
}

@Composable
fun GitErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo_github),
            contentDescription = "GitHub",
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Đã xảy ra lỗi",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD32F2F)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            fontSize = 14.sp,
            color = Color(0xFF656D76),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Thử lại", color = Color.White)
        }
    }
}

@Composable
fun GitProfileContent(
    user: GitHubUserDto,
    onLogout: () -> Unit,
    onOpenProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Real GitHub Avatar with fallback
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
                .background(Color(0xFF24292F)),
            contentAlignment = Alignment.Center
        ) {
            if (!user.avatarUrl.isNullOrBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.logo_github),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(44.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = user.name ?: user.login,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "@${user.login}",
            fontSize = 14.sp,
            color = Color(0xFF57606A)
        )

        if (!user.bio.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = user.bio,
                fontSize = 14.sp,
                color = Color(0xFF24292F),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Stats Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, Color(0xFFD0D7DE), RoundedCornerShape(12.dp))
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(title = "Repositories", value = user.publicRepos.toString())
            StatItem(title = "Followers", value = user.followers.toString())
            StatItem(title = "Following", value = user.following.toString())
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons
        Button(
            onClick = onOpenProfile,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF24292F),
                contentColor = Color.White
            )
        ) {
            Image(
                painter = painterResource(R.drawable.github_mark),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Xem hồ sơ trên GitHub")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFCF222E)
            )
        ) {
            Text("Đăng xuất tài khoản")
        }
    }
}

@Composable
private fun StatItem(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color(0xFF57606A)
        )
    }
}

@Composable
fun GitUnauthenticatedContent(
    onLoginClick: () -> Unit
) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFD0D7DE),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                "SAU KHI ĐĂNG NHẬP BẠN CÓ THỂ",
                fontSize = 13.sp,
                lineHeight = 16.sp,
                color = Color(0xFF656D76),
                fontWeight = FontWeight(600),
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFE5E7EB)
            )

            ItemListRow(
                icon = painterResource(R.drawable.ic_lock),
                title = "Xem Private Repos",
                subTitle = "Truy cập toàn bộ repo cá nhân và tổ chức",
                showDivider = true
            )

            ItemListRow(
                icon = painterResource(R.drawable.ic_issues),
                title = "Quản lý Issues & PRs",
                subTitle = "Xem và theo dõi Issues được gán cho bạn",
                showDivider = true
            )

            ItemListRow(
                icon = painterResource(R.drawable.ic_commit),
                title = "Commit Dashboard",
                subTitle = "Lịch sử commit với diff stats chi tiết",
                showDivider = true
            )

            ItemListRow(
                icon = painterResource(R.drawable.ic_notification),
                title = "Thông báo Real-time",
                subTitle = "Nhận ngay khi có PR review hoặc mention",
                showDivider = false
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff000000),
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.github_mark),
                contentDescription = "Github Logo",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                "Đăng nhập với Github"
            )
        }
    }
}