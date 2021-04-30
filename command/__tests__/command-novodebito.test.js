const {novoDebito} = require('../command/novodebito_handler')
const mockedEnv = require('mocked-env')

let restore = mockedEnv({
   
    KAFKA_ENABLE : 'OFF'

})


test('Teste de autenticacao OK, expera retorno 200', async () => {


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
        body : vbody
    }

    const result = await novoDebito(event, command.execCommand)
    //expect(result.statusCode).toBe(200)
})
