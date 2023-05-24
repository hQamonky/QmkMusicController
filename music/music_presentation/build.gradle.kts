apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.coreDomain))
    "implementation"(project(Modules.corePresentation))
    "implementation"(project(Modules.channelPresentation))
    "implementation"(project(Modules.musicManagerDomain))
    "implementation"(project(Modules.namingRulePresentation))
    "implementation"(project(Modules.settingPresentation))

}