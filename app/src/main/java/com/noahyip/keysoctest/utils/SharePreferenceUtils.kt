package com.noahyip.keysoctest.utils

import android.content.Context
import com.google.gson.Gson
import com.noahyip.keysoctest.model.ITuneSearchResult


class SharePreferenceUtils {

    companion object{
        val FAVOURITE_SHARE_PREFERENCE = "favouriteSharePreference"

        private fun put(context: Context, preference: String, key: String, value: String) {
            val sharedPreference = context.getSharedPreferences(preference, Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString(key, value)
            editor.apply()
            //editor.commit
        }

        private fun remove(context: Context, preference: String, key: String) {
            val sharedPreference = context.getSharedPreferences(preference, Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.remove(key)
            editor.apply()
            //editor.commit
        }

        fun addFavourite(context: Context, song: ITuneSearchResult) {
            val gson = Gson()
            val json: String = gson.toJson(song)
            put(context, FAVOURITE_SHARE_PREFERENCE, song.trackId.toString(), json)
        }

        fun removeFavourite(context: Context, key: String) {
            remove(context, FAVOURITE_SHARE_PREFERENCE, key)
        }


        fun getAllFavouriteKey(context: Context): MutableSet<String>{
            val sharedPreference = context.getSharedPreferences(FAVOURITE_SHARE_PREFERENCE, Context.MODE_PRIVATE)
            return sharedPreference.all.keys
        }

        fun getAllFavourite(context: Context): MutableMap<String, *>? {
            val sharedPreference = context.getSharedPreferences(FAVOURITE_SHARE_PREFERENCE, Context.MODE_PRIVATE)
            return sharedPreference.all
        }
    }
}