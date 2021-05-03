//const bp = require('body-parser')

const kafka = require('kafka-node')
const client = new kafka.KafkaClient({kafkaHost: process.env.KAFKA_SERVER})
const jwt = require('jsonwebtoken')
const {execVerify} = require('../auth/verify-token')
const {responseCode: respcod}  = require('../conf/response_code')
//# Class Exxeptions

const {PushTopicError} = require('../exceptions/exception')
const {AusenciaHeadersFundamentaisError: AusenciaHeadersFundamentaisError} = require('../exceptions/exception')
const {TokenExpiradoError: TokenExpiradoError} = require('../exceptions/exception')


const isKafkaOn = async ()=>{
  
  if (process.env.KAFKA_ENABLE == 'OFF')
    throw new Error('[Kafka Off] Nenhuma Acao Sera Tomada')
}

const createmsgtopushKafka = (topic, body)=>{

  return  [{ 
    topic: topic, 
    messages: body,
    partition: 0 
  }] 
}

const pushTopic = payloads =>{
 
  let producer = new kafka.Producer(client)
  producer.on('ready', function () {
    producer.send(payloads, (err, data) => {
      if (err) {
        
        console.error(err)
        throw new PushTopicError('[kafka-producer -> '+payloads.topic+']: broker failed')
      }else {

        console.log('[kafka-producer -> '+payloads.topic+']: broker success')
      }
    })
  })
  producer.on('error', function () {
    throw new PushTopicError("Erro in (" +payloads.topic + ")")
  })
  
}

const run = async (params)  =>{
  
  try{
    
    execVerify(params.event)
    await params.functions.forEach(func => {func(params.event) });

    return respcod.acceptedWithThismessageReturn(params.event, 'Operacao Realizada Com Sucesso, as acoes serão tomadas no decorrer do tempo')  
  }catch (exception) {
    if (exception instanceof TokenExpiradoError) {
        
      return respcod.tokenNaoAutorizadoReturn(params.event)
    }
    if (exception instanceof AusenciaHeadersFundamentaisError){
        
      return respcod.ausenciaHeadersFundamentaisReturn(params.event)
    }
    if (exception instanceof PushTopicError){
        
      return respcod.serviceUnavailableReturn(params.event)
    }
  }
    
}

const action = async (params) =>{

  return isKafkaOn()
              .then(() => {return run(params)})
              .catch(error=> {return respcod.acceptedWithThismessageReturn(params.event, error.message)})
}


module.exports = { isKafkaOn, createmsgtopushKafka, pushTopic, action }