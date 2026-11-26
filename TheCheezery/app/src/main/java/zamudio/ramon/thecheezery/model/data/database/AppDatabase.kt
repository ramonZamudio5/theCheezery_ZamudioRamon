package zamudio.ramon.thecheezery.model.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zamudio.ramon.thecheezery.model.data.database.dao.ComboDao
import zamudio.ramon.thecheezery.model.data.database.dao.ProductComboDao
import zamudio.ramon.thecheezery.model.data.database.dao.ProductDao
import zamudio.ramon.thecheezery.model.data.database.entity.ComboEntity
import zamudio.ramon.thecheezery.model.data.database.entity.ProductComboEntity
import zamudio.ramon.thecheezery.model.data.database.entity.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        ComboEntity::class,
        ProductComboEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun comboDao(): ComboDao
    abstract fun productComboDao(): ProductComboDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cheezery.db"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
