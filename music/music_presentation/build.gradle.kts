apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.coreDomain))
    "implementation"(project(Modules.corePresentation))
    "implementation"(project(Modules.musicManagerDomain))
    "implementation"(project(Modules.settingPresentation))

}