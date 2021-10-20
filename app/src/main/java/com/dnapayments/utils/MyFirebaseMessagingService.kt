package com.dnapayments.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.dnapayments.R
import com.dnapayments.presentation.activity.SplashScreenActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject
import java.util.*


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val prefsAuth: PrefsAuth by inject()


    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    @SuppressLint("HardwareIds")
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        Log.e("device_token", "service $token")
        prefsAuth.saveDeviceId(deviceId)
        prefsAuth.saveAuthToken(token)
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    private fun sendRegistrationToServer(token: String?) {
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }


    companion object {
        private const val TAG = "MyFirebaseMsgService"
        private const val CHANNEL_KMF_NOTIFICATION = "CHANNEL_KMF_NOTIFICATION"
        private const val KMF_NOTIFICATION = "KMF notification"
    }

    private var title = ""
    private var body = ""

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        val intent = Intent(this, SplashScreenActivity::class.java)
        body = remoteMessage.data["message"].toString()
        ////////////       START       ////////////
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random().nextInt(3000)
        /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them.
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            setupChannels(notificationManager)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_foreground)
        val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_KMF_NOTIFICATION)
            .setSmallIcon(R.drawable.ic_tenge_sign)
            .setLargeIcon(largeIcon)
            .setContentTitle("У вас новое уведомление")
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(notificationSoundUri)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        notificationManager.notify(notificationID, notificationBuilder.build())
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(notificationManager: NotificationManager?) {
        val adminChannelName = KMF_NOTIFICATION
        val adminChannelDescription = KMF_NOTIFICATION
        val adminChannel: NotificationChannel?
        adminChannel = NotificationChannel(CHANNEL_KMF_NOTIFICATION,
            adminChannelName,
            NotificationManager.IMPORTANCE_HIGH)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager?.createNotificationChannel(adminChannel)
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */

    private fun showNotification3(title: String, message: String) {
        val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val intent = Intent(this, SplashScreenActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,
            0 /* request code */,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var builder: NotificationCompat.Builder?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel =
                NotificationChannel(CHANNEL_KMF_NOTIFICATION, KMF_NOTIFICATION, importance)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = NotificationCompat.Builder(applicationContext, notificationChannel.getId())
        } else {
            builder = NotificationCompat.Builder(applicationContext)
        }
        builder = builder
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle((title))
            .setTicker(title)
            .setContentText(message)
            .setSound(notificationSoundUri)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        notificationManager.notify(1, builder.build());
    }

    private fun showNotification2(title: String, message: String) {
        val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        createNotificationChannel();
        val intent = Intent(this, SplashScreenActivity::class.java)
        if (title == "link")
            intent.putExtra("link", message)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,
            0 /* request code */,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_KMF_NOTIFICATION)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(notificationSoundUri)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
        startForeground(1, notification);
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_KMF_NOTIFICATION,
                KMF_NOTIFICATION,
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.setShowBadge(true)
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
            // Sets whether notifications posted to this channel should display notification lights
            notificationChannel.enableLights(true)
            // Sets whether notification posted to this channel should vibrate.
            notificationChannel.enableVibration(true)
            // Sets the notification light color for notifications posted to this channel
            notificationChannel.lightColor = Color.GREEN
            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notificationChannel)
        }
    }
}