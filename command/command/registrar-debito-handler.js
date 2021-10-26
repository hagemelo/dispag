//'use strict';
const commandReponse = require('./command-response')
const {instance: eventSource}  = require('../conf/event-source')
const {execVerify} = require('../auth/verify-token')


//Método Específico para executar push no Event Source
const pushRegistrardebito = async event=> {

  console.log('[REGISTRARDEBITO-HANDLER] Iniciar Push Registrar Debito')
  eventSource().push({topic:process.env.KAFKATOPIC_REGISTRARDEBITO, body:event.body})
  console.log('[REGISTRARDEBITO-HANDLER] Finalizar Push Registrar Debito')
}

//[Lambda AWS] Método para atender o Contexto Command Registrar Debito 
module.exports.registrarDebito = async event =>{
  
  console.log('[REGISTRARDEBITO-HANDLER] Iniciar Registrar Debito')
  try{
    execVerify(event)
    await pushRegistrardebito(event)
    console.log('[REGISTRARDEBITO-HANDLER] Finalizar Registrar Debito...')
    return commandReponse.commandReponse(event)
  }catch (exception) {
  
    
    console.error('[REGISTRARDEBITO-HANDLER] Lançado Error em Registrar Debito...')
    return commandReponse.commandReponseException({exception:exception, event:event})
  }
    
}



