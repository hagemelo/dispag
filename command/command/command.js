//const bp = require('body-parser')

const kafka = require('kafka-node')
const client = new kafka.KafkaClient({kafkaHost: process.env.KAFKA_SERVER})
const jwt = require('jsonwebtoken')
//# Class Exxeptions
const {PushTopicError: commandException} = require('../exceptions/exception')
const {TokenExpiradoError: TokenExpiradoError} = require('../exceptions/exception')
const {AusenciaHeadersFundamentaisError: AusenciaHeadersFundamentaisError} = require('../exceptions/exception')
const {PushTopicError: PushTopicError} = require('../exceptions/exception')


const isKafkaOn = async ()=>{
  
  if (process.env.KAFKA_ENABLE == 'OFF')
    throw new Error('[Kafka Off] Nenhuma Acao Sera Tomada')
}

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
    throw new commandException("Erro in (" +payloads.topic + ")")
  })
  
}




module.exports = { isKafkaOn, validarTokenExpirado, existHeadertkuuid, createmsgtopushKafka, pushTopic }