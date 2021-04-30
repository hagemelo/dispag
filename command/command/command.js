//const bp = require('body-parser')

const kafka = require('kafka-node')
const client = new kafka.KafkaClient({kafkaHost: process.env.KAFKA_SERVER})
const jwt = require('jsonwebtoken')
//# Class Exxeptions

const {PushTopicError} = require('../exceptions/exception')


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




module.exports = { isKafkaOn, createmsgtopushKafka, pushTopic }