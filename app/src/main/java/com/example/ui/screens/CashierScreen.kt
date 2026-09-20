package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Product
import com.example.data.repository.HitungCuanRepository
import com.example.ui.components.CuanMarginBadge
import com.example.ui.components.formatRupiah
import com.example.ui.theme.SageOnPrimary
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
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashierScreen(
    onCheckoutSuccess: () -> Unit = {}
) {
    val products by HitungCuanRepository.products.collectAsState()
    val cart by HitungCuanRepository.cart.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua Menu") }
    var isCheckoutSheetVisible by remember { mutableStateOf(false) }

    val categories = listOf("Semua Menu", "Kopi & Espresso", "Non-Kopi", "Pastry & Bakery", "Snack")

    val filteredProducts = products.filter { prod ->
        val matchesCategory = selectedCategory == "Semua Menu" || prod.category == selectedCategory
        val matchesSearch = prod.name.contains(searchQuery, ignoreCase = true) || prod.sku.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    val totalCartCount = cart.sumOf { it.quantity }
    val totalGross = cart.sumOf { it.product.price * it.quantity }
    val totalHpp = cart.sumOf { it.product.hpp * it.quantity }
    val totalProfit = totalGross - totalHpp
    val marginPct = if (totalGross > 0) (totalProfit / totalGross) * 100 else 0.0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("cashier_screen")
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 120.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Shift status strip
            item(span = { GridItemSpan(2) }) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(SagePrimary)
                            )
                            Text(
                                text = "Shift Pagi • Adit",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "34 Struk",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "•",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "Rp 1.480.000",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SagePrimary
                            )
                        }
                    }
                }
            }

            // Search Bar & Barcode button
            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("cashier_search_input"),
                        shape = RoundedCornerShape(999.dp),
                        placeholder = {
                            Text(
                                text = "Cari nama menu atau SKU...",
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
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Hapus",
                                        tint = TextSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
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
                        shape = CircleShape,
                        color = SageSurfaceCard,
                        shadowElevation = 1.dp,
                        modifier = Modifier
                            .size(44.dp)
                            .clickable { /* Barcode scan trigger */ }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.QrCodeScanner,
                                contentDescription = "Scan Barcode",
                                tint = SlateSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }

            // Horizontal Category Filter Pills
            item(span = { GridItemSpan(2) }) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = if (isSelected) SlateSecondary else SageSurfaceCard,
                            shadowElevation = if (isSelected) 1.dp else 0.dp,
                            modifier = Modifier.clickable { selectedCategory = cat }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = cat,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else TextSecondary
                                )
                                if (cat == "Semua Menu") {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(999.dp))
                                            .background(if (isSelected) Color.White.copy(alpha = 0.2f) else SageSurfaceContainer)
                                            .padding(horizontal = 6.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = "18",
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
            }

            // Products Grid
            items(filteredProducts, key = { it.id }) { product ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SageSurfaceCard,
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("menu_item_${product.sku}")
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Image with Cuan Badge & Limited tag
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .background(SageSurfaceContainerLow)
                        ) {
                            AsyncImage(
                                model = product.imageUrl,
                                contentDescription = product.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Cuan Margin Badge (Top-left)
                            CuanMarginBadge(
                                marginPercent = product.cuanPercent,
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(6.dp)
                            )

                            // "Terbatas" Tag if limited
                            if (product.isLimited) {
                                Surface(
                                    shape = RoundedCornerShape(999.dp),
                                    color = SemanticWarning.copy(alpha = 0.95f),
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(6.dp)
                                ) {
                                    Text(
                                        text = "Terbatas",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = product.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "Sisa ${product.stock} ${product.unit}",
                            fontSize = 11.sp,
                            color = if (product.stock < 10) SemanticDanger else TextSecondary,
                            fontWeight = if (product.stock < 10) FontWeight.SemiBold else FontWeight.Normal
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = formatRupiah(product.price),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            IconButton(
                                onClick = { HitungCuanRepository.addToCart(product) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SagePrimary)
                                    .testTag("add_item_${product.sku}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Tambah ${product.name}",
                                    tint = SageOnPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Floating Cart Action Bar (at bottom)
        AnimatedVisibility(
            visible = cart.isNotEmpty(),
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = SlateCharcoal,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isCheckoutSheetVisible = true }
                    .testTag("floating_cart_bar")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(SagePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = null,
                                tint = SageOnPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 1.dp, end = 1.dp)
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(SemanticDanger),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$totalCartCount",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Column {
                            Text(
                                text = formatRupiah(totalGross),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Margin: ${marginPct.toInt()}% (${formatRupiah(totalProfit)})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = SagePrimaryFixedDim
                            )
                        }
                    }

                    Button(
                        onClick = { isCheckoutSheetVisible = true },
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SagePrimary,
                            contentColor = SageOnPrimary
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.testTag("open_checkout_button")
                    ) {
                        Text(
                            text = "Bayar",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }
    }

    // Kasir Checkout Bottom Sheet
    if (isCheckoutSheetVisible) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        var paymentMethod by remember { mutableStateOf("Tunai") }
        var cashReceived by remember { mutableStateOf(100000.0) }
        var isCompleted by remember { mutableStateOf(false) }

        val changeAmount = (cashReceived - totalGross).coerceAtLeast(0.0)

        ModalBottomSheet(
            onDismissRequest = { isCheckoutSheetVisible = false },
            sheetState = sheetState,
            containerColor = SageSurfaceCard,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 28.dp)
                    .testTag("checkout_bottom_sheet"),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Kasir Checkout",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "$totalCartCount Produk Terpilih • Order #084",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }

                    IconButton(
                        onClick = { isCheckoutSheetVisible = false },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(SageSurfaceContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Payment Method Segmented Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(999.dp))
                        .background(SageSurfaceContainerLow)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf("Tunai" to Icons.Default.Payments, "QRIS" to Icons.Default.QrCode2, "Transfer" to Icons.Default.Payments).forEach { (method, icon) ->
                        val isSelected = paymentMethod == method
                        Surface(
                            shape = RoundedCornerShape(999.dp),
                            color = if (isSelected) SageSurfaceCard else Color.Transparent,
                            shadowElevation = if (isSelected) 1.dp else 0.dp,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { paymentMethod = method }
                                .testTag("payment_method_$method")
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = if (isSelected) SagePrimary else TextSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = method,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) SagePrimary else TextSecondary
                                )
                            }
                        }
                    }
                }

                // Amount Due Hero Card
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = SlateSecondary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Total Tagihan",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                            Text(
                                text = formatRupiah(totalGross),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = SlateSecondaryContainer
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "ESTIMASI LABA",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                                Text(
                                    text = "+${formatRupiah(totalProfit)}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SlateCharcoal
                                )
                            }
                        }
                    }
                }

                // Cash Received Row & Quick Chips
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Uang Diterima:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = formatRupiah(cashReceived),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val quickAmounts = listOf("Uang Pas" to totalGross, "Rp 90.000" to 90000.0, "Rp 100.000" to 100000.0)
                        quickAmounts.forEach { (label, amt) ->
                            val isSelected = cashReceived == amt
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) SagePrimary else SageSurfaceContainer,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { cashReceived = amt }
                            ) {
                                Box(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) SageOnPrimary else TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }

                // Calculated Change Highlight
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = SagePrimaryFixed.copy(alpha = 0.4f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
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
                                    .background(SagePrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CurrencyExchange,
                                    contentDescription = null,
                                    tint = SageOnPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Kembalian",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }

                        Text(
                            text = formatRupiah(changeAmount),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SagePrimary
                        )
                    }
                }

                // Complete Order Button
                Button(
                    onClick = {
                        HitungCuanRepository.completeCheckout(paymentMethod, cashReceived)
                        isCheckoutSheetVisible = false
                        onCheckoutSuccess()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("complete_checkout_button"),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SagePrimary,
                        contentColor = SageOnPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Print,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Selesaikan & Cetak Struk",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
