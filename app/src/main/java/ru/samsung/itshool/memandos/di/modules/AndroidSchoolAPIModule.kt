package ru.samsung.itshool.memandos.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.samsung.itshool.memandos.model.api.AndroidSchoolAuthAPI
import ru.samsung.itshool.memandos.model.api.AndroidSchoolMemesAPI
import javax.inject.Singleton

@Module
class AndroidSchoolAPIModule {

    @Singleton
    @Provides
    fun provideAndroidSchoolMemesAPI(retrofit: Retrofit): AndroidSchoolMemesAPI {
        return retrofit.create(AndroidSchoolMemesAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideAndroidSchoolAuthAPI(retrofit: Retrofit): AndroidSchoolAuthAPI {
        return retrofit.create(AndroidSchoolAuthAPI::class.java)
    }
}