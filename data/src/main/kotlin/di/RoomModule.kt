package di

import androidx.room.Room
import database.LocalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val dbName = "AndroidGymDatabase.db"

internal val roomModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            LocalDatabase::class.java,
            dbName
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        val database = get<LocalDatabase>()
        database.category()
    }
}