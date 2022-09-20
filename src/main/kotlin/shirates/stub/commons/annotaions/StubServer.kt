package shirates.stub.commons.annotaions

/**
 * Annotation for stub server
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class StubServer(
    /**
     * Server name
     */
    val server: String
) {
}