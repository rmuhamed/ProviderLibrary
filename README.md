# ProviderLib

## Requirements
Have maven installed in your computer 

## Deploy
Use the following command, gradle (or using wrapper,i.e ./gradlew) clean publishToMavenLocal

## Usage
Check first if the .aar is inside your .m2 directory.
Second, in order to use this library in a client app, and **mavenLocal**
in your project *build.gradle* file 

```groovy
allprojects { 
    repositories {
        mavenLocal()
        google()
        jcenter()
    }
}
```