//'use strict';
const command = require('./command')

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

module.exports.novodebito = event =>{
     
  params ={
    event:event,
    functions:  [pushCadastrarCredor, pushNovodebito, pushEfetivarNovodebito]
  }
  return command.action(params)   
}

