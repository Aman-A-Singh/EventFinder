package com.ticket.master.eventfinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ticket.master.eventfinder.util.Constants.EVENT_TABLE_NAME


/**
 * This class is resposible for creating the database in the application
 * Only one instance of this class should be created throughout the application
 * If the object of this class is already created then only that object should be accessible throughout the application
 */
@Database(entities = [EventEntity::class], version = 1, exportSchema = false)
abstract class EventEntityDatabase : RoomDatabase() {
    abstract fun eventEntityDao(): EventDao

    /**
     * This creates only one instance of the class throughout the class
     */
    companion object {

        @Volatile
        private var INSTANCE: EventEntityDatabase? = null

        /**
         * @return ExpenseDatabase Object.
         * Returns only the single instance of ExpenseDatabase
         * if the instance is already created it returns the reference of same object
         */
        fun getInstance(context: Context): EventEntityDatabase {

            //synchronized is used so that no two threads can create the instance of class
            // (in short same operation should not be carried out twice at any given time)

            return INSTANCE ?: synchronized(this) {//Checks if the INSTANCE is null
                //Executes only if the INSTANCE is null else return the INSTANCE value
                val instance = Room.databaseBuilder(
                    context,
                    EventEntityDatabase::class.java,
                    EVENT_TABLE_NAME
                )
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}