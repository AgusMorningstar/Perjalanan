package com.example.perjalanan

import android.app.Activity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

object NavHelper {
    fun setup(activity: Activity, nav: BottomNavigationView, selectedId: Int) {
        nav.selectedItemId = selectedId
        nav.setOnItemSelectedListener { item ->
            if (item.itemId != selectedId) {
                val target = when (item.itemId) {
                    R.id.nav_home -> HomeActivity::class.java
                    R.id.nav_map -> MapActivity::class.java
                    R.id.nav_profile -> ProfileActivity::class.java
                    R.id.nav_emergency -> EmergencyActivity::class.java
                    else -> null
                }
                target?.let {
                    activity.startActivity(Intent(activity, it))
                    activity.finish()
                    activity.overridePendingTransition(0, 0)
                }
            }
            true
        }
    }
}