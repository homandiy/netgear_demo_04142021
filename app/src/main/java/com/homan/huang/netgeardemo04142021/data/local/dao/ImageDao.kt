package com.homan.huang.netgeardemo04142021.data.local.dao

import androidx.room.*
import com.homan.huang.netgeardemo04142021.data.local.entity.ImageItem

@Dao
interface ImageDao {

    // Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageItem: ImageItem)

    // Delete
    @Delete
    suspend fun delete(imageItem: ImageItem)

    // Delete all
    @Query("DELETE FROM image_items")
    suspend fun deleteAll()

    // count total images
    @Query("SELECT COUNT(*) FROM image_items")
    suspend fun countCategory(): Int

    // Get images in same category
    @Query("SELECT * FROM image_items WHERE code = :code")
    suspend fun getImageItem(code: String): ImageItem

}