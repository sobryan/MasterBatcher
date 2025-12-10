plugins {
    java
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.10.4"
}

group = "com.example"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("com.ibm.db2:jcc:11.5.9.0")
}

graalvmNative {
    binaries {
        named("main") {
            imageName = "masterbatcher"
            mainClass = "com.example.masterbatcher.MasterBatcherApplication"
        }
    }
}
