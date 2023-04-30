FROM node:18.15-alpine3.17 AS build
WORKDIR /app

COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build:prod
# Serve Application using Nginx Server

FROM nginx:alpine as runtime

COPY --from=build /app/nginx-config.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/ui-angular/ /usr/share/nginx/html

EXPOSE 80
#USER nginx
ENTRYPOINT ["nginx","-g","daemon off;"]
