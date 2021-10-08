import com.dnapayments.data.repository.AuthorizationRepository
import com.dnapayments.data.repository.CharacterDetailedRepository
import com.dnapayments.data.repository.CharacterRepository
import com.dnapayments.data.repository.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single { CharacterRepository(get()) }
    single { CharacterDetailedRepository(get()) }
    single { AuthorizationRepository(get()) }
    single { MainRepository(get()) }
}
