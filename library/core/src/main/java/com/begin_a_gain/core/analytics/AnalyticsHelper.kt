package com.begin_a_gain.core.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsHelper @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) {
    fun logEvent(event: AnalyticsEvent) {
        val bundle = Bundle().apply {
            event.params.forEach { (key, value) -> putString(key, value) }
        }
        firebaseAnalytics.logEvent(event.name, bundle)
    }
}