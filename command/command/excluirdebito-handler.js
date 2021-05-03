const kafka = require('kafka-node')
const command = require('./command')
const verify = require('../auth/verify-token')
const {responseCode: respcod}  = require('../conf/response_code')
const {AusenciaHeadersFundamentaisError: AusenciaHeadersFundamentaisError} = require('../exceptions/exception')
const {TokenExpiradoError: TokenExpiradoError} = require('../exceptions/exception')

const pushExcluirdebito = async event=> {

    let payload = command.createmsgtopushKafka(process.env.KAFKATOPIC_EXCLUIRDEBITO, event.body)
    command.pushTopic(payload)
}

const commandExcluirDebito = async event =>{
  
    try{

        verify.validarTokenExpirado(event)
        verify.existHeadertkuuid(event)
        await pushExcluirdebito(event) 
        return respcod.acceptedWithThismessageReturn(event, 'Operacao Realizada Com Sucesso, as acoes serão tomadas no decorrer do tempo')
    }catch (exception) {
        if (exception instanceof TokenExpiradoError) {
            
          return respcod.tokenNaoAutorizadoReturn(event)
        }
        if (exception instanceof AusenciaHeadersFundamentaisError){
            
          return respcod.ausenciaHeadersFundamentaisReturn(event)
        }
    }
}

const excluirDebito = async (event, execCommand) =>{

    return command.isKafkaOn()
                .then(() => {return execCommand(event)})
                .catch(error=> {return respcod.acceptedWithThismessageReturn(event, error.message)})
}

const action = async event =>{
    
    return excluirDebito(event, commandExcluirDebito)   
}

module.exports = {
    action,
    excluirDebito
}