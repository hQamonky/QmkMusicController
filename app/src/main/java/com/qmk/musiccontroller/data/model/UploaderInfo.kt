package com.qmk.musiccontroller.data.model

data class UploaderInfo(
    val id: String,
    val name: String,
    val namingFormat: NamingFormatInfo = NamingFormatInfo()
)