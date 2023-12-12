package com.project.mypokedex

import android.app.Application
import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import com.project.mypokedex.database.PokemonDatabase
import com.project.mypokedex.repository.LanguageRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyPokedexApplication : Application(), ImageLoaderFactory {

    companion object {
        private lateinit var instance: MyPokedexApplication
        private lateinit var database: PokemonDatabase

        fun getInstance(): MyPokedexApplication {
            return instance
        }

        fun getContext(): Context {
            return instance.baseContext
        }

        fun getDatabase(): PokemonDatabase {
            return database
        }
    }

    override fun onCreate() {
        super.onCreate()
        LanguageRepository.initiateLanguage(baseContext)
        instance = this
        database = PokemonDatabase.createDatabase(baseContext)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(SvgDecoder.Factory())
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

}