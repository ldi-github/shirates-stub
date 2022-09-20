package shirates.stub.commons.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import shirates.stub.commons.logging.Logger
import shirates.stub.entities.HttpException


@ControllerAdvice
class StubControllerAdvice {

    @ExceptionHandler(HttpException::class)
    fun handleStubException(e: HttpException): ResponseEntity<String> {
        val status = e.status
        var errorMessage = "Error. ${status.value()} ${status.reasonPhrase}"
        if (e.message != null) {
            errorMessage += "<br>${e.message}"
        }
        Logger.info(errorMessage)
        return ResponseEntity(errorMessage, status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(): ResponseEntity<String> {
        return ResponseEntity("unexpected exception.", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}