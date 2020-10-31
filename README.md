# Eki Lib <br>  
A minecraft library provides modern station system.  
### features
* **Station System** -  
*Don't have a proper way to maintain your railway system? <br> Try this one! This system will define a **working** station to handle several service in you **real** block stsation.*  
  
* **Ticket System** -  
*Having trouble to keep up railway without fine income? <br> Don't worry! Now you can have sell ticket and force them to pass the ticket gate!*  
### Adding Eki Lib to your project
Firstly, Eki Lib doesn't have a maven repo. Instead, you can use CurseMaven to include the library.

Step 1: Add the following code to your build.gradle without modified any other setting from ForgeGradle
```
repositories {
    maven {
        url = "https://www.cursemaven.com"
    }
}
```

Step 2: Add target version of Eki Lib, see [cursemaven](https://www.cursemaven.com/) for more information.
The following example will add [this Eki Lib file](https://www.curseforge.com/minecraft/mc-mods/eki-lib/files/3089443) to your project.
```
dependencies {
    deobfCompile "curse.maven:eki-lib:3089443"
}
```

Step 3: Refresh your gradle, after it's done, you can now code with Eki Lib!