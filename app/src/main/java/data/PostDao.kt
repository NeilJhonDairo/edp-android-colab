package data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY created_at DESC")
    fun observeAll(): Flow<List<Post>>

    @Insert
    suspend fun insert(post: Post): Long

    @Update
    suspend fun update(post: Post)

    @Delete
    suspend fun delete(post: Post)
}