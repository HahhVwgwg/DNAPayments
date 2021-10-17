import com.dnapayments.data.repository.MainRepository
import com.dnapayments.data.repository.LoginRepository
import org.koin.dsl.module

val repoModule = module {
    single { MainRepository(get()) }
    single { LoginRepository(get()) }
}
