#!/bin/sh

# 환경 변수를 치환하여 Nginx 설정 파일 생성
envsubst '${DOMAIN}' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf

# Nginx 설정 파일 확인 (디버깅용)
echo "Nginx configuration:"
cat /etc/nginx/conf.d/default.conf


echo "check ssl:"
ls -l /etc/letsencrypt/live
ls -l /etc/letsencrypt/live/${DOMAIN}
# Nginx 시작
exec nginx -g 'daemon off;'
