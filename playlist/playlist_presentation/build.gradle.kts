apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.corePresentation))
    "implementation"(project(Modules.musicManagerDomain))

    "implementation"(Coil.coilCompose)
}