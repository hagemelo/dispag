'use strict';
const {pool} = require('../conf/config')


module.exports.authenticate = async params => {
    const QUERY_USUARIO = ` SELECT u.use_id, u.nome FROM public.tb_usuario u WHERE u.login = '${params.user}' and  u.senha = '${params.passwd}' `
    console.log(QUERY_USUARIO)
    let result = false
    
    pool.query(QUERY_USUARIO)
        .then(res => {
            console.log('rowCount: ' + res.rowCount)
            res.rowCount>0? result = true: result = false
        })
        .catch(()=> result = false)
        .finally(()=>  pool.end())
    return result
} 
