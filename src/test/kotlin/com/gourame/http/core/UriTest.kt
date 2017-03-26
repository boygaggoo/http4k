package com.gourame.http.core

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import junit.framework.Assert.assertNull
import org.junit.Test

class UriTest {
    @Test
    fun can_parse_full_uri() {
        val value = "http://user:pass@host:1234/some/path?q1=v1&q2=v2#abc"
        val uri = Uri.uri(value)
        assertThat(uri.scheme, equalTo("http"))
        assertThat(uri.authority, equalTo("user:pass@host:1234"))
        assertThat(uri.host, equalTo("host"))
        assertThat(uri.port, equalTo(1234))
        assertThat(uri.fragment, equalTo("abc"))
        assertThat(uri.path, equalTo("/some/path"))
        assertThat(uri.query, equalTo("q1=v1&q2=v2"))
        assertThat(uri.toString(), equalTo(value))
    }

    @Test
    fun can_parse_minimal_uri() {
        val value = "http://host"
        val uri = Uri.uri(value)
        assertThat(uri.scheme, equalTo("http"))
        assertThat(uri.authority, equalTo("host"))
        assertThat(uri.host, equalTo("host"))
        assertNull(uri.port)
        assertThat(uri.fragment, equalTo(""))
        assertThat(uri.path, equalTo(""))
        assertThat(uri.query, equalTo(""))
        assertThat(uri.toString(), equalTo(value))
    }

    @Test
    fun handles_empty_uri(){
        val uri = Uri.uri("")
        assertThat(uri.toString(), equalTo(""))
    }
}