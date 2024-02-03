#!/bin/bash

sudo ./gradlew clean bootjar

sudo docker build  --platform  linux/amd64 --no-cache  -t suker800/bukki .
sudo docker push suker800/bukki