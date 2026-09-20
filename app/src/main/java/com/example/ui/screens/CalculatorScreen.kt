package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Moped
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.repository.HitungCuanRepository
import com.example.ui.components.DonutChartHpp
import com.example.ui.components.formatRupiah
import com.example.ui.theme.SageOnPrimary
import com.example.ui.theme.SageOnPrimaryContainer
import com.example.ui.theme.SageOnPrimaryFixedVariant
import com.example.ui.theme.SagePrimary
import com.example.ui.theme.SagePrimaryContainer
import com.example.ui.theme.SagePrimaryFixed
import com.example.ui.theme.SagePrimaryFixedDim
import com.example.ui.theme.SageSurfaceCard
import com.example.ui.theme.SageSurfaceContainer
import com.example.ui.theme.SageSurfaceContainerHigh
import com.example.ui.theme.SageSurfaceContainerLow
import com.example.ui.theme.SemanticDanger
import com.example.ui.theme.SemanticWarning
import com.example.ui.theme.SlateCharcoal
import com.example.ui.theme.SlateSecondary
import com.example.ui.theme.SlateSecondaryContainer
import com.example.ui.theme.SlateSurfaceDark
import com.example.ui.theme.TertiaryContainerColor
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlin.math.ceil

@Composable
fun CalculatorScreen(
    initialTab: String = "HPP Universal"
) {
    var activeSubTab by remember { mutableStateOf(initialTab) }
    val subTabs = listOf("HPP Universal", "Margin & Harga", "Marketplace", "BEP Impas")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("calculator_screen")
    ) {
        // Horizontal Scrollable Segmented Navigation Tabs
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(subTabs) { tab ->
                val isSelected = activeSubTab == tab
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = if (isSelected) SageSurfaceCard else SageSurfaceContainer,
                    shadowElevation = if (isSelected) 1.dp else 0.dp,
                    modifier = Modifier
                        .clickable { activeSubTab = tab }
                        .testTag("calc_tab_$tab")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val icon = when (tab) {
                            "HPP Universal" -> Icons.Default.Calculate
                            "Margin & Harga" -> Icons.Default.TrendingUp
                            "Marketplace" -> Icons.Default.Storefront
                            else -> Icons.Default.Balance
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSelected) SagePrimary else TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = tab,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) TextPrimary else TextSecondary
                        )
                    }
                }
            }
        }

        // Subtab Content
        Box(modifier = Modifier.weight(1f)) {
            when (activeSubTab) {
                "HPP Universal" -> HppUniversalTab()
                "Margin & Harga" -> MarginHargaTab()
                "Marketplace" -> MarketplaceFeeTab()
                "BEP Impas" -> BepImpasTab()
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 1: HPP UNIVERSAL
// -------------------------------------------------------------
@Composable
fun HppUniversalTab() {
    var recipeName by remember { mutableStateOf("Kopi Susu Gula Aren 250ml") }
    var batchOutput by remember { mutableIntStateOf(10) }
    var selectedUnit by remember { mutableStateOf("Botol / Porsi") }

    var isBahanExpanded by remember { mutableStateOf(true) }
    var isKemasanExpanded by remember { mutableStateOf(true) }
    var isOverheadExpanded by remember { mutableStateOf(true) }

    var isSavedToastVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 8.dp, bottom = 100.dp)
        ) {
            // Recipe Header Context
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(SagePrimaryFixed),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocalCafe,
                                        contentDescription = null,
                                        tint = SageOnPrimaryFixedVariant,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Resep & Batch Baru",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "Hitung akurat modal per porsi untuk tentukan harga jual",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }

                        // Name input
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Nama Produk / Resep",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                fontWeight = FontWeight.Medium
                            )
                            OutlinedTextField(
                                value = recipeName,
                                onValueChange = { recipeName = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SageSurfaceContainerLow,
                                    unfocusedContainerColor = SageSurfaceContainerLow,
                                    focusedBorderColor = SagePrimary,
                                    unfocusedBorderColor = Color.Transparent
                                )
                            )
                        }

                        // Output quantity & unit
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Jumlah Output",
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    fontWeight = FontWeight.Medium
                                )
                                OutlinedTextField(
                                    value = "$batchOutput",
                                    onValueChange = { batchOutput = it.toIntOrNull() ?: 1 },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    singleLine = true,
                                    trailingIcon = {
                                        Text(
                                            text = "botol",
                                            fontSize = 12.sp,
                                            color = TextSecondary,
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = SageSurfaceContainerLow,
                                        unfocusedContainerColor = SageSurfaceContainerLow,
                                        focusedBorderColor = SagePrimary,
                                        unfocusedBorderColor = Color.Transparent
                                    )
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Satuan",
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    fontWeight = FontWeight.Medium
                                )
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = SageSurfaceContainerLow,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = selectedUnit,
                                            fontSize = 12.sp,
                                            color = TextPrimary
                                        )
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = TextSecondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Accordion 1: Bahan Baku Pokok
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isBahanExpanded = !isBahanExpanded },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(SagePrimaryContainer.copy(alpha = 0.25f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Calculate,
                                        contentDescription = null,
                                        tint = SagePrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = "Bahan Baku Pokok",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(999.dp),
                                            color = SagePrimaryFixed
                                        ) {
                                            Text(
                                                text = "3 Bahan",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SageOnPrimaryFixedVariant,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "Subtotal: Rp 73.500 (Rp 7.350/porsi)",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }

                            Icon(
                                imageVector = if (isBahanExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = TextSecondary
                            )
                        }

                        AnimatedVisibility(visible = isBahanExpanded) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                HitungCuanRepository.rawIngredients.forEach { item ->
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = SageSurfaceContainerLow,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(12.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(6.dp)
                                                            .clip(CircleShape)
                                                            .background(SagePrimary)
                                                    )
                                                    Text(
                                                        text = item.name,
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.SemiBold,
                                                        color = TextPrimary
                                                    )
                                                }
                                                Text(
                                                    text = formatRupiah(item.totalCost),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = TextPrimary
                                                )
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 12.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = item.spec,
                                                    fontSize = 10.sp,
                                                    color = TextSecondary
                                                )
                                                Surface(
                                                    shape = RoundedCornerShape(999.dp),
                                                    color = SageSurfaceContainerHigh
                                                ) {
                                                    Text(
                                                        text = "${formatRupiah(item.costPerPortion)} / porsi",
                                                        fontSize = 9.sp,
                                                        color = TextPrimary,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(999.dp),
                                    color = SageSurfaceContainerLow,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { /* Add ingredient dialog */ }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AddCircle,
                                            contentDescription = null,
                                            tint = SagePrimary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Tambah Bahan Pokok",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = SagePrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Accordion 2: Kemasan & Packaging
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isKemasanExpanded = !isKemasanExpanded },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(SlateSecondaryContainer.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingBag,
                                        contentDescription = null,
                                        tint = SlateSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = "Kemasan & Packaging",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(999.dp),
                                            color = SlateSecondaryContainer.copy(alpha = 0.5f)
                                        ) {
                                            Text(
                                                text = "2 Item",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SlateSecondary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "Subtotal: Rp 15.500 (Rp 1.550/porsi)",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }

                            Icon(
                                imageVector = if (isKemasanExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = TextSecondary
                            )
                        }

                        AnimatedVisibility(visible = isKemasanExpanded) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                HitungCuanRepository.packagingIngredients.forEach { item ->
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = SageSurfaceContainerLow,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(12.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(6.dp)
                                                            .clip(CircleShape)
                                                            .background(SlateSecondary)
                                                    )
                                                    Text(
                                                        text = item.name,
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.SemiBold,
                                                        color = TextPrimary
                                                    )
                                                }
                                                Text(
                                                    text = formatRupiah(item.totalCost),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = TextPrimary
                                                )
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 12.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = item.spec,
                                                    fontSize = 10.sp,
                                                    color = TextSecondary
                                                )
                                                Surface(
                                                    shape = RoundedCornerShape(999.dp),
                                                    color = SageSurfaceContainerHigh
                                                ) {
                                                    Text(
                                                        text = "${formatRupiah(item.costPerPortion)} / porsi",
                                                        fontSize = 9.sp,
                                                        color = TextPrimary,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(999.dp),
                                    color = SageSurfaceContainerLow,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { /* Add packaging */ }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AddCircle,
                                            contentDescription = null,
                                            tint = SlateSecondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Tambah Kemasan",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = SlateSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Accordion 3: Tenaga Kerja & Operasional
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isOverheadExpanded = !isOverheadExpanded },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(TertiaryContainerColor.copy(alpha = 0.3f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Engineering,
                                        contentDescription = null,
                                        tint = TextPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = "Tenaga Kerja & Operasional",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(999.dp),
                                            color = SageSurfaceContainerHigh
                                        ) {
                                            Text(
                                                text = "2 Pos",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextSecondary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "Total: Rp 700 / porsi (Rp 7.000 / batch)",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }

                            Icon(
                                imageVector = if (isOverheadExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = TextSecondary
                            )
                        }

                        AnimatedVisibility(visible = isOverheadExpanded) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                HitungCuanRepository.overheadItems.forEach { item ->
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = SageSurfaceContainerLow,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(
                                                    text = item.name,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = TextPrimary
                                                )
                                                Text(
                                                    text = item.spec,
                                                    fontSize = 10.sp,
                                                    color = TextSecondary
                                                )
                                            }
                                            Text(
                                                text = "${formatRupiah(item.costPerPortion)} / porsi",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextPrimary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Proporsi Komponen Biaya (Donut Chart)
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
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
                                    text = "Proporsi Komponen Biaya",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Analisis struktur pengeluaran resep",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = SagePrimaryFixed
                            ) {
                                Text(
                                    text = "Efisien",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SageOnPrimaryFixedVariant,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            DonutChartHpp(
                                bahanPercent = 0.71f,
                                kemasanPercent = 0.22f,
                                overheadPercent = 0.07f,
                                sizeDp = 96.dp
                            )

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(SagePrimary)
                                        )
                                        Text(text = "Bahan Pokok", fontSize = 11.sp, color = TextPrimary)
                                    }
                                    Text(
                                        text = "71% (Rp 7.350)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(SlateSecondary)
                                        )
                                        Text(text = "Kemasan Botol", fontSize = 11.sp, color = TextPrimary)
                                    }
                                    Text(
                                        text = "22% (Rp 1.550)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(TertiaryContainerColor)
                                        )
                                        Text(text = "Tenaga & Ops", fontSize = 11.sp, color = TextPrimary)
                                    }
                                    Text(
                                        text = "7% (Rp 700)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = SageSurfaceContainerLow
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.TipsAndUpdates,
                                    contentDescription = null,
                                    tint = SagePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Modal per porsi Rp 9.050. Untuk mendapatkan margin standar cafe 50% - 60%, rekomendasi harga jual adalah Rp 18.000 - Rp 23.000.",
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sticky Bottom Result Bar (Pinned at bottom)
        Surface(
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            color = SageSurfaceCard,
            shadowElevation = 8.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "TOTAL HPP PER PORSI",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Rp 9.050",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Total Batch: Rp 90.500",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }

                Button(
                    onClick = { isSavedToastVisible = true },
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SagePrimaryContainer,
                        contentColor = SageOnPrimaryContainer
                    ),
                    modifier = Modifier.testTag("save_recipe_button")
                ) {
                    Icon(
                        imageVector = if (isSavedToastVisible) Icons.Default.Check else Icons.Default.Save,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isSavedToastVisible) "Tersimpan!" else "Simpan ke Produk",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 2: MARGIN & HARGA (Simulasi Harga Jual)
// -------------------------------------------------------------
@Composable
fun MarginHargaTab() {
    val hpp = 9850.0
    val monthlyQty = 1200

    var marginPercent by remember { mutableDoubleStateOf(45.3) }
    var sellingPrice by remember { mutableDoubleStateOf(18000.0) }

    fun updateFromMargin(m: Double) {
        val marginDec = (m / 100.0).coerceAtMost(0.95)
        var price = hpp / (1.0 - marginDec)
        price = (ceil(price / 100.0) * 100.0)
        sellingPrice = price
        marginPercent = m
    }

    fun updateFromPrice(p: Double) {
        sellingPrice = p
        if (p > hpp) {
            marginPercent = ((p - hpp) / p) * 100.0
        } else {
            marginPercent = 0.0
        }
    }

    val profit = (sellingPrice - hpp).coerceAtLeast(0.0)
    val monthlyProfit = profit * monthlyQty

    val qrisFee = sellingPrice * 0.007
    val offlineNet = (sellingPrice - qrisFee - hpp).coerceAtLeast(0.0)

    val ojolCut = (sellingPrice * 0.20) + 1000.0
    val ojolNet = (sellingPrice - ojolCut - hpp).coerceAtLeast(0.0)

    var isAppliedToCashier by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 8.dp, bottom = 100.dp)
        ) {
            // Active Product Banner
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SagePrimaryContainer.copy(alpha = 0.25f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocalCafe,
                                        contentDescription = null,
                                        tint = SagePrimary,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text(text = "Produk Aktif", fontSize = 11.sp, color = TextSecondary)
                                        Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(SagePrimaryContainer))
                                    }
                                    Text(
                                        text = "Kopi Susu Gula Aren 250ml",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "Modal HPP: Rp 9.850 / botol",
                                        fontSize = 12.sp,
                                        color = SlateSecondary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = SagePrimaryFixed.copy(alpha = 0.4f)
                            ) {
                                Text(
                                    text = "Ganti",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SagePrimary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }

                        // Sub-Cost Pills
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val subCosts = listOf("Bahan Baku" to "Rp 6.200", "Kemasan" to "Rp 1.850", "Operasional" to "Rp 1.800")
                            subCosts.forEach { (title, cost) ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = SageSurfaceContainerLow,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(text = title, fontSize = 10.sp, color = TextSecondary)
                                        Text(text = cost, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Interactive Two-Way Simulator Card (Slate Teal Canvas)
            item {
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = SlateSurfaceDark,
                    shadowElevation = 3.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Section A: Target Margin %
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Target Margin Keuntungan",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontWeight = FontWeight.Medium
                                )
                                Surface(
                                    shape = RoundedCornerShape(999.dp),
                                    color = SagePrimaryFixed.copy(alpha = 0.25f)
                                ) {
                                    Text(
                                        text = "Markup Dinamis",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SagePrimaryFixed,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "%.1f".format(marginPercent),
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "%",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White.copy(alpha = 0.7f),
                                        modifier = Modifier.padding(bottom = 2.dp, start = 2.dp)
                                    )
                                }
                                Text(
                                    text = "Ideal Kafe: 40-55%",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }

                            Slider(
                                value = marginPercent.toFloat(),
                                onValueChange = { updateFromMargin(it.toDouble()) },
                                valueRange = 10f..75f,
                                colors = SliderDefaults.colors(
                                    thumbColor = SagePrimaryContainer,
                                    activeTrackColor = SagePrimaryContainer,
                                    inactiveTrackColor = Color.White.copy(alpha = 0.25f)
                                )
                            )

                            // Preset Chips
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val presets = listOf(25.0 to "25% Grosir", 35.0 to "35% Reseller", 45.0 to "45% Kafe", 60.0 to "60% Dine-In")
                                presets.forEach { (presetMargin, label) ->
                                    val isSelected = kotlin.math.abs(marginPercent - presetMargin) < 1.0
                                    Surface(
                                        shape = RoundedCornerShape(999.dp),
                                        color = if (isSelected) SagePrimaryContainer else Color.White.copy(alpha = 0.15f),
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { updateFromMargin(presetMargin) }
                                    ) {
                                        Box(
                                            modifier = Modifier.padding(vertical = 6.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = label,
                                                fontSize = 10.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                color = if (isSelected) SageOnPrimaryContainer else Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Sync indicator
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color.White.copy(alpha = 0.1f))
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = null,
                                tint = SagePrimaryFixed,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Otomatis sinkron 2 arah",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }

                        // Section B: Selling Price
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Harga Jual ke Pembeli",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "HPP: Rp 9.850",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = Color.White.copy(alpha = 0.15f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Rp",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                    Text(
                                        text = "%,d".format(sellingPrice.toInt()).replace(',', '.'),
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            // Psychology Pricing Quick Suggestions
                            Text(
                                text = "Saran Taktik Harga Psikologis:",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val psychPrices = listOf(17900.0 to "Promo Ganjil", 18000.0 to "Pas Bulat", 20000.0 to "Premium")
                                psychPrices.forEach { (pPrice, pLabel) ->
                                    val isSelected = sellingPrice == pPrice
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (isSelected) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.12f),
                                        modifier = Modifier
                                            .weight(1f)
                                            .border(
                                                width = if (isSelected) 1.dp else 0.dp,
                                                color = if (isSelected) SagePrimaryContainer else Color.Transparent,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clickable { updateFromPrice(pPrice) }
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = formatRupiah(pPrice),
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) SagePrimaryFixed else Color.White
                                            )
                                            Text(
                                                text = pLabel,
                                                fontSize = 9.sp,
                                                color = Color.White.copy(alpha = 0.75f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Live Profit Metrics Panel
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = SageSurfaceCard,
                        shadowElevation = 2.dp,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = "Cuan per Botol", fontSize = 11.sp, color = TextSecondary)
                            Text(text = formatRupiah(profit), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SagePrimary)
                            Surface(shape = RoundedCornerShape(999.dp), color = SagePrimaryFixed) {
                                Text(
                                    text = "%.1f%% Margin".format(marginPercent),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SageOnPrimaryFixedVariant,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = SageSurfaceCard,
                        shadowElevation = 2.dp,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = "Est. Laba Bulanan", fontSize = 11.sp, color = TextSecondary)
                            Text(text = formatRupiah(monthlyProfit), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(text = "Asumsi 1.200 botol/bln", fontSize = 10.sp, color = TextSecondary)
                        }
                    }
                }
            }

            // Sales Channel Simulation Cards
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
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
                            Text(text = "Simulasi Saluran Penjualan", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(text = "Bersih Diterima", fontSize = 11.sp, color = TextSecondary)
                        }

                        // Kasir Offline
                        Surface(shape = RoundedCornerShape(14.dp), color = SageSurfaceContainerLow) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(SlateSecondary.copy(alpha = 0.12f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.Storefront, contentDescription = null, tint = SlateSecondary, modifier = Modifier.size(18.dp))
                                    }
                                    Column {
                                        Text(text = "Kasir Offline / Dine-in", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                        Text(text = "Potongan QRIS 0.7% (Rp ${qrisFee.toInt()})", fontSize = 10.sp, color = TextSecondary)
                                    }
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = formatRupiah(offlineNet), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SagePrimary)
                                    Text(text = "laba/btl", fontSize = 10.sp, color = TextSecondary)
                                }
                            }
                        }

                        // GoFood / GrabFood
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = SemanticDanger.copy(alpha = 0.1f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(SemanticDanger.copy(alpha = 0.2f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(imageVector = Icons.Default.Moped, contentDescription = null, tint = SemanticDanger, modifier = Modifier.size(18.dp))
                                        }
                                        Column {
                                            Text(text = "GoFood / GrabFood", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                            Text(text = "Komisi 20% + Biaya Rp 1.000", fontSize = 10.sp, color = TextSecondary)
                                        }
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(text = formatRupiah(ojolNet), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SemanticWarning)
                                        Text(text = "laba/btl", fontSize = 10.sp, color = TextSecondary)
                                    }
                                }

                                if (ojolNet < 5000) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(SemanticDanger.copy(alpha = 0.15f))
                                            .padding(6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = Color.Red, modifier = Modifier.size(14.dp))
                                        Text(
                                            text = "Margin tipis di ojol! Disarankan naikkan harga ke Rp 22.000",
                                            fontSize = 10.sp,
                                            color = TextPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Sticky Bottom Price Bar
        Surface(
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            color = SageSurfaceCard,
            shadowElevation = 8.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "Harga:", fontSize = 11.sp, color = TextSecondary)
                        Text(text = formatRupiah(sellingPrice), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                    Text(
                        text = "Cuan +${formatRupiah(profit)} /btl",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SagePrimary
                    )
                }

                Button(
                    onClick = { isAppliedToCashier = true },
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SlateSurfaceDark,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.testTag("apply_to_cashier_button")
                ) {
                    Text(
                        text = if (isAppliedToCashier) "Diterapkan!" else "Terapkan ke Kasir",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = if (isAppliedToCashier) Icons.Default.Done else Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 3: MARKETPLACE (Simulasi Anti-Bocor Fee)
// -------------------------------------------------------------
@Composable
fun MarketplaceFeeTab() {
    var selectedChannelId by remember { mutableStateOf("shopee") }
    var baseHpp by remember { mutableDoubleStateOf(25000.0) }
    var targetProfit by remember { mutableDoubleStateOf(15000.0) }
    var isGratisOngkir by remember { mutableStateOf(true) }
    var voucherDiscount by remember { mutableDoubleStateOf(2000.0) }
    var isCopied by remember { mutableStateOf(false) }

    val channel = HitungCuanRepository.marketplaceChannels.firstOrNull { it.id == selectedChannelId }
        ?: HitungCuanRepository.marketplaceChannels[0]

    val ongkirRate = if (isGratisOngkir) 0.04 else 0.0
    val totalRate = channel.adminRate + ongkirRate

    var recommendedPrice = (baseHpp + targetProfit + voucherDiscount + channel.fixedFee) / (1.0 - totalRate)
    recommendedPrice = (ceil(recommendedPrice / 500.0) * 500.0)

    val adminFee = recommendedPrice * channel.adminRate
    val ongkirFee = if (isGratisOngkir) (recommendedPrice * 0.04).coerceAtMost(10000.0) else 0.0
    val totalCut = adminFee + ongkirFee + voucherDiscount + channel.fixedFee
    val netReceived = (recommendedPrice - totalCut - baseHpp).coerceAtLeast(0.0)
    val marginPercentage = if (recommendedPrice > 0) (netReceived / recommendedPrice) * 100.0 else 0.0

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 8.dp, bottom = 100.dp)
        ) {
            // Header Descriptor Card
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SagePrimaryFixed.copy(alpha = 0.35f),
                    modifier = Modifier.fillMaxWidth()
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
                                .background(SagePrimaryContainer.copy(alpha = 0.35f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Storefront, contentDescription = null, tint = SagePrimary, modifier = Modifier.size(20.dp))
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Simulasi Anti-Bocor Fee", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(
                                text = "Hitung harga jual pas agar profit bersih tidak tergerus komisi.",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCDX-_6e04tA2ZVZdhAonw44GIOB7xFSj_IRAkRsXdDsXV4b-CRE4o8xV_0vV5LJcBLkIYG11dMEE5nnfq4-OFV6jZ_AA_Xj1647ez7tfNKvdQRFKXamo1jZfw-ADOhgMZOqCvzVGLP8b6elHirbAQMGBHzCIp7V4bUErB0t-cvWhje9FrIQRPsI1CKIQYKKpUjo4CXMRpcBqbaP_OfAa5kOz1cpqIwvOsNJQMOwJwV0CadjW0zHhS1",
                            contentDescription = "Seller",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            // Channel Selector Grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Pilih Kanal Penjualan", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        HitungCuanRepository.marketplaceChannels.take(2).forEach { ch ->
                            val isSelected = selectedChannelId == ch.id
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = SageSurfaceCard,
                                shadowElevation = 1.dp,
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        width = if (isSelected) 1.5.dp else 0.dp,
                                        color = if (isSelected) SagePrimary else Color.Transparent,
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable { selectedChannelId = ch.id }
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(text = ch.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                        if (isSelected) {
                                            Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(SagePrimary), contentAlignment = Alignment.Center) {
                                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(text = "${"%.1f".format(ch.adminRate * 100)}%", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = if (isSelected) SagePrimary else TextPrimary)
                                        Text(text = "+ Rp1.000", fontSize = 10.sp, color = TextSecondary)
                                    }
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        HitungCuanRepository.marketplaceChannels.drop(2).forEach { ch ->
                            val isSelected = selectedChannelId == ch.id
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = SageSurfaceCard,
                                shadowElevation = 1.dp,
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        width = if (isSelected) 1.5.dp else 0.dp,
                                        color = if (isSelected) SagePrimary else Color.Transparent,
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable { selectedChannelId = ch.id }
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(text = ch.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                        if (isSelected) {
                                            Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(SagePrimary), contentAlignment = Alignment.Center) {
                                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(text = "${"%.1f".format(ch.adminRate * 100)}%", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = if (isSelected) SagePrimary else TextPrimary)
                                        Text(text = "+ Rp1.000", fontSize = 10.sp, color = TextSecondary)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Calculation Inputs Card
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Modal Dasar
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Modal Dasar (HPP Produk)", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
                                Text(text = "Resep & Kemasan", fontSize = 10.sp, color = TextSecondary)
                            }
                            OutlinedTextField(
                                value = "%,d".format(baseHpp.toInt()).replace(',', '.'),
                                onValueChange = {
                                    val cleaned = it.replace(".", "")
                                    baseHpp = cleaned.toDoubleOrNull() ?: 0.0
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                prefix = { Text(text = "Rp ", fontSize = 13.sp, color = TextSecondary) },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SageSurfaceContainerLow,
                                    unfocusedContainerColor = SageSurfaceContainerLow,
                                    focusedBorderColor = SagePrimary,
                                    unfocusedBorderColor = Color.Transparent
                                )
                            )
                        }

                        // Target Cuan Bersih
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Target Cuan Bersih", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
                                Surface(shape = RoundedCornerShape(999.dp), color = SagePrimaryFixed) {
                                    Text(text = "Tinggi (60% HPP)", fontSize = 10.sp, color = SageOnPrimaryFixedVariant, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                            OutlinedTextField(
                                value = "%,d".format(targetProfit.toInt()).replace(',', '.'),
                                onValueChange = {
                                    val cleaned = it.replace(".", "")
                                    targetProfit = cleaned.toDoubleOrNull() ?: 0.0
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                prefix = { Text(text = "Rp ", fontSize = 13.sp, color = SagePrimary, fontWeight = FontWeight.Bold) },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SageSurfaceContainerLow,
                                    unfocusedContainerColor = SageSurfaceContainerLow,
                                    focusedBorderColor = SagePrimary,
                                    unfocusedBorderColor = Color.Transparent
                                )
                            )
                            Slider(
                                value = targetProfit.toFloat(),
                                onValueChange = { targetProfit = (it / 1000).toInt() * 1000.0 },
                                valueRange = 5000f..40000f,
                                colors = SliderDefaults.colors(
                                    thumbColor = SagePrimary,
                                    activeTrackColor = SagePrimary
                                )
                            )
                        }

                        // Gratis Ongkir Toggle
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = SageSurfaceContainerLow
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text(text = "Gratis Ongkir Xtra / Bebas Ongkir", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                        Icon(imageVector = Icons.Default.LocalShipping, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                                    }
                                    Text(text = "Komisi promo platform (4.0% maks Rp10.000)", fontSize = 10.sp, color = TextSecondary)
                                }
                                Switch(
                                    checked = isGratisOngkir,
                                    onCheckedChange = { isGratisOngkir = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = SagePrimary
                                    )
                                )
                            }
                        }

                        // Voucher Diskon Toko
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Voucher Diskon Toko", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
                                Text(text = "Subsidi Seller", fontSize = 10.sp, color = TextSecondary)
                            }
                            OutlinedTextField(
                                value = "%,d".format(voucherDiscount.toInt()).replace(',', '.'),
                                onValueChange = {
                                    val cleaned = it.replace(".", "")
                                    voucherDiscount = cleaned.toDoubleOrNull() ?: 0.0
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                prefix = { Text(text = "Rp ", fontSize = 13.sp, color = TextSecondary) },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SageSurfaceContainerLow,
                                    unfocusedContainerColor = SageSurfaceContainerLow,
                                    focusedBorderColor = SagePrimary,
                                    unfocusedBorderColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }

            // Recommendation & Breakdown Card
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
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
                            Text(text = "REKOMENDASI HARGA JUAL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Surface(shape = RoundedCornerShape(999.dp), color = SlateSecondary.copy(alpha = 0.12f)) {
                                Text(
                                    text = "Margin Bersih ${marginPercentage.toInt()}%",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateSecondary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = formatRupiah(recommendedPrice),
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = formatRupiah(recommendedPrice + 2000),
                                fontSize = 13.sp,
                                color = TextSecondary,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }

                        // Stacked Bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(999.dp))
                        ) {
                            Box(modifier = Modifier.weight(0.52f).background(SlateSurfaceDark))
                            Box(modifier = Modifier.weight(0.17f).background(SemanticDanger))
                            Box(modifier = Modifier.weight(0.31f).background(SagePrimaryContainer))
                        }

                        // Stacked Legend
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(SlateSurfaceDark))
                                    Text(text = "Modal", fontSize = 10.sp, color = TextSecondary)
                                }
                                Text(text = formatRupiah(baseHpp), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(SemanticDanger))
                                    Text(text = "Fee & Promo", fontSize = 10.sp, color = TextSecondary)
                                }
                                Text(text = formatRupiah(totalCut), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(SagePrimary))
                                    Text(text = "Cuan Bersih", fontSize = 10.sp, color = SagePrimary)
                                }
                                Text(text = formatRupiah(targetProfit), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SagePrimary)
                            }
                        }

                        // Detailed Fee Breakdown table
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = SageSurfaceContainerLow
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Rincian Biaya Potongan", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary)
                                    Text(text = "Nominal", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary)
                                }

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "• Biaya Layanan Kategori (${"%.1f".format(channel.adminRate * 100)}%)", fontSize = 11.sp, color = TextSecondary)
                                    Text(text = formatRupiah(adminFee), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                }

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "• Program Gratis Ongkir Xtra (4.0%)", fontSize = 11.sp, color = TextSecondary)
                                    Text(text = formatRupiah(ongkirFee), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                }

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "• Diskon Toko Ditanggung Seller", fontSize = 11.sp, color = TextSecondary)
                                    Text(text = formatRupiah(voucherDiscount), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                }

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "• Biaya Penanganan Transaksi", fontSize = 11.sp, color = TextSecondary)
                                    Text(text = formatRupiah(channel.fixedFee), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Total Potongan Marketplace", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Text(text = formatRupiah(totalCut), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                                }
                            }
                        }

                        // Coaching advice
                        Surface(shape = RoundedCornerShape(12.dp), color = SageSurfaceContainerHigh) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = SemanticWarning, modifier = Modifier.size(18.dp))
                                Text(
                                    text = "Pasang harga ${formatRupiah(recommendedPrice)} di etalase agar profit Anda tetap utuh ${formatRupiah(targetProfit)} setelah dipotong seluruh fee platform.",
                                    fontSize = 11.sp,
                                    color = TextPrimary,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sticky Bottom Price Bar
        Surface(
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            color = SageSurfaceCard,
            shadowElevation = 8.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Harga Rekomendasi", fontSize = 10.sp, color = TextSecondary)
                    Text(text = formatRupiah(recommendedPrice), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = "(Cuan ${formatRupiah(targetProfit)})", fontSize = 11.sp, color = SagePrimary, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = { isCopied = true },
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SagePrimaryContainer, contentColor = SageOnPrimaryContainer),
                    modifier = Modifier.testTag("copy_save_marketplace_button")
                ) {
                    Icon(imageVector = if (isCopied) Icons.Default.Done else Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = if (isCopied) "Tersalin!" else "Salin & Simpan", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 4: BEP IMPAS (Titik Impas)
// -------------------------------------------------------------
@Composable
fun BepImpasTab() {
    val fixedCost = 6250000.0 // Sewa 2.5jt + Gaji 3jt + Listrik 750k
    val baseHpp = 9050.0

    var simulatedPrice by remember { mutableFloatStateOf(20000f) }

    val basePrice = 18000.0
    val baseContributionMargin = basePrice - baseHpp
    val baseBepCups = ceil(fixedCost / baseContributionMargin).toInt() // ~699 cups

    val simContributionMargin = (simulatedPrice.toDouble() - baseHpp).coerceAtLeast(100.0)
    val simulatedBepCups = ceil(fixedCost / simContributionMargin).toInt()
    val cupDiff = baseBepCups - simulatedBepCups

    var isTargetSaved by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 8.dp, bottom = 100.dp)
        ) {
            // Big Highlight Result Card (Slate Teal Theme)
            item {
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = SlateSurfaceDark,
                    shadowElevation = 3.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = SlateSecondaryContainer.copy(alpha = 0.25f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Flag, contentDescription = null, tint = SlateSecondaryContainer, modifier = Modifier.size(13.dp))
                                Text(text = "TITIK IMPAS (BEP)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SlateSecondaryContainer)
                            }
                        }

                        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(text = "$baseBepCups Cup", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(text = "/ bulan", fontSize = 13.sp, color = Color.White.copy(alpha = 0.8f))
                        }

                        Text(
                            text = "Target minimum penjualan agar operasional tidak rugi sepeserpun.",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )

                        // Daily Target Chip
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color.White.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = SemanticWarning, modifier = Modifier.size(18.dp))
                                    Column {
                                        Text(text = "Target Penjualan Harian", fontSize = 10.sp, color = Color.White.copy(alpha = 0.8f))
                                        Text(text = "24 cup / hari", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                }
                                Surface(shape = RoundedCornerShape(999.dp), color = Color.White.copy(alpha = 0.15f)) {
                                    Text(text = "30 Hari", fontSize = 10.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Omset Impas Rupiah:", fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                            Text(text = formatRupiah(baseBepCups * basePrice), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SlateSecondaryContainer)
                        }
                    }
                }
            }

            // Visual Profit Zone Map
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Peta Zona Profit Bisnis", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(text = "Basis Penjualan", fontSize = 11.sp, color = TextSecondary)
                        }

                        // Segmented bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(999.dp))
                        ) {
                            Box(modifier = Modifier.weight(0.5f).background(SemanticDanger))
                            Box(modifier = Modifier.width(3.dp).background(TextPrimary))
                            Box(modifier = Modifier.weight(0.5f).background(SagePrimaryContainer))
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "0 - 698 Cup (Rugi)", fontSize = 10.sp, color = SemanticDanger, fontWeight = FontWeight.Medium)
                            Text(text = "699 Cup", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(text = "> 700 Cup (Cuan Murni)", fontSize = 10.sp, color = SagePrimary, fontWeight = FontWeight.Bold)
                        }

                        Surface(shape = RoundedCornerShape(12.dp), color = SageSurfaceContainerLow) {
                            Text(
                                text = "💡 Setiap penjualan mulai cup ke-700 menghasilkan profit bersih utuh Rp 8.950 per cup langsung ke kantong Anda!",
                                fontSize = 11.sp,
                                color = TextPrimary,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }

            // Calculation Parameters
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = "A. BIAYA TETAP (FIXED COST)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

                        val fixedCosts = listOf("Sewa Tempat / Booth" to "Rp 2.500.000", "Gaji Karyawan / Barista" to "Rp 3.000.000", "Listrik, Air & Wi-Fi" to "Rp 750.000")
                        fixedCosts.forEach { (label, amt) ->
                            Surface(shape = RoundedCornerShape(12.dp), color = SageSurfaceContainerLow) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = label, fontSize = 11.sp, color = TextSecondary)
                                    Text(text = amt, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                }
                            }
                        }

                        Surface(shape = RoundedCornerShape(12.dp), color = SageSurfaceContainer) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Total Biaya Tetap:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                Text(text = "Rp 6.250.000 /bln", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "B. UNIT ECONOMICS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(shape = RoundedCornerShape(12.dp), color = SageSurfaceContainerLow, modifier = Modifier.weight(1f)) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = "Harga Rata-rata", fontSize = 10.sp, color = TextSecondary)
                                    Text(text = "Rp 18.000", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                }
                            }
                            Surface(shape = RoundedCornerShape(12.dp), color = SageSurfaceContainerLow, modifier = Modifier.weight(1f)) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = "HPP Dasar", fontSize = 10.sp, color = TextSecondary)
                                    Text(text = "Rp 9.050", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                }
                            }
                        }

                        Surface(shape = RoundedCornerShape(12.dp), color = SagePrimaryFixed.copy(alpha = 0.35f)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "Margin Kontribusi", fontSize = 10.sp, color = TextSecondary)
                                    Text(text = "Rp 8.950 / cup", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SagePrimary)
                                }
                                Surface(shape = RoundedCornerShape(999.dp), color = SagePrimary) {
                                    Text(text = "49.7%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Interactive What-If Simulation
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Simulasi “What-If”", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Surface(shape = RoundedCornerShape(999.dp), color = SemanticWarning.copy(alpha = 0.25f)) {
                                Text(text = "Eksperimen", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA07800), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Text(
                            text = "Geser simulasi kenaikan harga jual untuk melihat seberapa cepat bisnis Anda mencapai titik balik modal:",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Simulasi Harga Jual", fontSize = 11.sp, color = TextSecondary)
                            Text(text = formatRupiah(simulatedPrice.toDouble()), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SagePrimary)
                        }

                        Slider(
                            value = simulatedPrice,
                            onValueChange = { simulatedPrice = (it / 500).toInt() * 500f },
                            valueRange = 18000f..25000f,
                            colors = SliderDefaults.colors(thumbColor = SagePrimary, activeTrackColor = SagePrimary)
                        )

                        Surface(shape = RoundedCornerShape(14.dp), color = SageSurfaceContainerLow) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(SagePrimary.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.RocketLaunch, contentDescription = null, tint = SagePrimary, modifier = Modifier.size(16.dp))
                                }
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(text = "Dampak Kenaikan Harga:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Text(
                                        text = if (cupDiff > 0) {
                                            "Jika harga jual dinaikkan menjadi ${formatRupiah(simulatedPrice.toDouble())}, target BEP turun menjadi $simulatedBepCups cup (-$cupDiff cup lebih cepat balik modal!)."
                                        } else {
                                            "Harga sama dengan patokan awal. Target BEP $simulatedBepCups cup."
                                        },
                                        fontSize = 11.sp,
                                        color = TextSecondary,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Sticky Bottom Bar
        Surface(
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            color = SageSurfaceCard,
            shadowElevation = 8.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Target BEP: 24 cup / hari", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = "Omset Impas: Rp 12,5 Jt", fontSize = 11.sp, color = TextSecondary)
                }

                Button(
                    onClick = { isTargetSaved = true },
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SagePrimaryContainer, contentColor = SageOnPrimaryContainer),
                    modifier = Modifier.testTag("save_target_bep_button")
                ) {
                    Icon(imageVector = if (isTargetSaved) Icons.Default.Done else Icons.Default.BookmarkAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = if (isTargetSaved) "Target Disimpan!" else "Simpan Target", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
