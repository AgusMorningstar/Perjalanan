package com.example.perjalanan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    // Contoh koordinat (Makassar); ganti dengan lokasi korban dari server
    private val victim = LatLng(-5.1477, 119.4327)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        NavHelper.setup(this, findViewById<BottomNavigationView>(R.id.bottomNav), R.id.nav_map)

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<android.view.View>(R.id.btnMyLocation).setOnClickListener {
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(victim, 16f))
        }
        findViewById<android.view.View>(R.id.btnNavigate).setOnClickListener { startNavigation() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.addMarker(MarkerOptions().position(victim).title("Lokasi Korban"))
        googleMap.addCircle(
            CircleOptions().center(victim).radius(150.0)
                .fillColor(0x33C62828).strokeColor(0x66C62828.toInt()).strokeWidth(2f)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(victim, 16f))
    }

    private fun startNavigation() {
        val uri = Uri.parse("google.navigation:q=${victim.latitude},${victim.longitude}")
        val intent = Intent(Intent.ACTION_VIEW, uri).setPackage("com.google.android.apps.maps")
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Google Maps tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }
}