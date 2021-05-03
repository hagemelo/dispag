const command = require('./command')

const pushNovoOrcamento = async event=> {

    let payload = command.createmsgtopushKafka(process.env.KAFKATOPIC_NOVOORCAMENTO, event.body)
    command.pushTopic(payload)
}

const pushEfetivarNovoOrcamento = async event=> {

    let payload = command.createmsgtopushKafka(process.env.KAFKATOPIC_EFETIVARNOVOORCAMENTO, event.body)
    command.pushTopic(payload)
}

module.exports.novoorcamento = event =>{
     
    params ={
      event:event,
      functions:  [pushNovoOrcamento, pushEfetivarNovoOrcamento]
    }
    return command.action(params)   
 }