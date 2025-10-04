package com.app.keshavassignment.database


import android.content.Context
import com.app.keshavassignment.model.AnalyticsModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetsDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    // NOTE: Place your JSON file in the 'app/src/main/assets' directory.
    private val JSON_FILE_NAME = "analytics.json"

    fun loadDataFromJson(): AnalyticsModel? {
        return try {
            val inputStream = context.assets.open(JSON_FILE_NAME)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            gson.fromJson(jsonString, AnalyticsModel::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}