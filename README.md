# PanamaVectorDistance

### C Vector Distance Study
### Distance __m256d

I've built a lib to benchmark different distance measures operations using some ___m256d_ vector functions, to compare it with the new Java's Panama Vectors API.

In the upcoming days I'll be creating a few benchmarks using Vanilla Java solutions, the new Vector API and integrating directly with my own lib
using the Foreign accessor (also from Panama).

This project requires that you have the latest JDK 19 incubation with `jextract` available.

In the `src` directly, simple run the following:
```bash
jextract --source -t org.distance -ldistance__m256d -I /usr/local/include /usr/local/include/distance__m256d.h
```

Note that you need to compile the `distance__m256d` lib beforehand.
Optionally you could simply run the `.sh` file located in the Root of this repository.

After compiling, you'll need to pass some arguments to the java program to be able to properly run this benchmark:

```bash
java --enable-native-access=ALL-UNNAMED \
      --add-modules jdk.incubator.foreign \
      -Djava.library.path=/usr/local/lib Main.java [elements-size]
```

Where's element size is an integer.