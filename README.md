# spun [![](https://jitpack.io/v/user11681/spun.svg)](https://jitpack.io/#user11681/spun)
a mostly unencapsulated "unsafe" Fabric GUI library

### using
[wiki](https://github.com/user11681/spun/wiki)

### including with Gradle
If you don't have a top-level `repositories` block, then make such a block. ***Do not use the*** `publishing` ***block.***
```groovy
repositories {
    // . . .
    maven {
        url "https://jitpack.io"
    }
}
```

to include spun in a development environment:
```groovy
dependencies {
    // . . .
    modImplementation "com.github.user11681:spun:1.16-SNAPSHOT"
}
```

to include spun in your mod's JAR file:
```groovy
dependencies {
    // . . .
    include "com.github.user11681:spun:1.16-SNAPSHOT"
}
```

to combine both of the above:
```groovy
dependencies {
    // . . .
    modImplementation include("com.github.user11681:spun:1.16-SNAPSHOT")
}
```
