package com.sigmotoa.roverphotos.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.sigmotoa.roverphotos.data.remote.Rover

/**
 *
 * Created by sigmotoa on 8/10/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */
@Database(entities = [LocalPhoto::class], version = 1)
abstract class PhotosDatabase: RoomDatabase() {
    abstract fun photosDao():PhotosDao
}

@Dao
interface PhotosDao {

    @Query("SELECT * FROM LocalPhoto")
    suspend fun getLikedPhotos():List<LocalPhoto>

    @Insert
    suspend fun insertPhoto(photo: LocalPhoto)

    @Update
    suspend fun updateLikedPhoto(photo: LocalPhoto)

    @Query("SELECT COUNT(*) FROM LocalPhoto")
    suspend fun couunt(): Int
}

@Entity
data class LocalPhoto(
    @PrimaryKey val id: Int,
    val roverName: String,
    val cameraName: String,
    val marsDate: Int,
    val earthDate: String,
    val imgSrc: String,
    val like: Boolean
)