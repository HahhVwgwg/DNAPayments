import com.dnapayments.data.repository.CharacterDetailedRepository
import com.dnapayments.data.repository.CharacterRepository
import org.koin.dsl.module

val repoModule = module {
    single { CharacterRepository(get()) }
    single { CharacterDetailedRepository(get()) }
}
