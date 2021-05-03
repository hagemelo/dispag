const command = require('./command')

const pushPagardebito = async event=> {

    let payload = command.createmsgtopushKafka(process.env.KAFKATOPIC_PAGARDEBITO, event.body)
    command.pushTopic(payload)
}

module.exports.pagardebito = event =>{
     
    params ={
      event:event,
      functions:  [pushPagardebito]
    }
    return command.action(params)   
 }
