//import com.readystatesoftware.chuck.ChuckInterceptor
import com.dnapayments.api_clients.ICharacterDetailsService
import com.dnapayments.api_clients.ICharacterListService
import com.dnapayments.api_clients.ILoginService
import com.dnapayments.api_clients.IMainService
import com.dnapayments.utils.Constants
import com.dnapayments.utils.PrefsAuth
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { getOkHttpClient(get()) }
    single { getRetrofit(get()) }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(IMainService::class.java) as IMainService
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ILoginService::class.java) as ILoginService
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ICharacterDetailsService::class.java) as ICharacterDetailsService
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ICharacterListService::class.java) as ICharacterListService
    }
}

fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
}

fun getOkHttpClient(prefsAuth: PrefsAuth): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return Builder()
        .addInterceptor(interceptor)
        .addInterceptor {
            val requestBuilder = it.request().newBuilder()
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Authorization", prefsAuth.getAccessToken() ?: "")
            it.proceed(requestBuilder.build())
        }
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .build()
}
