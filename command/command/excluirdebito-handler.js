const command = require('./command')

const pushExcluirdebito = async event=> {

    let payload = command.createmsgtopushKafka(process.env.KAFKATOPIC_EXCLUIRDEBITO, event.body)
    command.pushTopic(payload)
}

module.exports.excluirdebito = event =>{
     
    params ={
      event:event,
      functions:  [pushExcluirdebito]
    }
    return command.action(params)   
 }