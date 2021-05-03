const {responseCode: respcod}  = require('../conf/response-code')
const {execVerify} = require('../auth/verify-token')

const {AusenciaHeadersFundamentaisError: AusenciaHeadersFundamentaisError} = require('../exceptions/exception')
const {TokenExpiradoError: TokenExpiradoError} = require('../exceptions/exception')


module.exports.run = async (event, repository)=>{

    const jsonBody = JSON.parse( event.body)

    try{

        execVerify(event)
        return repository(jsonBody)
                            .then(res => {
                                return respcod.successWithThisBodyReturn(event, JSON.stringify(res))
                            })
    }catch (exception) {
        if (exception instanceof TokenExpiradoError) {
            
            return respcod.tokenNaoAutorizadoReturn(event)
        }
        if (exception instanceof AusenciaHeadersFundamentaisError){
            
            return respcod.ausenciaHeadersFundamentaisReturn(event)
        }
    }
}