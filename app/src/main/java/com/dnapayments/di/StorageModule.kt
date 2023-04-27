import com.dnapayments.data.prefs.PrefsStories
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    //  Storage for token, pin and other user info data
    single { PrefsStories(androidContext()) }
}