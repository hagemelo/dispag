'use strict';

const {saldosDebitosNoMes} = require('../repository/debitos-repository')
const {detalhesDebitosNoMes} = require('../repository/debitos-repository')
const {run} = require('./query')


module.exports.saldosmes = async event => {

   return run(event, saldosDebitosNoMes)
}

module.exports.datelhes = async event => {

    return run(event, detalhesDebitosNoMes)
}
