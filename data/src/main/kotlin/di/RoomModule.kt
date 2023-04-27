package di

import androidx.room.Room
import database.LocalDatabase
import database.dao.AnswersDao
import database.dao.CategoryDao
import database.dao.LastUpdateDao
import database.dao.QuestionsDao
import database.dao.SubcategoryDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val dbName = "AndroidGymDatabase.db"

internal val roomModule = module {
    single<LocalDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            LocalDatabase::class.java,
            dbName
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single<CategoryDao> {
        get<LocalDatabase>().category()
    }

    single<LastUpdateDao> { get<LocalDatabase>().lastUpdate() }

    single<SubcategoryDao> { get<LocalDatabase>().subcategories() }

    single<QuestionsDao> { get<LocalDatabase>().questions() }

    single<AnswersDao> { get<LocalDatabase>().answers() }
}