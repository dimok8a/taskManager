package ru.taskManager.taskManager

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.test.web.servlet.MvcResult
import java.nio.charset.Charset

inline fun <reified T> MvcResult.convert(): T {
    return jacksonObjectMapper().readValue(
        response.getContentAsString(Charset.defaultCharset()),
        T::class.java
    )
}