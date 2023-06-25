package com.projects.techvibe.admin.file_extractor

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@Service
class FileExtractorService {

    companion object {
        private val logger = LoggerFactory.getLogger(FileExtractorService::class.java)
    }

    fun extractCSVFile(csvFileStream: InputStream): List<String> {
        val inputStreamReader = InputStreamReader(csvFileStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        return bufferedReader.readLines()
    }
}