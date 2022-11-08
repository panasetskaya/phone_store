package com.panasetskaia.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.panasetskaia.core.data.models.PhoneDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM phonedbmodel")
    fun getAllCart(): Flow<List<PhoneDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(dbModel: PhoneDbModel)

    @Query("DELETE FROM phonedbmodel WHERE id=:thisId")
    suspend fun deleteFromCart(thisId: Int)

}