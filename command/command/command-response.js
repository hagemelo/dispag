const {responseCode: respcod}  = require('../conf/response-code')
const {PushTopicError} = require('../exceptions/exception')
const {AusenciaHeadersFundamentaisError: AusenciaHeadersFundamentaisError} = require('../exceptions/exception')
const {TokenExpiradoError: TokenExpiradoError} = require('../exceptions/exception')


const commandReponseException = params => {

    if (params.exception instanceof TokenExpiradoError) {
        
        return respcod.tokenNaoAutorizadoReturn(params.event)
    }
    if (params.exception instanceof AusenciaHeadersFundamentaisError){
        
        return respcod.ausenciaHeadersFundamentaisReturn(params.event)
    }
    if (params.exception instanceof PushTopicError){
        
        return respcod.serviceUnavailableReturn(params.event)
    }
}


const commandReponse = () =>{
    
    const MSG = 'Operacao Realizada Com Sucesso, as acoes serão tomadas no decorrer do tempo'
    return respcod.acceptedWithThismessageReturn(`${MSG}`)  
}

module.exports = { commandReponse, commandReponseException}