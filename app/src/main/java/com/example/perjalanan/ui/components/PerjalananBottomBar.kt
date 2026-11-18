package com.example.perjalanan.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.navigation.Screen
import com.example.perjalanan.ui.theme.CardBg
import com.example.perjalanan.ui.theme.Maroon
import com.example.perjalanan.ui.theme.SoftPeach
import com.example.perjalanan.ui.theme.TextGray

data class BottomNavItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
)

@Composable
fun PerjalananBottomBar(
    currentRoute: String?,
    onNavigate: (Screen) -> Unit
) {
    val strings = LocalAppStrings.current
    val items = listOf(
        BottomNavItem(Screen.Home, strings.navHome, Icons.Default.Home),
        BottomNavItem(Screen.Map, strings.navMap, Icons.Default.Place),
        BottomNavItem(Screen.Profile, strings.navProfile, Icons.Default.Person),
        BottomNavItem(Screen.Emergency, strings.navEmergency, Icons.Default.Phone)
    )

    NavigationBar(
        containerColor = CardBg,
        contentColor = Maroon
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.screen.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        onNavigate(item.screen)
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Maroon,
                    selectedTextColor = Maroon,
                    indicatorColor = SoftPeach,
                    unselectedIconColor = TextGray,
                    unselectedTextColor = TextGray
                )
            )
        }
    }
}
