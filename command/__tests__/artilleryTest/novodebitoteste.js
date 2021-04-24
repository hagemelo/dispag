const SEPARADOR = ";"
var count = 0

let startTimeNow

console.log('XXXX INICIANDO O TESTE XXXXXXX')

module.exports = {
    logRequest,
    logResponse
}



function logRequest(requestParams, context, ee, next){
	console.log(requestParams)
	//console.log("Request:::" + requestParams.url)
	
	
	//console.log("Eventos:::" + ee._eventsCount)
	startTimeNow = new Date().getTime()
	return next()
}


function logResponse(requestParams, response, context, ee, next) {
	let elapsed = new Date().getTime() - startTimeNow
	let log = requestParams.url + SEPARADOR + elapsed
	console.log(log)
    //console.log(response.headers)
	//console.log(response.body)
	//console.log("Time: " + time)
    return next()
}