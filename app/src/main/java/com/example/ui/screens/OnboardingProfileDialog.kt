package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.BusinessProfile
import com.example.ui.theme.SageOnPrimary
import com.example.ui.theme.SagePrimary
import com.example.ui.theme.SagePrimaryContainer
import com.example.ui.theme.SagePrimaryFixed
import com.example.ui.theme.SageSurface
import com.example.ui.theme.SageSurfaceCard
import com.example.ui.theme.SageSurfaceContainer
import com.example.ui.theme.SageSurfaceContainerLow
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun OnboardingProfileDialog(
    currentProfile: BusinessProfile,
    onDismiss: () -> Unit,
    onSave: (name: String, category: String, scale: String, revenue: String) -> Unit
) {
    var brandName by remember { mutableStateOf(currentProfile.brandName) }
    var selectedCategory by remember { mutableStateOf(currentProfile.category) }
    var selectedScale by remember { mutableStateOf(currentProfile.scale) }
    var selectedRevenue by remember { mutableStateOf(currentProfile.monthlyRevenueTarget) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(24.dp))
                .testTag("onboarding_dialog"),
            color = SageSurfaceCard
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Dialog Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Kenalkan Usaha Anda ☕",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                        Text(
                            text = "Sesuaikan rumus HPP dan profil toko Anda",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Brand Name Input
                Text(
                    text = "Nama Brand / Usaha",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = brandName,
                    onValueChange = { brandName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("business_name_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SageSurfaceContainerLow,
                        unfocusedContainerColor = SageSurfaceContainerLow,
                        focusedBorderColor = SagePrimary,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = null,
                            tint = TextSecondary
                        )
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Category Selection
                Text(
                    text = "Kategori Bisnis Utama",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                val categories = listOf(
                    "Minuman & Kafe" to Icons.Default.Coffee,
                    "Bakery & Kue" to Icons.Default.Restaurant,
                    "Katering & Makanan" to Icons.Default.Restaurant,
                    "Fashion & Craft" to Icons.Default.ShoppingBag,
                    "Retail & Reseller" to Icons.Default.LocalMall
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    categories.chunked(2).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowItems.forEach { (catName, icon) ->
                                val isSelected = selectedCategory == catName
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (isSelected) SagePrimaryContainer.copy(alpha = 0.25f) else SageSurfaceContainerLow,
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(
                                            width = if (isSelected) 1.5.dp else 0.dp,
                                            color = if (isSelected) SagePrimary else Color.Transparent,
                                            shape = RoundedCornerShape(14.dp)
                                        )
                                        .clickable { selectedCategory = catName }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .clip(CircleShape)
                                                    .background(if (isSelected) SagePrimary.copy(alpha = 0.2f) else SageSurfaceContainer),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = icon,
                                                    contentDescription = null,
                                                    tint = if (isSelected) SagePrimary else TextSecondary,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                            Text(
                                                text = catName,
                                                fontSize = 12.sp,
                                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                                color = TextPrimary
                                            )
                                        }
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = SagePrimary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Business Scale
                Text(
                    text = "Skala Operasional Saat Ini",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                val scales = listOf("Rumahan / Pemula", "Kios / 1 Outlet", "Multi-Cabang")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SageSurfaceContainerLow)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    scales.forEach { scaleName ->
                        val isSelected = selectedScale == scaleName
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) SageSurfaceCard else Color.Transparent,
                            shadowElevation = if (isSelected) 1.dp else 0.dp,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedScale = scaleName }
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = scaleName,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) TextPrimary else TextSecondary,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Monthly Revenue Target
                Text(
                    text = "Estimasi Target Omset Bulanan",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                val revenues = listOf("< Rp 10 Jt", "Rp 10 - 50 Jt", "> Rp 50 Jt")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    revenues.forEach { rev ->
                        val isSelected = selectedRevenue == rev
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) SagePrimaryContainer.copy(alpha = 0.25f) else SageSurfaceContainerLow,
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    width = if (isSelected) 1.5.dp else 0.dp,
                                    color = if (isSelected) SagePrimary else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedRevenue = rev }
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = rev,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) SagePrimary else TextPrimary
                                )
                                Text(
                                    text = if (rev == "Rp 10 - 50 Jt") "Paling umum" else if (rev.contains("<")) "Rintisan" else "Ekspansi",
                                    fontSize = 10.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Save Button
                Button(
                    onClick = {
                        onSave(brandName, selectedCategory, selectedScale, selectedRevenue)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("save_profile_button"),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SagePrimary,
                        contentColor = SageOnPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Simpan Profil Usaha",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
