'use strict';

const repository = require('../repository/debitosRepository')
const {responseCode: respcod}  = require('../conf/response-code')



module.exports.saldosdebitosnomes = async event => {
    const body = JSON.stringify(
        {
          auth: false,
          message: 'Nao Autenticado'
        },
        null,
        2
      )

    return repository.saldosDebitosNoMes(event)
                            .then(res => {
                                console.log(res)
                                return respcod.successWithThisBodyReturn(event, body)
                            })
    
}