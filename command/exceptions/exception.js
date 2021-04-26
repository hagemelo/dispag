
const NOVODEBITOERROR = "ERROR =>> Kafka Producer Novo Debito"
const EXCLUIRDEBITOERROR = "Kafka Producer Excluir Debito onError"
const PAGARDEBITOERROR = "Kafka Producer Pagar Debito onError"
const PUSHTOPICERROR = "Error ao push Topic"
const AUSENCIAHEADERSFUNDAMENTAISERROR = "Ausencia de Headers Fundamentais"
const TOKENEXPIRADOERROR = "Token expirado error"
const PUSHTOPICKAFKAERROR = "Push topic Kafak error"
const NAOEUTENTICARERROR = "Error ao Autenticar"

function printError(obj){
  console.error(obj.name)
  console.error(obj.message)
  console.error(obj.stack)
}

class NovoDebitoError extends Error {
    constructor(message) {
      super(message)
      this.name = NOVODEBITOERROR
      Object.setPrototypeOf(this, NovoDebitoError.prototype)
      printError(this)
    }
}

class ExcluirDebitoError extends Error {
  constructor(message) {
    super(message)
    this.name = EXCLUIRDEBITOERROR
    Object.setPrototypeOf(this, ExcluirDebitoError.prototype)
    printError(this)
  }
}

class PagarDebitoError extends Error {
  constructor(message) {
    super(message)
    this.name = PAGARDEBITOERROR
    Object.setPrototypeOf(this, PagarDebitoError.prototype)
    printError(this)
  }
}

class PushTopicError extends Error {
  constructor(message) {
    super(message)
    this.name = PUSHTOPICERROR
    Object.setPrototypeOf(this, PushTopicError.prototype)
    printError(this)
  }
}

class AusenciaHeadersFundamentaisError extends Error {
  constructor(message) {
    super(message)
    this.name = AUSENCIAHEADERSFUNDAMENTAISERROR
    Object.setPrototypeOf(this, AusenciaHeadersFundamentaisError.prototype)
    printError(this)
  }
}

class TokenExpiradoError extends Error {
  constructor(message) {
    super(message)
    this.name = TOKENEXPIRADOERROR
    Object.setPrototypeOf(this, TokenExpiradoError.prototype)
    printError(this)
  }
}

module.exports = {
  NovoDebitoError,
  ExcluirDebitoError,
  PagarDebitoError,
  PushTopicError,
  AusenciaHeadersFundamentaisError,
  TokenExpiradoError,
}

