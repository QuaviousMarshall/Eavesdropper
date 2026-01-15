package com.example.eavesdropper.presentation

import androidx.lifecycle.ViewModel
import com.example.eavesdropper.domain.entity.ButtonType

class SettingsViewModel : ViewModel() {

    fun onButtonClickedReaction(buttonType: ButtonType) {
        if (buttonType == ButtonType.ABOUTACCOUNT) {
            //TODO интент на активити или фрагмент страницы об аккаунте
        }
        if (buttonType == ButtonType.ABOUTAPP) {
            //TODO интент на активити или фрагмент страницы о пользователе
        }
        if (buttonType == ButtonType.LOGOUTACCOUNT) {
            //TODO интент на активити или фрагмент на страницу входа
        } else {
            throw RuntimeException("Unknow error in SettingsViewModel")
        }
    }
}