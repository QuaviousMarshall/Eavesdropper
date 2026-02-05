package com.example.eavesdropper.data.detector

import android.content.Context
import android.media.AudioManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SystemSoundController @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val audioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private var previousVolume: Int = 0

    fun muteSystemSounds() {
        previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM)
        audioManager.adjustStreamVolume(
            AudioManager.STREAM_SYSTEM,
            AudioManager.ADJUST_MUTE,
            0
        )
    }

    fun restoreSystemSounds() {
        audioManager.adjustStreamVolume(
            AudioManager.STREAM_SYSTEM,
            AudioManager.ADJUST_UNMUTE,
            0
        )
        audioManager.setStreamVolume(
            AudioManager.STREAM_SYSTEM,
            previousVolume,
            0
        )
    }
}
