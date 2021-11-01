#!/bin/bash

echo Inicialemente Excluir todas as imagens do Deploy Domain Debito
docker rmi -f `docker images -a | grep hagemelo/domaindebito| awk '{print $3}'`

echo Acessar diretorio do Domain debito
cd /mnt/c/Users/hagem/Documents/Desenvolvimento/dispag/domain/debito

echo Criar Deploy hagemelo/debito
docker build -t hagemelo/domaindebito:latest .

cd /home/alexmelo