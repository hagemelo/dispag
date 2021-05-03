'use strict';
const {pool} = require('../conf/data-source')


module.exports.authenticate = async params => {
    const QUERY_USUARIO = ` SELECT u.use_id, u.nome FROM tb_usuario u WHERE u.login = '${params.user}' and  u.senha = '${params.passwd}' `
    console.log(QUERY_USUARIO)
    
    return pool.query(QUERY_USUARIO)
        .then(res => {
            console.log('rowCount: ' + res.rowCount)
            return res.rowCount>0            
        })
        .catch(()=> {return false})
        //.finally(()=>  pool.end())

} 
