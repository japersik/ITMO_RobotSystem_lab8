package com.itmo.r3135.ViewClient.controller

import tornadofx.*
import java.util.*

class LocalizationManager : Controller() {
    private lateinit var locale: Locale
    private lateinit var buttons: ResourceBundle
    private lateinit var titles: ResourceBundle


    init {
        setLocale(LocaleString.LANGUAGE_RU.stringLocale)
    }

    fun setLocale(s: String) {
        when (s) {
            LocaleString.LANGUAGE_ES.stringLocale -> locale = Locale(LocaleString.LANGUAGE_ES.stringLocale, LocaleString.COUNTRY_HN.stringLocale)
            LocaleString.LANGUAGE_FI.stringLocale -> locale = Locale(LocaleString.LANGUAGE_FI.stringLocale, LocaleString.COUNTRY_FI.stringLocale)
            LocaleString.LANGUAGE_FR.stringLocale -> locale = Locale(LocaleString.LANGUAGE_FR.stringLocale, LocaleString.COUNTRY_FR.stringLocale)
            else -> locale = Locale(LocaleString.LANGUAGE_RU.stringLocale, LocaleString.COUNTRY_RU.stringLocale)
        }
        changeLanguage()
    }

    private fun changeLanguage() {
        buttons = ResourceBundle.getBundle("buttons", locale)
        titles = ResourceBundle.getBundle("titles", locale)
    }

    fun getNativeButton(button: LocaleString): String {
        return buttons.getString(button.stringLocale)
    }

    fun getNativeTitle(title: LocaleString): String {
        return titles.getString(title.stringLocale)
    }
}