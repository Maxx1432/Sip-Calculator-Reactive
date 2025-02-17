package com.sip_calculator.Sip_Calculator.Configuration

import com.sip_calculator.Sip_Calculator.Util.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.IOException


@Component
class DownloadConfig @Autowired constructor(private val fileUtils: FileUtils) {
    private val log : Logger = LoggerFactory.getLogger(DownloadConfig::class.java)

    fun createDownloadDirectory() {
        fileUtils.setStorageLocation()
            .doOnError { e -> log.error("Exception while creating directory {}", e.message) }
            .subscribe()
    }

}