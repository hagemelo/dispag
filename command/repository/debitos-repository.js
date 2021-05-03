const {pool} = require('../conf/data-source')


module.exports.saldosDebitosNoMes = async params => {

    console.log(`Saldos Debitos no mes Parametros==>  Ano: '${params.ano}' Mes: '${params.mes}'`)
    const QUERY = `SELECT o.mes||' '|| o.ano as orcamento,
                        coalesce((select sum(d.valor) from 
                                tb_debitos d 
                                join tb_credor c using (credor_id)
                        where c.tipo = 'AVULSOS' 
                            and d.estado = 'APROVADO' 
                            and d.orc_id = o.orc_id
                        ), 0) as AVULSOS,
                        coalesce((select sum(d.valor) from 
                                tb_debitos d 
                                join tb_credor c using (credor_id)
                        where c.tipo = 'BASICOS' 
                            and d.estado = 'APROVADO' 
                            and d.orc_id = o.orc_id
                        ), 0) as BASICOS,
                        coalesce((select sum(d.valor) from 
                                tb_debitos d 
                                join tb_credor c using (credor_id)
                        where c.tipo = 'RECORRENTES' 
                            and d.estado = 'APROVADO' 
                            and d.orc_id = o.orc_id
                        ), 0) as RECORRENTES,
                        coalesce((select sum(d.valor) from 
                                tb_debitos d 
                                join tb_credor c using (credor_id)
                        where d.estado = 'APROVADO' 
                            and d.orc_id = o.orc_id
                        ), 0) as TOTAL
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

module.exports.detalhesDebitosNoMes = async params => {

    console.log(`Detalhes Debitos no mes Parametros==> tipo: '${params.tipo}' Ano: '${params.ano}' Mes: '${params.mes}'`)
    const QUERY = `select 
                        to_char(d.vencimento, 'dd/MM/yyyy') vencimento,
                        d.marcacao ,
                        d.valor,
                        d.status situacao
                    from tb_debitos d 
                        join tb_credor c using (credor_id)
                        join tb_orcamento o using (orc_id) 
                    where  c.tipo = '${params.tipo}' and o.ano = '${params.ano}' and o.mes = '${params.mes}'
                    order by d.vencimento`
    
    
    return pool.query(QUERY)
        .then(res => {
            console.log('rowCount: ' + res.rowCount)
            return res.rowCount>0? res.rows: '[]'
        })
        .catch(err => {
            console.log(err)
            return '[]'})
       
}