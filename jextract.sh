#!/bin/bash

cd src || exit
jextract --source -t org.distance -ldistance__m256d \
                  -I /usr/local/include /usr/local/include/distance__m256d.h \
                  --include-function euclidean \
                  --include-function manhattan \
                  --include-function cosine \
                  --include-function aligned_alloc \
                  --dump-includes=includes.txt

cd ..