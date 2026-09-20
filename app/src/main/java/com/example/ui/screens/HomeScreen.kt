package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FlagCircle
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.TakeoutDining
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.TransactionRecord
import com.example.data.repository.HitungCuanRepository
import com.example.ui.components.formatRupiah
import com.example.ui.theme.SageOnPrimary
import com.example.ui.theme.SagePrimary
import com.example.ui.theme.SagePrimaryContainer
import com.example.ui.theme.SagePrimaryFixed
import com.example.ui.theme.SageSurfaceCard
import com.example.ui.theme.SageSurfaceContainer
import com.example.ui.theme.SageSurfaceContainerHigh
import com.example.ui.theme.SageSurfaceContainerLow
import com.example.ui.theme.SemanticDanger
import com.example.ui.theme.SemanticWarning
import com.example.ui.theme.SlateSecondary
import com.example.ui.theme.SlateSurfaceDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    onNavigateToCashier: () -> Unit,
    onNavigateToCalculator: () -> Unit,
    onNavigateToSales: () -> Unit,
    onNavigateToCatalog: () -> Unit
) {
    var selectedPeriod by remember { mutableStateOf("Maret 2025") }
    val transactions by HitungCuanRepository.transactions.collectAsState()
    val profile by HitungCuanRepository.businessProfile.collectAsState()

    var showExpenseDialog by remember { mutableStateOf(false) }
    var expenseName by remember { mutableStateOf("") }
    var expenseAmount by remember { mutableStateOf("") }
    var showReportDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Store Location & Live Sync Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = SageSurfaceContainer,
                    modifier = Modifier.clickable { /* Select Branch */ }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = null,
                            tint = SlateSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = profile.branchName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = SagePrimary.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(SagePrimary)
                        )
                        Text(
                            text = "Live Sync",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SagePrimary
                        )
                    }
                }
            }
        }

        item {
            // Period Filter Pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val periods = listOf("Hari Ini", "7 Hari Terakhir", "Maret 2025")
                periods.forEach { period ->
                    val isSelected = selectedPeriod == period
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = if (isSelected) SagePrimary else SageSurfaceContainer,
                        shadowElevation = if (isSelected) 1.dp else 0.dp,
                        modifier = Modifier
                            .clickable { selectedPeriod = period }
                            .testTag("period_tab_$period")
                    ) {
                        Text(
                            text = period,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) SageOnPrimary else TextSecondary,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }

        item {
            // BEP Health Tracker Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SageSurfaceCard,
                shadowElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SemanticWarning.copy(alpha = 0.25f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FlagCircle,
                                    contentDescription = null,
                                    tint = Color(0xFFA07800),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Status Kesehatan Bisnis",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                                Text(
                                    text = "Target Titik Impas (BEP)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                        }
                        Text(
                            text = "78%",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SagePrimary
                        )
                    }

                    LinearProgressIndicator(
                        progress = { 0.78f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(999.dp)),
                        color = SagePrimary,
                        trackColor = SageSurfaceContainer,
                        strokeCap = StrokeCap.Round
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SagePrimary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "860 cup terjual",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                        Text(
                            text = "Sisa 240 cup ke BEP",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }
                }
            }
        }

        item {
            // Hero Financial Card (Slate Teal Theme)
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = SlateSurfaceDark,
                shadowElevation = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Total Omset Penjualan",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                            Text(
                                text = "Rp 24.850.000",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = (-0.5).sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = Color.White.copy(alpha = 0.18f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.TrendingUp,
                                    contentDescription = null,
                                    tint = SagePrimaryFixed,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "+14.2% vs bln lalu",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        }

                        Text(
                            text = "1.100 transaksi",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        item {
            // Net Profit Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SageSurfaceCard,
                shadowElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(SagePrimaryContainer.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Savings,
                                contentDescription = null,
                                tint = SagePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Cuan Bersih (Net Profit)",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "Rp 11.430.000",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = SagePrimaryContainer.copy(alpha = 0.35f)
                        ) {
                            Text(
                                text = "46.0% Margin",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SagePrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = "Sangat Sehat",
                            fontSize = 11.sp,
                            color = TextSecondary,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }

        item {
            // 2-Column Grid for HPP and Operational Cost
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // HPP Card
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(SageSurfaceContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Inventory2,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                            Text(
                                text = "HPP Terjual",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            text = "Rp 11.220k",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Bahan Baku", fontSize = 10.sp, color = TextSecondary)
                            Text(
                                text = "45.1%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }
                    }
                }

                // Operasional Card
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(SageSurfaceContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Receipt,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                            Text(
                                text = "Operasional",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            text = "Rp 2.200k",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Sewa & Listrik", fontSize = 10.sp, color = TextSecondary)
                            Text(
                                text = "8.9%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
        }

        item {
            // Visual Trend Chart Card (7 days back)
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SageSurfaceCard,
                shadowElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Tren Omset vs Margin",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Performa 7 hari ke belakang",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(SagePrimary)
                                )
                                Text(text = "Omset", fontSize = 11.sp, color = TextSecondary)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(SlateSecondary)
                                )
                                Text(text = "Profit", fontSize = 11.sp, color = TextSecondary)
                            }
                        }
                    }

                    // 7-day paired Bar Chart
                    val dayData = listOf(
                        Triple("Sen", 0.48f, 0.22f),
                        Triple("Sel", 0.58f, 0.28f),
                        Triple("Rab", 0.65f, 0.31f),
                        Triple("Kam", 0.52f, 0.25f),
                        Triple("Jum", 0.75f, 0.38f),
                        Triple("Sab", 0.94f, 0.50f),
                        Triple("Min", 0.85f, 0.44f)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        dayData.forEach { (day, omsetFraction, profitFraction) ->
                            val isPeak = day == "Sab" || day == "Min"
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    modifier = Modifier.height(100.dp)
                                ) {
                                    // Omset bar
                                    Box(
                                        modifier = Modifier
                                            .width(8.dp)
                                            .height((100 * omsetFraction).dp)
                                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                            .background(if (isPeak) SagePrimary else SagePrimary.copy(alpha = 0.4f))
                                    )
                                    // Profit bar
                                    Box(
                                        modifier = Modifier
                                            .width(8.dp)
                                            .height((100 * profitFraction).dp)
                                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                            .background(SlateSecondary)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = day,
                                    fontSize = 11.sp,
                                    fontWeight = if (isPeak) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isPeak) TextPrimary else TextSecondary
                                )
                            }
                        }
                    }

                    // Highlight Strip
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SageSurfaceContainerLow
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Hari tersibuk: Sabtu (Rp 4.8jt)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Margin stabil 48%",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SagePrimary
                            )
                        }
                    }
                }
            }
        }

        item {
            // Aksi Cepat (4 Thumb-Friendly Shortcuts)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "AKSI CEPAT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 0.5.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Button 1: Buka Kasir
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SageSurfaceCard,
                        shadowElevation = 1.dp,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigateToCashier() }
                            .testTag("action_open_cashier")
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(SagePrimary.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PointOfSale,
                                    contentDescription = null,
                                    tint = SagePrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "Buka Kasir",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }
                    }

                    // Button 2: Hitung HPP
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SageSurfaceCard,
                        shadowElevation = 1.dp,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigateToCalculator() }
                            .testTag("action_calculate_hpp")
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(SlateSecondary.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Calculate,
                                    contentDescription = null,
                                    tint = SlateSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "Hitung HPP",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }
                    }

                    // Button 3: Pengeluaran
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SageSurfaceCard,
                        shadowElevation = 1.dp,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showExpenseDialog = true }
                            .testTag("action_add_expense")
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(SemanticWarning.copy(alpha = 0.25f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountBalanceWallet,
                                    contentDescription = null,
                                    tint = Color(0xFF9E7300),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "Pengeluaran",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }
                    }

                    // Button 4: Laporan PDF
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SageSurfaceCard,
                        shadowElevation = 1.dp,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showReportDialog = true }
                            .testTag("action_pdf_report")
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(SageSurfaceContainerHigh),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PictureAsPdf,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "Laporan PDF",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
        }

        item {
            // Produk Bintang Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SageSurfaceContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { onNavigateToCatalog() }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AsyncImage(
                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCod2XHZGzztbWrbUpOTbOBKowB4MyD0bNoxAVh8uadWTOEQDs00Y5vhp0a8UK3a-nvIVFUTUKNYAWeIHXpufq3O5l1l9PlhXFLrzXvjc624sjfSExw8v0QcrSWTwkQnulGgbT2nCwwyiZPBWSzOwOptguM2lvPwBL0Yyl8O5c9UybVEco0KT_acttoFhl-vDv3HaZwpLcvxwnGsYiOzQ5kzrmhlAhzWE4P9OryqlwZl7KDCC0IEVcB",
                        contentDescription = "Produk Bintang",
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(14.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = SagePrimary
                        ) {
                            Text(
                                text = "Produk Bintang",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageOnPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Kopi Aren Santai (Literan)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Sumbang cuan terbesar: Rp 3.840.000",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        item {
            // Transaksi Terakhir Section Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transaksi Terakhir",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Lihat Semua",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SagePrimary,
                    modifier = Modifier.clickable { onNavigateToSales() }
                )
            }
        }

        items(transactions.take(3)) { tx ->
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = SageSurfaceCard,
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { onNavigateToSales() }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                if (tx.channel.contains("Offline")) SagePrimary.copy(alpha = 0.12f)
                                else SlateSecondary.copy(alpha = 0.12f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (tx.channel.contains("Offline")) Icons.Default.LocalCafe else Icons.Default.TakeoutDining,
                            contentDescription = null,
                            tint = if (tx.channel.contains("Offline")) SagePrimary else SlateSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = tx.id,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = tx.time,
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                        Text(
                            text = tx.itemsSummary.joinToString(", "),
                            fontSize = 11.sp,
                            color = TextSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = formatRupiah(tx.grossAmount),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = SagePrimary.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Margin ${tx.marginPercent.toInt()}%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SagePrimary,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            // Cuan Booster Banner
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = SagePrimary.copy(alpha = 0.1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(SagePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = SageOnPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "Cuan Booster Hari Ini",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Harga susu segar naik 3%, namun margin produk botolan Anda masih di atas benchmark industri (35%). Strategi bundling akhir pekan berhasil menjaga profit stabil.",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }

    // Quick Expense Modal Dialog
    if (showExpenseDialog) {
        AlertDialog(
            onDismissRequest = { showExpenseDialog = false },
            title = {
                Text(
                    text = "Catat Pengeluaran Operasional",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Contoh: Beli Es Batu, Gas LPG, Cup Tambahan",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    OutlinedTextField(
                        value = expenseName,
                        onValueChange = { expenseName = it },
                        label = { Text("Nama Pengeluaran") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = expenseAmount,
                        onValueChange = { expenseAmount = it },
                        label = { Text("Jumlah (Rp)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showExpenseDialog = false
                        expenseName = ""
                        expenseAmount = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
                ) {
                    Text("Simpan Biaya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExpenseDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    // PDF Report Export Dialog
    if (showReportDialog) {
        AlertDialog(
            onDismissRequest = { showReportDialog = false },
            title = {
                Text(
                    text = "Laporan Keuangan Siap",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Text(
                    text = "Laporan Laba Rugi & HPP untuk ${profile.brandName} (${selectedPeriod}) berhasil digenerate dan tersimpan rapi.",
                    fontSize = 13.sp,
                    color = TextPrimary
                )
            },
            confirmButton = {
                Button(
                    onClick = { showReportDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
                ) {
                    Text("Tutup")
                }
            }
        )
    }
}
