package app.yog.accessibiilityserviceexample.Services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent


/**
 * Created by sotsys-014 on 4/10/16. modifed and converted by yog
 */

class MyAccessibilityService : AccessibilityService() {
    private val info = AccessibilityServiceInfo()
    private var currntApplicationPackage = ""


    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent")
        val sourcePackageName = accessibilityEvent.packageName as String?
        currntApplicationPackage = sourcePackageName.toString()
        Log.d(TAG, "sourcePackageName:$sourcePackageName")
        Log.d(TAG, "parcelable:" + accessibilityEvent.text.toString())


        /*  if (accessibilityEvent.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION) {
            Log.d(TAGEVENTS, "CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.d(TAGEVENTS, "TYPE_WINDOW_STATE_CHANGED");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_SUBTREE) {
            Log.d(TAGEVENTS, "CONTENT_CHANGE_TYPE_SUBTREE");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_TEXT) {
            Log.d(TAGEVENTS, "CONTENT_CHANGE_TYPE_TEXT");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.INVALID_POSITION) {
            Log.d(TAGEVENTS, "INVALID_POSITION");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED) {
            Log.d(TAGEVENTS, "CONTENT_CHANGE_TYPE_UNDEFINED");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_ANNOUNCEMENT) {
            Log.d(TAGEVENTS, "TYPE_ANNOUNCEMENT");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT) {
            Log.d(TAGEVENTS, "TYPE_ASSIST_READING_CONTEXT");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_GESTURE_DETECTION_END) {
            Log.d(TAGEVENTS, "TYPE_GESTURE_DETECTION_END");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            Log.d(TAGEVENTS, "TYPE_VIEW_CLICKED");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START) {
            Log.d(TAGEVENTS, "TYPE_TOUCH_EXPLORATION_GESTURE_START");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_GESTURE_DETECTION_START) {
            Log.d(TAGEVENTS, "TYPE_GESTURE_DETECTION_START");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED) {
            Log.d(TAGEVENTS, "TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
            Log.d(TAGEVENTS, "TYPE_VIEW_ACCESSIBILITY_FOCUSED");
        }
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOWS_CHANGED) {
            Log.d(TAGEVENTS, "TYPE_WINDOWS_CHANGED");
        }*/

//        if (accessibilityEvent.packageName == null || !(accessibilityEvent.packageName == "com.bsb.hike" || !(accessibilityEvent.packageName == "com.whatsapp" || accessibilityEvent.packageName == "com.facebook.orca" || accessibilityEvent.packageName == "com.twitter.android" || accessibilityEvent.packageName == "com.facebook.katana" || accessibilityEvent.packageName == "com.facebook.lite")))
//            showWindow = false

        if (accessibilityEvent.packageName != null){
            Log.d(TAGEVENTS, "package name : ${packageName}")
        }
        if (accessibilityEvent.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            Log.d(TAGEVENTS, "TYPE_VIEW_TEXT_CHANGED ${accessibilityEvent.text} packagename : ${currntApplicationPackage}")

        } else if (accessibilityEvent.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.d(TAGEVENTS, "TYPE_WINDOW_STATE_CHANGED:" + accessibilityEvent.contentDescription)

        }
    }

    override fun onInterrupt() {

    }

    public override fun onServiceConnected() {
        // Set the type of events that this service wants to listen to.
        //Others won't be passed to this service.
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.notificationTimeout = 100

        this.serviceInfo = info
    }

    companion object {
        private val TAG = "MyAccessibilityService"
        private val TAGEVENTS = "TAGEVENTS"

        /**
         * Check if Accessibility Service is enabled.
         *
         * @param mContext
         * @return `true` if Accessibility Service is ON, otherwise `false`
         */
        fun isAccessibilitySettingsOn(mContext: Context): Boolean {
            var accessibilityEnabled = 0
            //your package /   accesibility service path/class
            val service = "com.accessibilityexample/com.accessibilityexample.Service.MyAccessibilityService"

            val accessibilityFound = false
            try {
                accessibilityEnabled = Settings.Secure.getInt(
                    mContext.applicationContext.contentResolver,
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED)
                Log.v(TAG, "accessibilityEnabled = $accessibilityEnabled")
            } catch (e: Settings.SettingNotFoundException) {
                Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.message)
            }

            val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')

            if (accessibilityEnabled == 1) {
                Log.v(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------")
                val settingValue = Settings.Secure.getString(
                    mContext.applicationContext.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
                if (settingValue != null) {
                    mStringColonSplitter.setString(settingValue)
                    while (mStringColonSplitter.hasNext()) {
                        val accessabilityService = mStringColonSplitter.next()

                        Log.v(TAG, "-------------- > accessabilityService :: $accessabilityService")
                        if (accessabilityService.equals(service, ignoreCase = true)) {
                            Log.v(TAG, "We've found the correct setting - accessibility is switched on!")
                            return true
                        }
                    }
                }
            } else {
                Log.v(TAG, "***ACCESSIBILIY IS DISABLED***")
            }

            return accessibilityFound
        }
    }
}