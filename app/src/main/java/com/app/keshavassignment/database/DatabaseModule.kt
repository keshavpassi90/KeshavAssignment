package com.app.keshavassignment.database



import android.content.Context
import androidx.room.Room
import com.app.keshavassignment.database.AppDatabase
import com.app.keshavassignment.database.InventoryDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    // AppModule.kt

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "analytics_db"
        )
            // ⭐ This line tells Room to wipe out all old data and recreate the DB
            // with the new schema (version 2) when a mismatch is detected.
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideInventoryDao(database: AppDatabase): InventoryDao {
        return database.inventoryDao()
    }
}
