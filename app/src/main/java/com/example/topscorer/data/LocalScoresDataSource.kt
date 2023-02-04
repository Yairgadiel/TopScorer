package com.example.topscorer.data

import android.content.res.AssetManager
import androidx.annotation.WorkerThread
import com.example.topscorer.data.model.GameList
import com.example.topscorer.di.Assets
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.Charset
import javax.inject.Inject

private const val SCORES_FILE = "scores.json"

class LocalScoresDataSource @Inject constructor(@Assets private val assetManager: AssetManager) {

    @WorkerThread
    fun getGames() : GameList? {
        return try {
            // create Gson instance
            val gson = Gson()
            val jsonFileString = getJsonFromAssets(SCORES_FILE)

            if (jsonFileString == null) {
                null
            }
            else {
                val gameListType: Type = object : TypeToken<GameList>() {}.type
                val gameList: GameList = gson.fromJson(jsonFileString, gameListType)

                gameList
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    @WorkerThread
    private fun getJsonFromAssets(fileName: String): String? {
        val jsonString: String = try {
            val inputStream = assetManager.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.defaultCharset())
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}