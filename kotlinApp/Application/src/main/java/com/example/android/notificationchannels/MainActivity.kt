/*
* Copyright 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.notificationchannels

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Display main screen for sample. Displays controls for sending test notifications.
 */
class MainActivity : Activity() {

    private val ui by lazy { MainUi(activity_main) }

    /*
     * Class for managing notifications
     */
    private val helper by lazy { NotificationHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ui
        helper
    }

    /**
     * Send activity notifications.

     * @param id The ID of the notification to create
     * *
     * @param title The title of the notification
     */
    fun sendNotification(id: Int, title: String) {
        val notification = when (id) {
            NOTI_PRIMARY1 -> helper.getNotification1(title, getString(R.string.primary1_body))
            NOTI_PRIMARY2 -> helper.getNotification1(title, getString(R.string.primary2_body))
            NOTI_SECONDARY1 -> helper.getNotification2(title, getString(R.string.secondary1_body))
            NOTI_SECONDARY2 -> helper.getNotification2(title, getString(R.string.secondary2_body))
        }
        helper.notify(id, notification)
    }

    /**
     * Send Intent to load system Notification Settings for this app.
     */
    fun goToNotificationSettings() {
        val i = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        i.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        startActivity(i)
    }

    /**
     * Send intent to load system Notification Settings UI for a particular channel.

     * @param channel Name of channel to configure
     */
    fun goToNotificationSettings(channel: String) {
        val i = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        i.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel)
        startActivity(i)
    }

    /**
     * View model for interacting with Activity UI elements. (Keeps core logic for sample
     * seperate.)
     */
    internal inner class MainUi(root: View) {

        init {
            main_primary_send1.setOnClickListener { sendNotification(NOTI_PRIMARY1, titlePrimaryText) }
            main_primary_send2.setOnClickListener { sendNotification(NOTI_PRIMARY2, titlePrimaryText) }
            main_primary_config.setOnClickListener { goToNotificationSettings(NotificationHelper.PRIMARY_CHANNEL) }

            main_secondary_send1.setOnClickListener { sendNotification(NOTI_SECONDARY1, titleSecondaryText) }
            main_secondary_send2.setOnClickListener { sendNotification(NOTI_SECONDARY2, titleSecondaryText) }
            main_secondary_config.setOnClickListener { goToNotificationSettings(NotificationHelper.SECONDARY_CHANNEL) }

            btnA.setOnClickListener { goToNotificationSettings() }
        }

        private val titlePrimaryText: String
            get() = main_primary_title?.text?.toString() ?: ""

        private val titleSecondaryText: String
            get() = if (main_primary_title != null) main_secondary_title.text.toString() else ""
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private val NOTI_PRIMARY1 = 1100
        private val NOTI_PRIMARY2 = 1101
        private val NOTI_SECONDARY1 = 1200
        private val NOTI_SECONDARY2 = 1201
    }
}
