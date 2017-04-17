package org.reekwest.http.contract

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.throws
import org.junit.Test
import org.reekwest.http.core.ContentType.Companion.APPLICATION_FORM_URLENCODED
import org.reekwest.http.core.body.toBody
import org.reekwest.http.core.contract.*
import org.reekwest.http.core.contract.ContractBreach.Companion.Invalid
import org.reekwest.http.core.contract.FormValidator.Feedback
import org.reekwest.http.core.contract.FormValidator.Strict
import org.reekwest.http.core.contract.Header.Common.CONTENT_TYPE
import org.reekwest.http.core.get

class WebFormTest {

    private val emptyRequest = get("")

    @Test
    fun `can get form body`() {
        val request = emptyRequest.copy(
            headers = listOf("Content-Type" to APPLICATION_FORM_URLENCODED.value),
            body = "hello=world&another=planet".toBody())
        val expected = mapOf("hello" to listOf("world"), "another" to listOf("planet"))
        assertThat(Body.form()(request), equalTo(expected))
    }

    @Test
    fun `form body blows up if not URL content type`() {
        val request = emptyRequest.copy(
            headers = listOf("Content-Type" to "unknown"),
            body = "hello=world&another=planet".toBody())
        assertThat({ Body.form()(request) }, throws(equalTo(Invalid(CONTENT_TYPE))))
    }

    @Test
    fun `web form blows up if not URL content type`() {
        val request = emptyRequest.copy(
            headers = listOf("Content-Type" to "unknown"),
            body = "hello=world&another=123".toBody())

        assertThat({
            Body.webForm(Strict,
                FormField.required("hello"),
                FormField.int().required("another")
            )(request)
        }, throws(equalTo(Invalid(CONTENT_TYPE))))
    }

    @Test
    fun `web form extracts ok form values`() {
        val request = emptyRequest.copy(
            headers = listOf("Content-Type" to APPLICATION_FORM_URLENCODED.value),
            body = "hello=world&another=123".toBody())

        val expected = mapOf("hello" to listOf("world"), "another" to listOf("123"))

        assertThat(Body.webForm(Strict,
            FormField.required("hello"),
            FormField.int().required("another")
        )(request), equalTo(WebForm(expected, emptyList())))
    }

    @Test
    fun `feedback web form extracts ok form values and errors`() {
        val request = emptyRequest.copy(
            headers = listOf("Content-Type" to APPLICATION_FORM_URLENCODED.value),
            body = "another=123".toBody())

        val requiredString = FormField.required("hello")
        assertThat(Body.webForm(Feedback,
            requiredString,
            FormField.int().required("another")
        )(request), equalTo(WebForm(mapOf("another" to listOf("123")), listOf(Missing(requiredString.meta)))))
    }

    @Test
    fun `strict web form blows up with invalid form values`() {
        val request = emptyRequest.copy(
            headers = listOf("Content-Type" to APPLICATION_FORM_URLENCODED.value),
            body = "another=notANumber".toBody())

        val stringRequiredField = FormField.required("hello")
        val intRequiredField = FormField.int().required("another")
        assertThat(
            { Body.webForm(Strict, stringRequiredField, intRequiredField)(request) },
            throws(equalTo(ContractBreach(Missing(stringRequiredField.meta), Invalid(intRequiredField.meta))))
        )
    }
}

