import com.dnapayments.data.repository.CharacterDetailedRepository
import com.dnapayments.data.repository.StoriesRepository
import com.dnapayments.data.repository.ZhyrauRepository
import org.koin.dsl.module

val repoModule = module {
    single { ZhyrauRepository() }
    single { StoriesRepository(get()) }
    single { CharacterDetailedRepository(get()) }
}
