'use strict';

const repository = require('../repository/orcamento-repository')
const {responseCode: respcod}  = require('../conf/response-code')
const verify = require('../auth/verify-token')

const {AusenciaHeadersFundamentaisError: AusenciaHeadersFundamentaisError} = require('../exceptions/exception')
const {TokenExpiradoError: TokenExpiradoError} = require('../exceptions/exception')

const implementacao = async (event, exefunc)=>{

    const jsonBody = JSON.parse( event.body)

    try{

        verify.validarTokenExpirado(event)
        verify.existHeadertkuuid(event)
        return exefunc(jsonBody)
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


module.exports.orcamentoanomes = async event => {

   return implementacao(event, repository.orcamentoAnoMes)
}