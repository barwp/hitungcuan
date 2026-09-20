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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Moped
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.TakeoutDining
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TransactionRecord
import com.example.data.repository.HitungCuanRepository
import com.example.ui.components.formatRupiah
import com.example.ui.theme.SageOnPrimary
import com.example.ui.theme.SageOnPrimaryContainer
import com.example.ui.theme.SageOnPrimaryFixedVariant
import com.example.ui.theme.SagePrimary
import com.example.ui.theme.SagePrimaryContainer
import com.example.ui.theme.SagePrimaryFixed
import com.example.ui.theme.SageSurfaceCard
import com.example.ui.theme.SageSurfaceContainer
import com.example.ui.theme.SageSurfaceContainerHigh
import com.example.ui.theme.SageSurfaceContainerLow
import com.example.ui.theme.SemanticDanger
import com.example.ui.theme.SlateCharcoal
import com.example.ui.theme.SlateSecondary
import com.example.ui.theme.SlateSecondaryContainer
import com.example.ui.theme.SlateSurfaceDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun SalesScreen() {
    val transactions by HitungCuanRepository.transactions.collectAsState()

    var selectedChannelFilter by remember { mutableStateOf("Semua Channel") }
    var showManualRecordDialog by remember { mutableStateOf(false) }

    val channelFilters = listOf("Semua Channel", "Kasir Offline", "GoFood/Grab", "ShopeeFood")

    val filteredTransactions = transactions.filter { tx ->
        when (selectedChannelFilter) {
            "Semua Channel" -> true
            "Kasir Offline" -> tx.channel.contains("Offline")
            "GoFood/Grab" -> tx.channel.contains("GoFood") || tx.channel.contains("Grab")
            "ShopeeFood" -> tx.channel.contains("Shopee")
            else -> true
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("sales_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 8.dp, bottom = 80.dp)
    ) {
        // Date Selector & Screen Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pencatatan Penjualan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Real-time ledger & margin tracking",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }

                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = SageSurfaceContainer,
                    modifier = Modifier.clickable { /* Select Date */ }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = SagePrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "24 Mar 2025",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }
                }
            }
        }

        // Live Performance Banner (Slate Teal Gradient)
        item {
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
                                text = "Omset Berjalan Hari Ini",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                            Text(
                                text = "Rp 1.840.000",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = SagePrimaryFixed.copy(alpha = 0.25f)
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
                                    text = "+14.2%",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SagePrimaryFixed
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.12f))
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "42 Struk Terbit",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            text = "•",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                        Text(
                            text = "Rata-rata Margin 51.2%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SagePrimaryFixed
                        )
                    }
                }
            }
        }

        // Action Button: Catat Transaksi Manual
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SageSurfaceCard,
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { showManualRecordDialog = true }
                    .testTag("record_manual_transaction_button")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = SagePrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Catat Transaksi Manual (Luar Kasir)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = SagePrimary
                    )
                }
            }
        }

        // Channel Filters Horizontal Row
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(channelFilters) { filter ->
                    val isSelected = selectedChannelFilter == filter
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = if (isSelected) SlateSecondary else SageSurfaceContainer,
                        shadowElevation = if (isSelected) 1.dp else 0.dp,
                        modifier = Modifier
                            .clickable { selectedChannelFilter = filter }
                            .testTag("sales_channel_filter_$filter")
                    ) {
                        Text(
                            text = when (filter) {
                                "Semua Channel" -> "Semua Channel (42)"
                                "Kasir Offline" -> "Kasir Offline (28)"
                                "GoFood/Grab" -> "GoFood/Grab (9)"
                                else -> "ShopeeFood (5)"
                            },
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else TextSecondary,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }

        // Transactions Cards List
        items(filteredTransactions, key = { it.id }) { tx ->
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SageSurfaceCard,
                shadowElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("transaction_item_${tx.id}")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Header: ID, Time, Channel Badge, Payment Badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = tx.id,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = tx.time,
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = if (tx.channel.contains("Offline")) SageSurfaceContainerHigh else SlateSecondaryContainer.copy(alpha = 0.4f)
                            ) {
                                Text(
                                    text = tx.channel,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (tx.channel.contains("Offline")) TextPrimary else SlateSecondary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = SageSurfaceContainer
                            ) {
                                Text(
                                    text = tx.paymentMethod,
                                    fontSize = 10.sp,
                                    color = TextSecondary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    // Items list
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        tx.itemsSummary.forEach { itemText ->
                            Text(
                                text = "• $itemText",
                                fontSize = 12.sp,
                                color = TextPrimary
                            )
                        }
                    }

                    // Financial metrics row
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = SageSurfaceContainerLow
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Omset Kotor", fontSize = 10.sp, color = TextSecondary)
                                Text(
                                    text = formatRupiah(tx.grossAmount),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                            }

                            if (tx.feeCutAmount > 0) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "Fee Potongan", fontSize = 10.sp, color = SemanticDanger)
                                    Text(
                                        text = "-${formatRupiah(tx.feeCutAmount)}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SemanticDanger
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "Cuan Bersih", fontSize = 10.sp, color = TextSecondary)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = formatRupiah(tx.netProfit),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SagePrimary
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(999.dp),
                                        color = SagePrimaryFixed
                                    ) {
                                        Text(
                                            text = "${tx.marginPercent.toInt()}%",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SageOnPrimaryFixedVariant,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Status & Order Type strip
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
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = tx.status,
                                fontSize = 11.sp,
                                color = TextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Text(
                            text = tx.orderType,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = SlateSecondary
                        )
                    }
                }
            }
        }

        // Pagination load more
        item {
            Surface(
                shape = RoundedCornerShape(999.dp),
                color = SageSurfaceContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { /* Load previous history */ }
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Muat Transaksi Sebelumnya",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary
                    )
                }
            }
        }
    }

    // Manual Transaction Dialog
    if (showManualRecordDialog) {
        var menuName by remember { mutableStateOf("Pesanan Katering Kantor") }
        var totalAmount by remember { mutableStateOf("150000") }
        var channelName by remember { mutableStateOf("Kasir Offline") }
        var paymentMethodName by remember { mutableStateOf("Transfer Bank") }

        AlertDialog(
            onDismissRequest = { showManualRecordDialog = false },
            title = {
                Text(
                    text = "Catat Transaksi Manual",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Gunakan untuk mencatat pesanan katering, bazaar, atau titip jual.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    OutlinedTextField(
                        value = menuName,
                        onValueChange = { menuName = it },
                        label = { Text("Keterangan Menu") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = totalAmount,
                        onValueChange = { totalAmount = it },
                        label = { Text("Total Nilai Penjualan (Rp)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = paymentMethodName,
                        onValueChange = { paymentMethodName = it },
                        label = { Text("Metode Pembayaran") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = totalAmount.toDoubleOrNull() ?: 0.0
                        HitungCuanRepository.addManualTransaction(menuName, amount, channelName, paymentMethodName)
                        showManualRecordDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
                ) {
                    Text("Catat Transaksi")
                }
            },
            dismissButton = {
                TextButton(onClick = { showManualRecordDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}
