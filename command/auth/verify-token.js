const jwt = require('jsonwebtoken')
const {TokenExpiradoError: TokenExpiradoError} = require('../exceptions/exception')
const {AusenciaHeadersFundamentaisError: AusenciaHeadersFundamentaisError} = require('../exceptions/exception')

const validarTokenExpirado = event =>{
  
  console.log('[VERIFY-TOKEN] Iniciar validar Token Expirado')
  jwt.verify(event.headers.token, process.env.SECRET, function(err, decoded) {      
    if (err){ 
      console.error('[VERIFY-TOKEN] Token Expirado')
      throw new TokenExpiradoError("Token Expirado")  
    }
  });

  console.log(`[VERIFY-TOKEN] Finalizar validar Token Expirado ${event.headers.token}`)
}
  
const existHeadertkuuid = event =>{
    
  console.log(`[VERIFY-TOKEN] Iniciar Exist header token e uuid ${event.headers.uuid}`)
  if(!event.headers.token && !event.headers.uuid){
    console.error('[VERIFY-TOKEN] Ausencia dos headers uuid e token') 
    throw new AusenciaHeadersFundamentaisError("Ausencia dos headers uuid e token")
  }
  console.log(`[VERIFY-TOKEN] Finalizar Exist header token e uuid ${event.headers.uuid}`)
}

const execVerify = event => {

  console.log('[VERIFY-TOKEN] Iniciar Verificacao do Token')
  validarTokenExpirado(event)
  existHeadertkuuid(event)
  console.log('[VERIFY-TOKEN] Finalizar Verificacao do Token')
}

module.exports = { validarTokenExpirado, existHeadertkuuid, execVerify}