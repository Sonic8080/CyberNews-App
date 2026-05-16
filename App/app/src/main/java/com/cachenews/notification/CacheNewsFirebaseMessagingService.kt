package com.cachenews.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CacheNewsFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // Handle FCM messages if sent from server
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Upload token to server if needed
    }
}
