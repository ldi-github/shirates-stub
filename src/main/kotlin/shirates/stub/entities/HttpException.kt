package shirates.stub.entities

import org.springframework.http.HttpStatus

class HttpException(
    val status: HttpStatus,
    override val message: String? = null
) : Exception()