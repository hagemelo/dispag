'use strict';
var jwt = require('jsonwebtoken') // package jwt 
const uuid = require ("uuid");
const {authenticate} = require('../repository/usuarioRepository')
const {responseCode: respcod}  = require('../conf/response-code')


const getToken = data =>{
  
  const token =  jwt.sign({ id: data.user, senha: data.passwd }, process.env.SECRET, {
    expiresIn: 86400 // validade do token, 24hrs
   })

  return respcod.autenticadoReturn(token, data.user, uuid.v1())
}

module.exports.login = async event => {
  
  const data = JSON.parse(event.body)
  console.log('Usuário::' + data.user)
  let result
  await authenticate(data)
          .then(res => res? result = getToken(data): result = respcod.naoAutenticadoReturn() )
          .catch(()=> result = respcod.naoAutenticadoReturn() )
  return result
}

module.exports.verificartk = async event => {
  
  const token  = event.headers.token
  if (!token) 
    return res.status(403).send({ auth: false, message: 'Informe um token' });

  jwt.verify(token, process.env.SECRET, function(err, decoded) {      
    if (err) 
      return res.status(500).send({ auth: false, message: 'Falha ao autenticar o token' });    
  
  });

  return {
    statusCode: 200,
    body: JSON.stringify(
      {
        tokenOK: true,
		
      },
      null,
      2
		),
	};
  
  
};
