package shirates.stub.commons.annotaions

@Target(AnnotationTarget.FUNCTION)
annotation class ApiDescription(

    /**
     * API description
     */
    val description: String
) {
}