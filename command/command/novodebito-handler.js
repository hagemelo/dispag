//'use strict';
const command = require('./command')
const verify = require('../auth/verify-token')
const {responseCode: respcod}  = require('../conf/response_code')
const {AusenciaHeadersFundamentaisError: AusenciaHeadersFundamentaisError} = require('../exceptions/exception')
const {TokenExpiradoError: TokenExpiradoError} = require('../exceptions/exception')
const {PushTopicError: PushTopicError} = require('../exceptions/exception')

const pushNovodebito = async event=> {

  let payload = command.createmsgtopushKafka(process.env.KAFKATOPIC_NOVODEBITO, event.body)
  command.pushTopic(payload)
}

const pushEfetivarNovodebito = async event=> {

  let payload = command.createmsgtopushKafka(process.env.KAFKATOPIC_EFETIVARNOVODEBITO, event.body)
  command.pushTopic(payload)
}

const pushCadastrarCredor = async event=> {

  const jsonBody = JSON.parse( event.body)
  const cadastrarCredorBody = JSON.stringify(jsonBody.credor)
  const payload = command.createmsgtopushKafka(process.env.KAFKATOPIC_CADASTRARCREDOR, cadastrarCredorBody)
  command.pushTopic(payload)
}

const commandNovoDebito = async event =>{
  
  try{
    
    verify.validarTokenExpirado(event)
    verify.existHeadertkuuid(event)
    await pushCadastrarCredor(event) 
    await pushNovodebito(event)
    await pushEfetivarNovodebito(event) 
    return respcod.acceptedWithThismessageReturn(event, 'Operacao Realizada Com Sucesso, as acoes serão tomadas no decorrer do tempo')  
  }catch (exception) {
    if (exception instanceof TokenExpiradoError) {
        
      return respcod.tokenNaoAutorizadoReturn(event)
    }
    if (exception instanceof AusenciaHeadersFundamentaisError){
        
      return respcod.ausenciaHeadersFundamentaisReturn(event)
    }
    if (exception instanceof PushTopicError){
        
      return respcod.serviceUnavailableReturn(event)
    }
  }
    
}

const novoDebito = async (event, execCommand) =>{

  return command.isKafkaOn()
              .then(() => {return execCommand(event)})
              .catch(error=> {return respcod.acceptedWithThismessageReturn(event, error.message)})
}

const action = async event =>{
     
  return novoDebito(event, commandNovoDebito)   
}

module.exports = {
  action,
  novoDebito
}
