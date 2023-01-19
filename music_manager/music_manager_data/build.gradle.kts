apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.coreData))
    "implementation"(project(Modules.musicManagerDomain))

    "implementation"(Retrofit.okHttp)
    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.okHttpLoggingInterceptor)
    "implementation"(Retrofit.moshiConverter)

    "kapt"(Room.roomCompiler)
    "implementation"(Room.roomKtx)
    "implementation"(Room.roomRuntime)
}