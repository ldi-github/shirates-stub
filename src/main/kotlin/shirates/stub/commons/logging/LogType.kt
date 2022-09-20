package shirates.stub.commons.logging

enum class LogType(val label: String) {

    /**
     * NONE
     */
    NONE("-"),

    /**
     * TRACE
     */
    TRACE("trace"),

    /**
     * INFO
     */
    INFO("info"),

    /**
     * WARN
     */
    WARN("warn"),

    /**
     * ERROR
     */
    ERROR("ERROR"),
}