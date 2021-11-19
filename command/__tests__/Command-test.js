
console.log('>>>> START LOAD TEST...')


const logRequest = (requestParams, context, ee, next)=>{
	
	console.log(`### Request URL: ${requestParams.url}`)
	return next()
}


const responseLogin = (requestParams, response, context, ee, next) =>{
	
	

	const responseBody = JSON.parse(response.body)

	context.vars.respuuid = responseBody.uuid
	console.log(`${responseBody.uuid}`)
	//for (x in response)
	//	console.log("UserContext:::" + x)
	console.log(`### Request END`)
    return next()
}

const responseRegistrar = (requestParams, response, context, ee, next) =>{
	
	console.log(`${response.body}`)

	//for (x in response)
	//	console.log("UserContext:::" + x)
	console.log(`### Request END`)
    return next()
}


module.exports = {
    logRequest,
    responseLogin,
	
}
