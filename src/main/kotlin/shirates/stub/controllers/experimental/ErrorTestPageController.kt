package shirates.stub.controllers.experimental

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

/**
 * ErrorTestPageController
 */
@Controller
@RequestMapping("/")
class ErrorTestPageController {

    /**
     * HTTP Status 500 – Internal Server Error
     * java.lang.ArithmeticException: / by zero
     */
    @Suppress("DIVISION_BY_ZERO")
    @GetMapping("/error/devbyzero")
    fun reserveCouponTestListCouponList(request: HttpServletRequest): String {

        1 / 0

        return ""
    }
}
