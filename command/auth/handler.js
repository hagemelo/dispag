'use strict';
var jwt = require('jsonwebtoken') // package jwt 
const uuid = require ("uuid");

module.exports.login = async event => {
  
  const data = JSON.parse(event.body);


  console.log('Usuário::' + data.user)
  console.log('Usuário::' + data.passwd)
  var token = jwt.sign({ id: data.user, senha: data.passwd }, process.env.SECRET, {
						 expiresIn: 86400 // validade do token, 24hrs
						});
  
  return {
    statusCode: 200,
    headers: {
      'token': token,
    },
    body: JSON.stringify(
      {
        authentication: true,
		    user: data.user,
        "uuid": uuid.v1()
      },
      null,
      2
    ),
  };
};



module.exports.loginteste = async event => {
  
  const data = JSON.parse(event.body);


  console.log(data)
  
  return {
    statusCode: 200,
    body: JSON.stringify(
      {
        message: 'OK',
         },
      null,
      2
    ),
  };
};

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
