const {orcamentoAnoMes} = require('../repository/orcamento-repository')
const {run} = require('./query')

module.exports.orcamentoanomes = async event => {

   return run(event, orcamentoAnoMes)
}