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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Product
import com.example.data.model.RawMaterial
import com.example.data.repository.HitungCuanRepository
import com.example.ui.components.CuanMarginBadge
import com.example.ui.components.formatRupiah
import com.example.ui.theme.SageOnPrimary
import com.example.ui.theme.SageOnPrimaryFixedVariant
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
import com.example.ui.theme.SlateSecondaryContainer
import com.example.ui.theme.SlateSurfaceDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CatalogScreen(
    onNavigateToCalculator: (tab: String) -> Unit
) {
    var activeTab by remember { mutableStateOf("Produk Jadi") }
    val products by HitungCuanRepository.products.collectAsState()
    val rawMaterials by HitungCuanRepository.rawMaterials.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("catalog_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 8.dp, bottom = 80.dp)
    ) {
        // Segmented Switch: Produk Jadi vs Bahan Baku
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(SageSurfaceContainer)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                listOf("Produk Jadi" to "18", "Bahan Baku" to "24").forEach { (tabTitle, count) ->
                    val isSelected = activeTab == tabTitle
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = if (isSelected) SagePrimary else Color.Transparent,
                        shadowElevation = if (isSelected) 1.dp else 0.dp,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { activeTab = tabTitle }
                            .testTag("catalog_tab_$tabTitle")
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = tabTitle,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) SageOnPrimary else TextSecondary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(if (isSelected) Color.White.copy(alpha = 0.25f) else SageSurfaceContainerHigh)
                                    .padding(horizontal = 6.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = count,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else TextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Search & Add Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(999.dp),
                    placeholder = {
                        Text(
                            text = if (activeTab == "Produk Jadi") "Cari produk & SKU..." else "Cari bahan baku...",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SageSurfaceCard,
                        unfocusedContainerColor = SageSurfaceCard,
                        focusedBorderColor = SagePrimary,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = SagePrimary,
                    modifier = Modifier
                        .clickable { showAddDialog = true }
                        .testTag("add_item_catalog_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = SageOnPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Tambah",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SageOnPrimary
                        )
                    }
                }
            }
        }

        // Warning Stock Alert Banner
        item {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = SemanticWarning.copy(alpha = 0.2f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF3C969)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFF6B4E00),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "3 Bahan Baku Menipis!",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Susu UHT (1.2L), Cup PET 14oz (28 pcs)",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Text(
                        text = "Kelola ->",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6B4E00),
                        modifier = Modifier.clickable { activeTab = "Bahan Baku" }
                    )
                }
            }
        }

        // Summary Metric Strip
        if (activeTab == "Produk Jadi") {
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = SagePrimaryFixed.copy(alpha = 0.35f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Rata-rata Margin Produk 51.4% Sehat",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SageOnPrimaryFixedVariant
                        )
                        Text(
                            text = "18 Siap Jual",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SageOnPrimaryFixedVariant
                        )
                    }
                }
            }

            // Products List
            val filteredProds = products.filter {
                it.name.contains(searchQuery, ignoreCase = true) || it.sku.contains(searchQuery, ignoreCase = true)
            }

            items(filteredProds, key = { it.id }) { prod ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AsyncImage(
                                model = prod.imageUrl,
                                contentDescription = prod.name,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(14.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = prod.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    CuanMarginBadge(marginPercent = prod.cuanPercent)
                                }
                                Text(
                                    text = "SKU: ${prod.sku} • ${prod.category}",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                                Text(
                                    text = "Sisa Stok: ${prod.stock} ${prod.unit}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (prod.stock < 10) SemanticDanger else TextSecondary
                                )
                            }
                        }

                        // Financial breakdown
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
                                Column {
                                    Text(text = "Harga Jual", fontSize = 10.sp, color = TextSecondary)
                                    Text(
                                        text = formatRupiah(prod.price),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "HPP Modal", fontSize = 10.sp, color = TextSecondary)
                                    Text(
                                        text = formatRupiah(prod.hpp),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextSecondary
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = "Cuan Bersih", fontSize = 10.sp, color = SagePrimary)
                                    Text(
                                        text = "+${formatRupiah(prod.profit)}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SagePrimary
                                    )
                                }
                            }
                        }

                        // Action buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = SageSurfaceContainer,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigateToCalculator("Margin & Harga") }
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.TrendingUp,
                                        contentDescription = null,
                                        tint = SlateSecondary,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Simulasi Margin",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SlateSecondary
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = SageSurfaceContainer,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigateToCalculator("HPP Universal") }
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Calculate,
                                        contentDescription = null,
                                        tint = SagePrimary,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Resep & HPP",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SagePrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Bahan Baku Tab
            val filteredRaw = rawMaterials.filter {
                it.name.contains(searchQuery, ignoreCase = true) || it.sku.contains(searchQuery, ignoreCase = true)
            }

            items(filteredRaw, key = { it.id }) { item ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "SKU: ${item.sku} • ${item.category}",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = if (item.isLowStock) SemanticDanger.copy(alpha = 0.2f) else SagePrimaryFixed
                            ) {
                                Text(
                                    text = item.stockDisplay,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (item.isLowStock) SemanticDanger else SageOnPrimaryFixedVariant,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Stock progress bar
                        LinearProgressIndicator(
                            progress = { item.stockPercent },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(999.dp)),
                            color = if (item.isLowStock) SemanticDanger else SagePrimary,
                            trackColor = SageSurfaceContainer,
                            strokeCap = StrokeCap.Round
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Beli: ${item.purchasePriceDisplay}",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = item.unitCostDisplay,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
        }
    }

    // Add Item Dialog
    if (showAddDialog) {
        var itemName by remember { mutableStateOf("") }
        var itemPrice by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = {
                Text(
                    text = if (activeTab == "Produk Jadi") "Tambah Produk Baru" else "Tambah Bahan Baku",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        label = { Text("Nama Item") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = itemPrice,
                        onValueChange = { itemPrice = it },
                        label = { Text("Harga (Rp)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showAddDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = SagePrimary)
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}
