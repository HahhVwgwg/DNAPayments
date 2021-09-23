//import com.readystatesoftware.chuck.ChuckInterceptor
import android.content.Context
import com.dnapayments.api_clients.ICharacterDetailsService
import com.dnapayments.api_clients.ICharacterListService
import com.dnapayments.utils.Constants
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { getOkHttpClient(androidContext()) }
    single { getRetrofit(get()) }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(ICharacterListService::class.java) as ICharacterListService
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ICharacterDetailsService::class.java) as ICharacterDetailsService
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

fun getOkHttpClient(context: Context): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return Builder()
        .addInterceptor(interceptor)
//          .addInterceptor(ChuckInterceptor(context))
        .addInterceptor {
            val requestBuilder = it.request().newBuilder()
//                .addHeader("X-Kmf-Access-Token", KMFVars.userData.accessToken)
                .addHeader("platform", "ANDROID") // Todo need to make const val
//                .addHeader("lng", LanguageProvider.APP_LNG)
//                .addHeader("version", BuildConfig.VERSION_NAME)
            it.proceed(requestBuilder.build())
        }
        .connectTimeout(40, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .build()
}
