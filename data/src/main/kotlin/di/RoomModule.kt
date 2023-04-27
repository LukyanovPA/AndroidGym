package di

import androidx.room.Room
import database.LocalDatabase
import database.dao.CategoryDao
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
}