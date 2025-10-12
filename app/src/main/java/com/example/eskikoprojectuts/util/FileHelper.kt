package com.example.eskikoprojectuts.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileHelper (val context: Context) {
    val folderName = "myfolder"
    val fileName = "mydata.txt"

    fun getFile(): File {
        val dir = File(context.filesDir, folderName)
        if(!dir.exists()) {
            dir.mkdirs() // makes directory
        }

        return File(dir, fileName)
    }

    fun writeToFile(data:String){
        try {
            val file = getFile()
            // append = true -> data baru dilanjutkan
            // append = false  -> data baru mereplace data lama
            FileOutputStream(file, true).use {
                    output -> output.write((data + "\n").toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun readFromFile():String {
        try {
            val file = getFile()
            return file.bufferedReader().useLines { lines -> lines.joinToString ( "\n" ) }
        } catch (e: IOException) {
            return e.printStackTrace().toString()
        }
    }

    fun deleteFile(): Boolean {
        return getFile().delete()
    }

    fun getFilePath():String {
        return getFile().absolutePath
    }
}