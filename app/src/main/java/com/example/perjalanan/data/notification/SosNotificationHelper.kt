package com.example.perjalanan.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.perjalanan.MainActivity
import com.example.perjalanan.R
import com.example.perjalanan.ui.components.SosAlertData

object SosNotificationHelper {
    const val CHANNEL_ID = "sos_emergency_channel_v1"
    const val CHANNEL_NAME = "Sinyal Darurat SOS"
    const val NOTIFICATION_ID = 9119

    const val EXTRA_NAVIGATE_TO = "extra_navigate_to"
    const val EXTRA_SOS_ID = "extra_sos_id"
    const val EXTRA_VICTIM_NAME = "extra_victim_name"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifikasi pop-up prioritas tinggi ketika ada sinyal darurat SOS masuk"
                enableLights(true)
                lightColor = android.graphics.Color.RED
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 200, 500, 200, 500)
                setSound(soundUri, audioAttributes)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Menampilkan notifikasi pop-up (Heads-Up Notification).
     * Akan muncul di atas layar baik saat membuka aplikasi lain,
     * maupun saat layar sedang menyala.
     */
    fun showEmergencyHeadsUpNotification(
        context: Context,
        data: SosAlertData,
        isPoliceRole: Boolean = false
    ) {
        createNotificationChannel(context)

        // Target tujuan saat tombol "Terima & Lihat Lokasi" diklik
        val targetRoute = if (isPoliceRole) "police_map" else "map"

        val actionIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_NAVIGATE_TO, targetRoute)
            putExtra(EXTRA_SOS_ID, data.id)
            putExtra(EXTRA_VICTIM_NAME, data.victimName)
        }

        val pendingActionIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notifTitle = if (isPoliceRole) {
            "🚨 Laporan Darurat Baru Masuk (${data.distanceText})"
        } else {
            "🚨 Bantuan Diperlukan di Dekat Anda! (${data.distanceText})"
        }

        val notifBigText = if (isPoliceRole) {
            "${data.victimName} memicu sinyal darurat di ${data.locationAddress}.\nJarak: ${data.distanceText} • Detak Jantung: ${data.heartRate} bpm (Kritis)"
        } else {
            "${data.victimName} berada di dekat Anda (${data.distanceText}) dan membutuhkan pertolongan cepat di ${data.locationAddress}.\nDetak jantung terdeteksi kritis (${data.heartRate} bpm)."
        }

        val actionBtnText = if (isPoliceRole) "TERIMA & LIHAT LOKASI" else "BANTU & LIHAT PETA"

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_sos_warning)
            .setContentTitle(notifTitle)
            .setContentText("${data.victimName} · ${data.locationAddress}")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(notifBigText)
            )
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setVibrate(longArrayOf(0, 500, 200, 500, 200, 500))
            .setContentIntent(pendingActionIntent)
            .setFullScreenIntent(pendingActionIntent, true) // Muncul di lockscreen
            .addAction(
                R.drawable.ic_sos_location,
                actionBtnText,
                pendingActionIntent
            )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun cancelEmergencyNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
    }
}
