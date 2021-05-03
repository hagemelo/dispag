const command = require('./command')

const pushEcluirOrcamento = async event=> {

    let payload = command.createmsgtopushKafka(process.env.KAFKATOPIC_EXCLUIRORCAMENTO, event.body)
    command.pushTopic(payload)
}

module.exports.excluirorcamento = event =>{
     
    params ={
      event:event,
      functions:  [pushEcluirOrcamento]
    }
    return command.action(params)   
 }