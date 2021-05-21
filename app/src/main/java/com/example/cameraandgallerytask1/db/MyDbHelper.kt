package com.example.cameraandgallerytask1.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cameraandgallerytask1.models.Sign
import com.example.cameraandgallerytask1.utils.Constants

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, Constants.DB_NAME,null,Constants.DB_VERSION),
    DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table ${Constants.TABLE_NAME}(${Constants.ID} integer not null primary key autoincrement unique,${Constants.NAME} text not null,${Constants.CONTENT} text not null,${Constants.TYPE} integer not null,${Constants.IMAGE_PATH} text not null,${Constants.LIKE} integer not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists ${Constants.TABLE_NAME}")
        onCreate(db)
    }

    override fun addSign(sign: Sign) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.NAME,sign.name)
        contentValues.put(Constants.CONTENT,sign.content)
        contentValues.put(Constants.TYPE,sign.type)
        contentValues.put(Constants.IMAGE_PATH,sign.imagePath)
        contentValues.put(Constants.LIKE,sign.like)
        database.insert(Constants.TABLE_NAME,null,contentValues)
        database.close()
    }

    override fun deleteSign(sign: Sign) {
        val database = this.writableDatabase
        database.delete(Constants.TABLE_NAME, "${ Constants.ID } = ?", arrayOf("${sign.id}"))
        database.close()
    }

    override fun deleteSignById(id: Int) {
        val database = this.writableDatabase
        database.delete(Constants.TABLE_NAME, "${ Constants.ID } = ?", arrayOf("$id"))
        database.close()
    }

    override fun getAllSignByType(type: Int): ArrayList<Sign> {
        val list = ArrayList<Sign>()
        val query = "select * from ${Constants.TABLE_NAME} where ${Constants.TYPE}=$type"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query,null)
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val content = cursor.getString(2)
                val type = cursor.getInt(3)
                val imagePath = cursor.getString(4)
                val like = cursor.getInt(5)
                val sign = Sign(id, name, content, type, imagePath, like)
                list.add(sign)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllSignByLike(like: Int): ArrayList<Sign> {
        val list = ArrayList<Sign>()
        val query = "select * from ${Constants.TABLE_NAME} where ${Constants.LIKE}=$like"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query,null)
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val content = cursor.getString(2)
                val type = cursor.getInt(3)
                val imagePath = cursor.getString(4)
                val like = cursor.getInt(5)
                val sign = Sign(id, name, content, type, imagePath, like)
                list.add(sign)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getSignById(id: Int): Sign {
        val database = this.readableDatabase
        val cursor = database.query(
            Constants.TABLE_NAME,
            arrayOf(Constants.ID,Constants.NAME,Constants.CONTENT,Constants.TYPE,Constants.IMAGE_PATH,Constants.LIKE),
            "${Constants.ID} = ?",
            arrayOf(id.toString()),
            null,null,null
        )
        cursor?.moveToFirst()
        return Sign(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getInt(5))
    }

    override fun updateSign(sign: Sign): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.ID,sign.id)
        contentValues.put(Constants.NAME,sign.name)
        contentValues.put(Constants.CONTENT,sign.content)
        contentValues.put(Constants.TYPE,sign.type)
        contentValues.put(Constants.IMAGE_PATH,sign.imagePath)
        contentValues.put(Constants.LIKE,sign.like)
        return database.update(Constants.TABLE_NAME,contentValues,
            "${Constants.ID} = ?",
            arrayOf(sign.id.toString())
        )
    }


}