package com.noahyip.keysoctest.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.LocaleList
import java.util.Locale

class LocaleUtils(base: Context) : ContextWrapper(base) {
    companion object {
        fun updateLocale(context: Context, localeToSwitchTo: Locale): LocaleUtils {
            val resources = context.resources
            val configuration = resources.configuration
            val localeList = LocaleList(localeToSwitchTo)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
            val updatedContext = context.createConfigurationContext(configuration)
            return LocaleUtils(updatedContext)
        }
    }
}