//#200
const OK = 200
const CREATED = 201
const ACCEPTED = 202
//#400
const BAD_REQUEST = 400
const UNAUTHORIZED = 401
const FORBIDDEN = 403

//#500
const INTERNAL_SERVER_ERROR = 500
const NOT_IMPLEMENTED = 501
const BAD_GATEWAY = 502
const SERVICE_UNAVAILABLE = 503

let responseCode = {

    successDefault : function (event){
        return {
            statusCode: OK,
            body: JSON.stringify(
              {
                message: 'Operacao Realizada Com Sucesso!',
                input: event,
              },
              null,
              2
            ),
          }
    },

    successWithThisBodyReturn : function (event, pbody){
        
      return {
            statusCode: OK,
            body: pbody,
          }
    },

    acceptedWithThismessageReturn : function (msg){
        return {
            statusCode: ACCEPTED,
            body: JSON.stringify(
                {
                  message: msg,
                },
                null,
                2
              ),
          }
    },

    tokenNaoAutorizadoReturn : function (event){
        
      return {
            statusCode: UNAUTHORIZED,
            body: JSON.stringify(
              {
                auth: false,
                message: 'Falha ao autenticar'
              },
              null,
              2
            ),
          }
    },

    serviceUnavailableReturn : function (event){
        
      return {
            statusCode: SERVICE_UNAVAILABLE,
            body: JSON.stringify(
              {
                auth: false,
                message: 'O servidor não está pronto para manipular a requisição',
                input: event.body
              },
              null,
              2
            ),
          }
    },

    ausenciaHeadersFundamentaisReturn : function (event){
        
      return {
            statusCode: UNAUTHORIZED,
            body: JSON.stringify(
              {
                auth: false,
                message: 'Ausencia de Headers Fundamentais para Requisicao'
              },
              null,
              2
            ),
          }
    },

    autenticadoReturn : function (token, user, uuid){
        
      return {
            statusCode: OK,
            headers: {
              'token': token,
            },
            body: JSON.stringify(
              {
                authentication: true,
                user: user,
                "uuid": uuid
              },
              null,
              2
            ),
          }
    },

    naoAutenticadoReturn : function (){
        
      return {
            statusCode: UNAUTHORIZED,
            body: JSON.stringify(
              {
                auth: false,
                message: 'Nao Autenticado'
              },
              null,
              2
            ),
          }
    },

}



module.exports = {
    responseCode
}
