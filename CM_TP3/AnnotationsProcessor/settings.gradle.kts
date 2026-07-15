pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "GreetingProcessorProject"

include("annotations")
include("app")
include("processor")
include("cmtp1")