package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.HitungCuanRepository
import com.example.ui.components.HitungCuanHeader
import com.example.ui.screens.CalculatorScreen
import com.example.ui.screens.CashierScreen
import com.example.ui.screens.CatalogScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OnboardingProfileDialog
import com.example.ui.screens.SalesScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SageOnPrimary
import com.example.ui.theme.SagePrimary
import com.example.ui.theme.SagePrimaryContainer
import com.example.ui.theme.SagePrimaryFixed
import com.example.ui.theme.SageSurface
import com.example.ui.theme.SageSurfaceCard
import com.example.ui.theme.SageSurfaceContainer
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch

sealed class BottomNavDestination(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val testTag: String
) {
    object Beranda : BottomNavDestination("beranda", "Beranda", Icons.Default.Dashboard, "nav_beranda")
    object Kasir : BottomNavDestination("kasir", "Kasir", Icons.Default.PointOfSale, "nav_kasir")
    object Kalkulator : BottomNavDestination("kalkulator", "Kalkulator", Icons.Default.Calculate, "nav_kalkulator")
    object Penjualan : BottomNavDestination("penjualan", "Penjualan", Icons.Default.ReceiptLong, "nav_penjualan")
    object Katalog : BottomNavDestination("katalog", "Katalog", Icons.Default.Inventory2, "nav_katalog")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                HitungCuanApp()
            }
        }
    }
}

@Composable
fun HitungCuanApp() {
    var currentDestination by remember { mutableStateOf<BottomNavDestination>(BottomNavDestination.Beranda) }
    var calculatorInitialTab by remember { mutableStateOf("HPP Universal") }
    var showProfileDialog by remember { mutableStateOf(false) }

    val businessProfile by HitungCuanRepository.businessProfile.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val destinations = listOf(
        BottomNavDestination.Beranda,
        BottomNavDestination.Kasir,
        BottomNavDestination.Kalkulator,
        BottomNavDestination.Penjualan,
        BottomNavDestination.Katalog
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(SageSurface),
        topBar = {
            HitungCuanHeader(
                currentScreenTitle = currentDestination.title,
                storeName = businessProfile.brandName,
                onProfileClick = { showProfileDialog = true },
                onNotificationClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Target BEP bulan ini tercapai 78%!")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .shadow(8.dp)
                    .testTag("bottom_navigation"),
                containerColor = SageSurfaceCard,
                tonalElevation = 6.dp
            ) {
                destinations.forEach { dest ->
                    val isSelected = currentDestination.route == dest.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentDestination = dest },
                        icon = {
                            Icon(
                                imageVector = dest.icon,
                                contentDescription = dest.title,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = dest.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SagePrimary,
                            selectedTextColor = SagePrimary,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary,
                            indicatorColor = SagePrimaryFixed.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.testTag(dest.testTag)
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(SageSurface)
        ) {
            when (currentDestination) {
                BottomNavDestination.Beranda -> {
                    HomeScreen(
                        onNavigateToCashier = { currentDestination = BottomNavDestination.Kasir },
                        onNavigateToCalculator = {
                            calculatorInitialTab = "HPP Universal"
                            currentDestination = BottomNavDestination.Kalkulator
                        },
                        onNavigateToSales = { currentDestination = BottomNavDestination.Penjualan },
                        onNavigateToCatalog = { currentDestination = BottomNavDestination.Katalog }
                    )
                }
                BottomNavDestination.Kasir -> {
                    CashierScreen(
                        onCheckoutSuccess = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Transaksi Berhasil & Struk Dicetak!")
                            }
                        }
                    )
                }
                BottomNavDestination.Kalkulator -> {
                    CalculatorScreen(initialTab = calculatorInitialTab)
                }
                BottomNavDestination.Penjualan -> {
                    SalesScreen()
                }
                BottomNavDestination.Katalog -> {
                    CatalogScreen(
                        onNavigateToCalculator = { tab ->
                            calculatorInitialTab = tab
                            currentDestination = BottomNavDestination.Kalkulator
                        }
                    )
                }
            }
        }
    }

    // Business Profile & Onboarding Modal Dialog
    if (showProfileDialog) {
        OnboardingProfileDialog(
            currentProfile = businessProfile,
            onDismiss = { showProfileDialog = false },
            onSave = { name, category, scale, revenue ->
                HitungCuanRepository.updateProfile(name, category, scale, revenue)
                scope.launch {
                    snackbarHostState.showSnackbar("Profil Usaha berhasil disimpan!")
                }
            }
        )
    }
}
