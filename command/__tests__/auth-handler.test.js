const commandAuth = require ("../auth/auth-handler")
const mockedEnv = require('mocked-env')

let restore = mockedEnv({
    SECRET: '#jequiladispag@$12',
   
})


test('Teste de autenticacao OK, expera retorno 200', async () => {
    
    const PAYLOAD_AUTH = '{ "user":"Alex", "passwd":"1234" }'
    const vbody = JSON.stringify(PAYLOAD_AUTH)
    const repositoryTrue = {
        authenticate : async function (params){
    
                return true
        }
    }
    const event = {
        body : vbody
    }
    const result = await commandAuth.implementsLogin(event, repositoryTrue)
    expect(result.statusCode).toBe(200)
})




