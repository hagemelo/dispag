const kafka = require('kafka-node')


//funcao acionada qdo a conexão com o kafka esta off
const pushOFF = async payloads =>{

  console.log(`[EVENT-SOURCE] -> ${payloads.topic}] [Kafka Off]: Nenhuma Acao Sera Tomada`)
}

const returnSend = (err, data)=>{

  if (err) {
        
    console.error(err)
    //throw new PushTopicError('[kafka-producer -> '+payloads.topic+']: broker failed')
  }else {

    console.log('[kafka-producer -> '+data+']: broker success')
  }

}

//Constroi a mensagem que será publicada no tópico kafka
const buildingMessage = params=>{

  console.log(`[EVENT-SOURCE] Building Message para Topic-> ${params.topic}`)
  console.log(`[EVENT-SOURCE] Building Message para body-> ${params.body}`)
  return  [{ 
    topic: params.topic, 
    messages: params.body,
    partition: 0 
  }] 
}

//funcao acionada qdo a conexão com o kafka esta On
const pushON= async params =>{
 
  console.log(`[EVENT-SOURCE] Iniciar Push kafka-producer Topic-> ${params.topic}`)
  const message = buildingMessage(params)
  const client = new kafka.KafkaClient({kafkaHost: process.env.KAFKA_SERVER})
  const producer = new kafka.Producer(client)
  producer.on('ready', () => {producer.send(message, returnSend)}) 
  console.log(`[EVENT-SOURCE] Finalizado Push kafka-producer Topic-> ${params.body}`) 
}

module.exports.instance = () =>{
  
  return {push : process.env.KAFKA_ENABLE === 'OFF'?pushOFF:pushON}   
}