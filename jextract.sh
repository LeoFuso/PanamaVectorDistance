#!/bin/bash

cd src || exit
jextract --source -t org.distance -ldistance__m256d -I /usr/local/include /usr/local/include/distance__m256d.h
cd ..