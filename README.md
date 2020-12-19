# Eki Lib <br>  
A minecraft library provides modern station system.  
### features
* **Station System** -  
*Don't have a proper way to maintain your railway system? <br> Try this one! This system will define a **working** station to handle several service in you **real** block stsation.*  
  
* **Ticket System** -  
*Having trouble to keep up railway without fine income? <br> Don't worry! Now you can have sell ticket and force them to pass the ticket gate!*  
### Adding Eki Lib to your project
Firstly, Eki Lib doesn't have a maven repo. Instead, you can use JitPack to include the library in your mod.

Step 1: Add the following code to your build.gradle without modified any other setting from ForgeGradle
```
repositories {
    maven { url 'https://jitpack.io' }
}
```

Step 2: Add target version of Eki Lib, see Releases in [Eki Lib]("https://github.com/ChAoSUnItY/EkiLib") for versions.
```
dependencies {
    compile fg.deobf('com.github.ChAoSUnItY:EkiLib:EKI_LIB_VERSION_HERE')
}
```

Step 3: Refresh your gradle, after it's done, you can now code with Eki Lib!