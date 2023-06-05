FROM node:20.2.0-alpine3.17
WORKDIR /usr/src/app
COPY package.json ./
COPY package-lock.json ./
RUN npm install
COPY . .
ENTRYPOINT ["npm", "start"]