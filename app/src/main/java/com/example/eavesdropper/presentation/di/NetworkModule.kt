package com.example.eavesdropper.presentation.di

import android.content.Context
import com.example.eavesdropper.data.local.preferences.PreferencesRepository
import com.example.eavesdropper.data.remote.api.CloudinaryApi
import com.example.eavesdropper.data.remote.api.GigaChatApi
import com.example.eavesdropper.data.remote.api.GigaChatAuthApi
import com.example.eavesdropper.data.remote.model.gigachat.GigaChatAuthInterceptor
import com.example.eavesdropper.data.remote.api.OpenAiApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    @Named("gigachat_client")
    fun provideGigaChatClient(
        logging: HttpLoggingInterceptor,
        authInterceptor: GigaChatAuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("openai_client")
    fun provideOpenAiClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer кукиш с соусом")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    @Named("cloudinary")
    fun provideRetrofitCloudinary(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.cloudinary.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("gigachat")
    fun provideRetrofitGigaChat(@Named("gigachat_client") client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://gigachat.devices.sberbank.ru/api/v1/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideCloudinaryApi(@Named("cloudinary") retrofit: Retrofit): CloudinaryApi =
        retrofit.create(CloudinaryApi::class.java)


    @Provides
    @Singleton
    fun provideGigaChatApi(@Named("gigachat") retrofit: Retrofit): GigaChatApi =
        retrofit.create(GigaChatApi::class.java)

    @Provides
    @Singleton
    @Named("gigachat_auth")
    fun provideAuthRetrofit(): Retrofit {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://ngw.devices.sberbank.ru:9443/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideGigaChatAuthApi(
        @Named("gigachat_auth") retrofit: Retrofit
    ): GigaChatAuthApi =
        retrofit.create(GigaChatAuthApi::class.java)


    @Module
    @InstallIn(SingletonComponent::class)
    object PreferencesModule {

        @Provides
        @Singleton
        fun providePreferencesRepository(
            @ApplicationContext context: Context
        ): PreferencesRepository {
            return PreferencesRepository(context)
        }
    }

    @Provides
    @Singleton
    @Named("openai")
    fun provideRetrofitOpenAi(@Named("openai_client") client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/v1/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenAiApi(@Named("openai") retrofit: Retrofit): OpenAiApi =
        retrofit.create(OpenAiApi::class.java)
}



