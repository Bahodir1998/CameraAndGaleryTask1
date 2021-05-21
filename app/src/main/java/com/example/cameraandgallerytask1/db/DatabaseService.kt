package com.example.cameraandgallerytask1.db

import com.example.cameraandgallerytask1.models.Sign

interface DatabaseService {

    fun addSign(sign: Sign)

    fun deleteSign(sign: Sign)

    fun deleteSignById(id: Int)

    fun getAllSignByType(type: Int): List<Sign>

    fun getAllSignByLike(like: Int): List<Sign>

    fun getSignById(id: Int): Sign

    fun updateSign(sign: Sign): Int
}