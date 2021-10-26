const {registrarDebito} = require("../command/registrar-debito-handler")
const mockedEnv = require('mocked-env')
var jwt = require('jsonwebtoken') 

let restore = mockedEnv({
   
    KAFKA_ENABLE : 'OFF',
    SECRET: '#jequiladispag@$12',
    KAFKATOPIC_REGISTRARDEBITO: 'REGISTRARDEBITO'

})


const gerarToken = ()=>{

    return jwt.sign({ id: 'Alex', senha: '1234' }, process.env.SECRET, {
    expiresIn: 86400 // validade do token, 24hrs
   })
}


test('[Debito] Deve Executar Push <Kafka Off>', async () => {


    
    const PAYLOAD_AUTH = '{"valor":2, "marcacao":"PAGAMENTO DE BESTEIRA", '+
        '"credor":{ "descricao":"DINHEIRO NA CARTEIRA", "tipo":"AVULSOS" }, '+
        '"orcamento":{"mes":"ABRIL", "ano":"2021"}, ' +
        '"vencimento":"08/12/2021" }'
    const vbody = JSON.stringify(PAYLOAD_AUTH)
    const command = {
        execCommand : async function (params){
    
                return true
        }
    }
    const event = {
        body : vbody,
        headers: {
            token: gerarToken()
        }
    }

    const result = await registrarDebito(event)
        
    expect(result.statusCode).toBe(202)
    expect(result.body).toContain('Sucesso')
})
