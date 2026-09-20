package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.SageOnPrimary
import com.example.ui.theme.SageOnPrimaryFixedVariant
import com.example.ui.theme.SagePrimary
import com.example.ui.theme.SagePrimaryContainer
import com.example.ui.theme.SagePrimaryFixed
import com.example.ui.theme.SageSurfaceCard
import com.example.ui.theme.SageSurfaceContainer
import com.example.ui.theme.SageSurfaceContainerLow
import com.example.ui.theme.SemanticDanger
import com.example.ui.theme.SlateSecondary
import com.example.ui.theme.SlateSurfaceDark
import com.example.ui.theme.TertiaryContainerColor
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(amount: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    formatter.maximumFractionDigits = 0
    return formatter.format(amount).replace("Rp", "Rp ").trim()
}

fun formatRupiah(amount: Int): String = formatRupiah(amount.toDouble())

@Composable
fun HitungCuanHeader(
    currentScreenTitle: String,
    storeName: String = "Kopi Cuan Nusantara",
    onProfileClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("app_header"),
        color = SageSurfaceCard,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onProfileClick() }
            ) {
                // Branded App Logo
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SagePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(20.dp)) {
                        // Minimalist coin / growth icon
                        drawCircle(
                            color = Color.White,
                            radius = size.minDimension / 2.2f,
                            style = Stroke(width = 2.5f)
                        )
                        drawLine(
                            color = Color.White,
                            start = Offset(size.width * 0.3f, size.height * 0.65f),
                            end = Offset(size.width * 0.45f, size.height * 0.45f),
                            strokeWidth = 2.5f,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color.White,
                            start = Offset(size.width * 0.45f, size.height * 0.45f),
                            end = Offset(size.width * 0.7f, size.height * 0.3f),
                            strokeWidth = 2.5f,
                            cap = StrokeCap.Round
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f, fill = false)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = storeName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = "Ganti Toko",
                            tint = TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Row(
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
                            text = "Online",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = SagePrimary
                        )
                        Text(
                            text = "•",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = currentScreenTitle,
                            fontSize = 11.sp,
                            color = TextSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Notifications icon with badge
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable { onNotificationClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifikasi",
                        tint = TextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 6.dp, end = 6.dp)
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(SemanticDanger)
                    )
                }

                // Profile Avatar Button
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SagePrimary)
                        .clickable { onProfileClick() }
                        .testTag("profile_avatar_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profil Usaha",
                        tint = SageOnPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CuanMarginBadge(
    marginPercent: Double,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = SagePrimaryFixed,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = "${marginPercent.toInt()}%",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SageOnPrimaryFixedVariant
            )
            Text(
                text = "cuan",
                fontSize = 9.sp,
                color = SageOnPrimaryFixedVariant.copy(alpha = 0.85f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun DonutChartHpp(
    bahanPercent: Float = 0.71f,
    kemasanPercent: Float = 0.22f,
    overheadPercent: Float = 0.07f,
    modifier: Modifier = Modifier,
    sizeDp: Dp = 100.dp
) {
    Box(
        modifier = modifier.size(sizeDp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 28f
            val diameter = size.minDimension - strokeWidth
            val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
            val arcSize = Size(diameter, diameter)

            var startAngle = -90f

            // Bahan Baku arc
            val sweep1 = bahanPercent * 360f
            drawArc(
                color = SagePrimary,
                startAngle = startAngle,
                sweepAngle = sweep1,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            startAngle += sweep1

            // Kemasan arc
            val sweep2 = kemasanPercent * 360f
            drawArc(
                color = SlateSecondary,
                startAngle = startAngle,
                sweepAngle = sweep2,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            startAngle += sweep2

            // Overhead arc
            val sweep3 = overheadPercent * 360f
            drawArc(
                color = TertiaryContainerColor,
                startAngle = startAngle,
                sweepAngle = sweep3,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "HPP",
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
            )
            Text(
                text = "100%",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}
