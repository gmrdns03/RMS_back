#!/bin/bash

# 깃 풀 받기
git pull origin main

# test_back 컨테이너가 존재하는지 확인
if docker ps -a | grep -q "rms_back"; then
    docker rm -f rms_back
    echo "컨테이너 삭제 완료"
fi

# test:back 이미지가 존재하는지 확인
if docker images | grep -q "rms_back"; then
	docker rmi rms_back:test
	echo "이미지 삭제 완료"
fi

docker build -t rms_back:test .
echo "이미지 빌드 완료"

docker run -itd -p 8088:8088 -v /home/side/back/RMS_back/files:/app/RMS_back/build/libs/resources/main/files --env-file ./.env --name rms_back rms_back:test
echo "컨테이너 생성 완료"
