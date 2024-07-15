# Node.js의 공식 이미지 사용
FROM node:20-alpine AS build

# 작업 디렉토리 설정
WORKDIR /app

# .env 파일 생성
ARG DOMAIN
RUN echo "VITE_DOMAIN=${DOMAIN}" > .env

# package.json과 package-lock.json 복사
COPY package*.json ./

# 의존성 설치
RUN npm install

# 소스 파일 복사
COPY . .

# 빌드 실행
RUN npm run build

# Nginx 사용하여 빌드된 파일을 서빙
FROM nginx:alpine

# 환경 변수 설정
ARG DOMAIN

# Certbot 설치
RUN apk add --no-cache certbot certbot-nginx openssl

COPY ./ssl/ /etc/letsencrypt/live/${DOMAIN}/
# SSL 인증서 파일 확인
RUN ls -l /etc/letsencrypt/live/${DOMAIN}

COPY --from=build /app/dist /usr/share/nginx/html

# Nginx 설정 파일 복사
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf
COPY ./nginx/default.conf.template /etc/nginx/conf.d/default.conf.template

# Nginx 설정 파일 확인
RUN ls -l /etc/nginx/nginx.conf
RUN ls -l /etc/nginx/conf.d/default.conf.template

# Nginx 포트 설정
EXPOSE 80
EXPOSE 443

# 환경 변수를 치환하고 Nginx를 시작하는 엔트리포인트 스크립트 추가
RUN apk add --no-cache gettext
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh

# 엔트리포인트 스크립트 확인
RUN ls -l /docker-entrypoint.sh

CMD ["/docker-entrypoint.sh"]
