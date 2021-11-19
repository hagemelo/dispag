FROM node:alpine

WORKDIR /usr/src/app  


COPY packag*.json ./ 

RUN npm install -g npm@8.1.2

RUN npm install

COPY . .  

EXPOSE 3000

CMD ["npx", "serverless", "offline", "start"]