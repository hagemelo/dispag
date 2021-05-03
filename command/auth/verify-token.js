const jwt = require('jsonwebtoken')
const {TokenExpiradoError: TokenExpiradoError} = require('../exceptions/exception')
const {AusenciaHeadersFundamentaisError: AusenciaHeadersFundamentaisError} = require('../exceptions/exception')

const validarTokenExpirado = event =>{
  
    jwt.verify(event.headers.token, process.env.SECRET, function(err, decoded) {      
      if (err) 
        throw new TokenExpiradoError("Token Expirado")  
    });
  
    console.log("Token::" + event.headers.token)
  }
  
const existHeadertkuuid = event =>{
    
    if(!event.headers.token && !event.headers.uuid) 
      throw new AusenciaHeadersFundamentaisError("Ausencia dos headers uuid e token")
    
    console.log("uuid::" + event.headers.uuid)
}

const execVerify = event => {

  validarTokenExpirado(event)
  existHeadertkuuid(event)
}

module.exports = { validarTokenExpirado, existHeadertkuuid, execVerify}