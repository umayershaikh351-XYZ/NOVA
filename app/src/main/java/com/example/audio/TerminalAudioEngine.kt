package com.example.audio

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log

class TerminalAudioEngine(private val context: Context) {

    private var toneGenerator: ToneGenerator? = null
    var isMuted: Boolean = false

    private val vibrator: Vibrator? = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    } catch (e: Exception) {
        null
    }

    init {
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_SYSTEM, 60)
        } catch (e: Exception) {
            Log.w("TerminalAudioEngine", "ToneGenerator init warning: ${e.message}")
        }
    }

    fun playKeypress() {
        if (isMuted) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 14)
            triggerHaptic(12, 60)
        } catch (e: Exception) {
            // Ignore sound dropouts
        }
    }

    fun playBeep() {
        if (isMuted) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 50)
            triggerHaptic(25, 120)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun playWarning() {
        if (isMuted) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_ALERT_AUTOREDIAL_LITE, 120)
            triggerHaptic(60, 200)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun playKillSwitchAlarm() {
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_SUP_ERROR, 250)
            triggerHaptic(150, 255)
        } catch (e: Exception) {
            // Ignore
        }
    }

    private fun triggerHaptic(durationMs: Long, amplitude: Int = 100) {
        try {
            if (vibrator?.hasVibrator() == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val effect = VibrationEffect.createOneShot(
                        durationMs,
                        amplitude.coerceIn(1, 255)
                    )
                    vibrator.vibrate(effect)
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(durationMs)
                }
            }
        } catch (e: Exception) {
            // Safe fallback
        }
    }

    fun release() {
        try {
            toneGenerator?.release()
            toneGenerator = null
        } catch (e: Exception) {
            // Ignore
        }
    }
}
