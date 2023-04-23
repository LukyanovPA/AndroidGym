package di

import org.koin.dsl.module
import repository.QuestionRepository
import repository.impl.QuestionRepositoryImpl

internal val repositoryModule = module {
    single<QuestionRepository> { QuestionRepositoryImpl(get(), get(), get()) }
}