package com.example.compose
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.material3.Typography
import com.example.salestestapp.theme.backgroundDark
import com.example.salestestapp.theme.backgroundDarkHighContrast
import com.example.salestestapp.theme.backgroundDarkMediumContrast
import com.example.salestestapp.theme.backgroundLight
import com.example.salestestapp.theme.backgroundLightHighContrast
import com.example.salestestapp.theme.backgroundLightMediumContrast
import com.example.salestestapp.theme.errorContainerDark
import com.example.salestestapp.theme.errorContainerDarkHighContrast
import com.example.salestestapp.theme.errorContainerDarkMediumContrast
import com.example.salestestapp.theme.errorContainerLight
import com.example.salestestapp.theme.errorContainerLightHighContrast
import com.example.salestestapp.theme.errorContainerLightMediumContrast
import com.example.salestestapp.theme.errorDark
import com.example.salestestapp.theme.errorDarkHighContrast
import com.example.salestestapp.theme.errorDarkMediumContrast
import com.example.salestestapp.theme.errorLight
import com.example.salestestapp.theme.errorLightHighContrast
import com.example.salestestapp.theme.errorLightMediumContrast
import com.example.salestestapp.theme.inverseOnSurfaceDark
import com.example.salestestapp.theme.inverseOnSurfaceDarkHighContrast
import com.example.salestestapp.theme.inverseOnSurfaceDarkMediumContrast
import com.example.salestestapp.theme.inverseOnSurfaceLight
import com.example.salestestapp.theme.inverseOnSurfaceLightHighContrast
import com.example.salestestapp.theme.inverseOnSurfaceLightMediumContrast
import com.example.salestestapp.theme.inversePrimaryDark
import com.example.salestestapp.theme.inversePrimaryDarkHighContrast
import com.example.salestestapp.theme.inversePrimaryDarkMediumContrast
import com.example.salestestapp.theme.inversePrimaryLight
import com.example.salestestapp.theme.inversePrimaryLightHighContrast
import com.example.salestestapp.theme.inversePrimaryLightMediumContrast
import com.example.salestestapp.theme.inverseSurfaceDark
import com.example.salestestapp.theme.inverseSurfaceDarkHighContrast
import com.example.salestestapp.theme.inverseSurfaceDarkMediumContrast
import com.example.salestestapp.theme.inverseSurfaceLight
import com.example.salestestapp.theme.inverseSurfaceLightHighContrast
import com.example.salestestapp.theme.inverseSurfaceLightMediumContrast
import com.example.salestestapp.theme.onBackgroundDark
import com.example.salestestapp.theme.onBackgroundDarkHighContrast
import com.example.salestestapp.theme.onBackgroundDarkMediumContrast
import com.example.salestestapp.theme.onBackgroundLight
import com.example.salestestapp.theme.onBackgroundLightHighContrast
import com.example.salestestapp.theme.onBackgroundLightMediumContrast
import com.example.salestestapp.theme.onErrorContainerDark
import com.example.salestestapp.theme.onErrorContainerDarkHighContrast
import com.example.salestestapp.theme.onErrorContainerDarkMediumContrast
import com.example.salestestapp.theme.onErrorContainerLight
import com.example.salestestapp.theme.onErrorContainerLightHighContrast
import com.example.salestestapp.theme.onErrorContainerLightMediumContrast
import com.example.salestestapp.theme.onErrorDark
import com.example.salestestapp.theme.onErrorDarkHighContrast
import com.example.salestestapp.theme.onErrorDarkMediumContrast
import com.example.salestestapp.theme.onErrorLight
import com.example.salestestapp.theme.onErrorLightHighContrast
import com.example.salestestapp.theme.onErrorLightMediumContrast
import com.example.salestestapp.theme.onPrimaryContainerDark
import com.example.salestestapp.theme.onPrimaryContainerDarkHighContrast
import com.example.salestestapp.theme.onPrimaryContainerDarkMediumContrast
import com.example.salestestapp.theme.onPrimaryContainerLight
import com.example.salestestapp.theme.onPrimaryContainerLightHighContrast
import com.example.salestestapp.theme.onPrimaryContainerLightMediumContrast
import com.example.salestestapp.theme.onPrimaryDark
import com.example.salestestapp.theme.onPrimaryDarkHighContrast
import com.example.salestestapp.theme.onPrimaryDarkMediumContrast
import com.example.salestestapp.theme.onPrimaryLight
import com.example.salestestapp.theme.onPrimaryLightHighContrast
import com.example.salestestapp.theme.onPrimaryLightMediumContrast
import com.example.salestestapp.theme.onSecondaryContainerDark
import com.example.salestestapp.theme.onSecondaryContainerDarkHighContrast
import com.example.salestestapp.theme.onSecondaryContainerDarkMediumContrast
import com.example.salestestapp.theme.onSecondaryContainerLight
import com.example.salestestapp.theme.onSecondaryContainerLightHighContrast
import com.example.salestestapp.theme.onSecondaryContainerLightMediumContrast
import com.example.salestestapp.theme.onSecondaryDark
import com.example.salestestapp.theme.onSecondaryDarkHighContrast
import com.example.salestestapp.theme.onSecondaryDarkMediumContrast
import com.example.salestestapp.theme.onSecondaryLight
import com.example.salestestapp.theme.onSecondaryLightHighContrast
import com.example.salestestapp.theme.onSecondaryLightMediumContrast
import com.example.salestestapp.theme.onSurfaceDark
import com.example.salestestapp.theme.onSurfaceDarkHighContrast
import com.example.salestestapp.theme.onSurfaceDarkMediumContrast
import com.example.salestestapp.theme.onSurfaceLight
import com.example.salestestapp.theme.onSurfaceLightHighContrast
import com.example.salestestapp.theme.onSurfaceLightMediumContrast
import com.example.salestestapp.theme.onSurfaceVariantDark
import com.example.salestestapp.theme.onSurfaceVariantDarkHighContrast
import com.example.salestestapp.theme.onSurfaceVariantDarkMediumContrast
import com.example.salestestapp.theme.onSurfaceVariantLight
import com.example.salestestapp.theme.onSurfaceVariantLightHighContrast
import com.example.salestestapp.theme.onSurfaceVariantLightMediumContrast
import com.example.salestestapp.theme.onTertiaryContainerDark
import com.example.salestestapp.theme.onTertiaryContainerDarkHighContrast
import com.example.salestestapp.theme.onTertiaryContainerDarkMediumContrast
import com.example.salestestapp.theme.onTertiaryContainerLight
import com.example.salestestapp.theme.onTertiaryContainerLightHighContrast
import com.example.salestestapp.theme.onTertiaryContainerLightMediumContrast
import com.example.salestestapp.theme.onTertiaryDark
import com.example.salestestapp.theme.onTertiaryDarkHighContrast
import com.example.salestestapp.theme.onTertiaryDarkMediumContrast
import com.example.salestestapp.theme.onTertiaryLight
import com.example.salestestapp.theme.onTertiaryLightHighContrast
import com.example.salestestapp.theme.onTertiaryLightMediumContrast
import com.example.salestestapp.theme.outlineDark
import com.example.salestestapp.theme.outlineDarkHighContrast
import com.example.salestestapp.theme.outlineDarkMediumContrast
import com.example.salestestapp.theme.outlineLight
import com.example.salestestapp.theme.outlineLightHighContrast
import com.example.salestestapp.theme.outlineLightMediumContrast
import com.example.salestestapp.theme.outlineVariantDark
import com.example.salestestapp.theme.outlineVariantDarkHighContrast
import com.example.salestestapp.theme.outlineVariantDarkMediumContrast
import com.example.salestestapp.theme.outlineVariantLight
import com.example.salestestapp.theme.outlineVariantLightHighContrast
import com.example.salestestapp.theme.outlineVariantLightMediumContrast
import com.example.salestestapp.theme.primaryContainerDark
import com.example.salestestapp.theme.primaryContainerDarkHighContrast
import com.example.salestestapp.theme.primaryContainerDarkMediumContrast
import com.example.salestestapp.theme.primaryContainerLight
import com.example.salestestapp.theme.primaryContainerLightHighContrast
import com.example.salestestapp.theme.primaryContainerLightMediumContrast
import com.example.salestestapp.theme.primaryDark
import com.example.salestestapp.theme.primaryDarkHighContrast
import com.example.salestestapp.theme.primaryDarkMediumContrast
import com.example.salestestapp.theme.primaryLight
import com.example.salestestapp.theme.primaryLightHighContrast
import com.example.salestestapp.theme.primaryLightMediumContrast
import com.example.salestestapp.theme.scrimDark
import com.example.salestestapp.theme.scrimDarkHighContrast
import com.example.salestestapp.theme.scrimDarkMediumContrast
import com.example.salestestapp.theme.scrimLight
import com.example.salestestapp.theme.scrimLightHighContrast
import com.example.salestestapp.theme.scrimLightMediumContrast
import com.example.salestestapp.theme.secondaryContainerDark
import com.example.salestestapp.theme.secondaryContainerDarkHighContrast
import com.example.salestestapp.theme.secondaryContainerDarkMediumContrast
import com.example.salestestapp.theme.secondaryContainerLight
import com.example.salestestapp.theme.secondaryContainerLightHighContrast
import com.example.salestestapp.theme.secondaryContainerLightMediumContrast
import com.example.salestestapp.theme.secondaryDark
import com.example.salestestapp.theme.secondaryDarkHighContrast
import com.example.salestestapp.theme.secondaryDarkMediumContrast
import com.example.salestestapp.theme.secondaryLight
import com.example.salestestapp.theme.secondaryLightHighContrast
import com.example.salestestapp.theme.secondaryLightMediumContrast
import com.example.salestestapp.theme.surfaceBrightDark
import com.example.salestestapp.theme.surfaceBrightDarkHighContrast
import com.example.salestestapp.theme.surfaceBrightDarkMediumContrast
import com.example.salestestapp.theme.surfaceBrightLight
import com.example.salestestapp.theme.surfaceBrightLightHighContrast
import com.example.salestestapp.theme.surfaceBrightLightMediumContrast
import com.example.salestestapp.theme.surfaceContainerDark
import com.example.salestestapp.theme.surfaceContainerDarkHighContrast
import com.example.salestestapp.theme.surfaceContainerDarkMediumContrast
import com.example.salestestapp.theme.surfaceContainerHighDark
import com.example.salestestapp.theme.surfaceContainerHighDarkHighContrast
import com.example.salestestapp.theme.surfaceContainerHighDarkMediumContrast
import com.example.salestestapp.theme.surfaceContainerHighLight
import com.example.salestestapp.theme.surfaceContainerHighLightHighContrast
import com.example.salestestapp.theme.surfaceContainerHighLightMediumContrast
import com.example.salestestapp.theme.surfaceContainerHighestDark
import com.example.salestestapp.theme.surfaceContainerHighestDarkHighContrast
import com.example.salestestapp.theme.surfaceContainerHighestDarkMediumContrast
import com.example.salestestapp.theme.surfaceContainerHighestLight
import com.example.salestestapp.theme.surfaceContainerHighestLightHighContrast
import com.example.salestestapp.theme.surfaceContainerHighestLightMediumContrast
import com.example.salestestapp.theme.surfaceContainerLight
import com.example.salestestapp.theme.surfaceContainerLightHighContrast
import com.example.salestestapp.theme.surfaceContainerLightMediumContrast
import com.example.salestestapp.theme.surfaceContainerLowDark
import com.example.salestestapp.theme.surfaceContainerLowDarkHighContrast
import com.example.salestestapp.theme.surfaceContainerLowDarkMediumContrast
import com.example.salestestapp.theme.surfaceContainerLowLight
import com.example.salestestapp.theme.surfaceContainerLowLightHighContrast
import com.example.salestestapp.theme.surfaceContainerLowLightMediumContrast
import com.example.salestestapp.theme.surfaceContainerLowestDark
import com.example.salestestapp.theme.surfaceContainerLowestDarkHighContrast
import com.example.salestestapp.theme.surfaceContainerLowestDarkMediumContrast
import com.example.salestestapp.theme.surfaceContainerLowestLight
import com.example.salestestapp.theme.surfaceContainerLowestLightHighContrast
import com.example.salestestapp.theme.surfaceContainerLowestLightMediumContrast
import com.example.salestestapp.theme.surfaceDark
import com.example.salestestapp.theme.surfaceDarkHighContrast
import com.example.salestestapp.theme.surfaceDarkMediumContrast
import com.example.salestestapp.theme.surfaceDimDark
import com.example.salestestapp.theme.surfaceDimDarkHighContrast
import com.example.salestestapp.theme.surfaceDimDarkMediumContrast
import com.example.salestestapp.theme.surfaceDimLight
import com.example.salestestapp.theme.surfaceDimLightHighContrast
import com.example.salestestapp.theme.surfaceDimLightMediumContrast
import com.example.salestestapp.theme.surfaceLight
import com.example.salestestapp.theme.surfaceLightHighContrast
import com.example.salestestapp.theme.surfaceLightMediumContrast
import com.example.salestestapp.theme.surfaceVariantDark
import com.example.salestestapp.theme.surfaceVariantDarkHighContrast
import com.example.salestestapp.theme.surfaceVariantDarkMediumContrast
import com.example.salestestapp.theme.surfaceVariantLight
import com.example.salestestapp.theme.surfaceVariantLightHighContrast
import com.example.salestestapp.theme.surfaceVariantLightMediumContrast
import com.example.salestestapp.theme.tertiaryContainerDark
import com.example.salestestapp.theme.tertiaryContainerDarkHighContrast
import com.example.salestestapp.theme.tertiaryContainerDarkMediumContrast
import com.example.salestestapp.theme.tertiaryContainerLight
import com.example.salestestapp.theme.tertiaryContainerLightHighContrast
import com.example.salestestapp.theme.tertiaryContainerLightMediumContrast
import com.example.salestestapp.theme.tertiaryDark
import com.example.salestestapp.theme.tertiaryDarkHighContrast
import com.example.salestestapp.theme.tertiaryDarkMediumContrast
import com.example.salestestapp.theme.tertiaryLight
import com.example.salestestapp.theme.tertiaryLightHighContrast
import com.example.salestestapp.theme.tertiaryLightMediumContrast
import com.example.salestestapp.ui.theme.Typography

//@Immutable
//data class ExtendedColorScheme

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast
)

//val extendedLight = ExtendedColorScheme(
//)
//
//val extendedDark = ExtendedColorScheme(
//)
//
//val extendedLightMediumContrast = ExtendedColorScheme(
//)
//
//val extendedLightHighContrast = ExtendedColorScheme(
//)
//
//val extendedDarkMediumContrast = ExtendedColorScheme(
//)
//
//val extendedDarkHighContrast = ExtendedColorScheme(
//)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
//      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//          val context = LocalContext.current
//          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colorScheme.primary.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

