//'use strict';
const kafka = require('kafka-node')
const bp = require('body-parser')
const respcod = require('../conf/response-code')
const {pagardebito_error: PagarDebitoError} = require('../exceptions')
const client = new kafka.KafkaClient({kafkaHost: process.env.KAFKA_SERVER})


class KafkaOff {
    //Classe para responder de modo padrao qdo o Kafka estiver desabilitado
    constructor (event) {
      this.event = event
    } 
  
    run(){
      return respcod.responseCode.acceptedWithThismessageReturn(this.event, 'Nenhuma Acao Sera Tomada. Kafka Off')
    }
  
  }

  class KafkaOn {
  
    constructor (event) {
      this.event = event  
    } 
    
    criarmsg(){
      let kafka_topic = process.env.KAFKATOPIC_PAGARDEBITO
      return  [{ 
        topic: kafka_topic, 
        messages: this.event.body,
        partition: 0 
      }] 
    }
  
    pushTopic1(producer, payloads){
      producer.on('ready', async function () {
          
        producer.send(payloads, (err, data) => {
          if (err) {
            //console.error('[kafka-producer -> '+kafka_topic+']: broker failed')
            console.error('[kafka-producer -> '+payloads.topic+']: broker failed')
            console.error(err)
            //throw new AgendarAtendimentoError()
          }else {
            
            //console.log('[kafka-producer -> '+kafka_topic+']: broker success')
            console.log('[kafka-producer -> '+payloads.topic+']: broker success')
                      
          }
        })
  
      })
      
    }
  
    run(){
      
      try{
        let producer = new kafka.Producer(client)     
        let payloads = this.criarmsg()
        this.pushTopic1(producer, payloads)
        
      }catch(exception){
        console.log("Entrou na Exception--> " + exception)
        //throw new AgendarAtendimentoError("Erro kafka")
      }  
      return respcod.responseCode.acceptedWithThismessageReturn(this.event, 'Operacao Realizada Com Sucesso, as acoes serão tomadas no decorrer do tempo')
    }
    
  }
  
  
  module.exports.action = async event => {
    
    let runKafka = ((process.env.KAFKA_ENABLE == 'ON') ? new KafkaOn(event): new KafkaOff(event)) 
    return runKafka.run()
    
  }






