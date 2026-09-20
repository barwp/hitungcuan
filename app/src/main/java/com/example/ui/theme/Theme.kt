package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val HitungCuanColorScheme = lightColorScheme(
    primary = SagePrimary,
    onPrimary = SageOnPrimary,
    primaryContainer = SagePrimaryContainer,
    onPrimaryContainer = SageOnPrimaryContainer,
    secondary = SlateSecondary,
    onSecondary = SlateOnSecondary,
    secondaryContainer = SlateSecondaryContainer,
    onSecondaryContainer = SlateOnSecondaryContainer,
    tertiary = TertiaryColor,
    onTertiary = OnTertiaryColor,
    tertiaryContainer = TertiaryContainerColor,
    background = SageSurface,
    onBackground = TextPrimary,
    surface = SageSurface,
    onSurface = TextPrimary,
    surfaceVariant = SageSurfaceContainer,
    onSurfaceVariant = TextSecondary,
    error = SemanticDanger,
    onError = SageOnPrimary,
    errorContainer = SemanticDangerContainer,
    onErrorContainer = OnSemanticDangerContainer,
    outline = OutlineColor,
    outlineVariant = OutlineVariant
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = HitungCuanColorScheme,
        typography = Typography,
        content = content
    )
}
