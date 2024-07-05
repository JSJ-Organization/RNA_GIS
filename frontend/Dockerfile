## Node.js의 공식 이미지 사용
#FROM node:20-alpine AS build
#
## 작업 디렉토리 설정
#WORKDIR /app
#
## package.json과 package-lock.json 복사
#COPY package*.json ./
#
## 의존성 설치
#RUN npm install
#
## 소스 파일 복사
#COPY . .
#
## 빌드 실행
#RUN npm run build
#
## Nginx 사용하여 빌드된 파일을 서빙
#FROM nginx:alpine
#COPY --from=build /app/dist /usr/share/nginx/html
#
## Nginx 설정 파일 복사
#COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
#
## Nginx 포트 설정
#EXPOSE 80
#CMD ["nginx", "-g", "daemon off;"]
