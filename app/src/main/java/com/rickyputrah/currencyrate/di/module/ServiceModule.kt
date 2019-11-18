package com.rickyputrah.currencyrate.di.module

import com.rickyputrah.currencyrate.BuildConfig
import com.rickyputrah.currencyrate.network.CURRENCY_RATE_BASE_URL
import com.rickyputrah.currencyrate.network.CurrencyRateApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
class ServiceModule {

    @Singleton
    @Provides
    internal fun provideRetrofitInterface(): Retrofit {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
            httpClient.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl(CURRENCY_RATE_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(httpClient.build())
            .build()
    }

    @Singleton
    @Provides
    internal fun provideCurrencyRateApi(retrofit: Retrofit): CurrencyRateApi {
        return retrofit.create(CurrencyRateApi::class.java)
    }
}