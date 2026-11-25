package com.example.perjalanan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.perjalanan.ui.localization.LocalAppStrings
import com.example.perjalanan.ui.theme.LocalAppColors
import com.example.perjalanan.ui.navigation.Screen
import com.example.perjalanan.ui.theme.Cream
import com.example.perjalanan.ui.theme.Maroon
import com.example.perjalanan.ui.theme.StrokeColor
import com.example.perjalanan.ui.theme.TextGray
import com.example.perjalanan.ui.theme.White

data class PoliceNavItem(
    val label: String,
    val icon: ImageVector,
    val screen: Screen
)

val policeNavItems = listOf(
    PoliceNavItem("Dashboard", Icons.Default.LocalPolice, Screen.PoliceDashboard),
    PoliceNavItem("Peta", Icons.Default.Map, Screen.PoliceMap),
    PoliceNavItem("Profil", Icons.Default.Person, Screen.PoliceProfile)
)

@Composable
fun PoliceBottomBar(
    currentRoute: String,
    onNavigate: (Screen) -> Unit
) {
    val strings = LocalAppStrings.current
    val colors = LocalAppColors.current
    val items = listOf(
        PoliceNavItem(strings.navDashboard, Icons.Default.LocalPolice, Screen.PoliceDashboard),
        PoliceNavItem(strings.navMap, Icons.Default.Map, Screen.PoliceMap),
        PoliceNavItem(strings.navProfile, Icons.Default.Person, Screen.PoliceProfile)
    )

    NavigationBar(
        containerColor = if (colors.isDark) colors.cardBackground else White,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.screen.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) onNavigate(item.screen)
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = if (selected) Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Maroon.copy(alpha = 0.12f))
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                            else Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (selected) Maroon else TextGray,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) Maroon else TextGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = androidx.compose.ui.graphics.Color.Transparent
                )
            )
        }
    }
}
