const {pool} = require('../conf/data-source')


module.exports.orcamentoAnoMes = async params => {

    console.log(`Orcamento no mes Parametros==>  Ano: '${params.ano}' Mes: '${params.mes}'`)
    const QUERY = `SELECT o.receita, o.avulsos, o.basicos, o.recorrentes
                    FROM tb_orcamento o WHERE o.ano = '${params.ano}' and o.mes = '${params.mes}'`
        
    return pool.query(QUERY)
        .then(res => {
            console.log('rowCount: ' + res.rowCount)
            return res.rowCount>0? res.rows: '[]'
        })
        .catch(err => {
            console.log(err)
            return '[]'})
       
}