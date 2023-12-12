package com.moneybox.minimb.extensions

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore("money-box-prefs")
