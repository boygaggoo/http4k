package org.reekwest.http.contract

//class BodyTest {
//
//    @Test
//    fun `can get string body`() {
//        val request = get("").copy(body = "some value".toBody())
//        assertThat(Body.string()(request), equalTo("some value"))
//    }
//
//    @Test
//    fun `can get form body`() {
//        val request = get("").copy(
//            headers = listOf("Content-Type" to ContentType.APPLICATION_FORM_URLENCODED.value),
//            body = "hello=world&another=planet".toBody())
//        val expected: Form = listOf("hello" to "world", "another" to "planet")
//        assertThat(Body.form()(request), equalTo(expected))
//    }
//
//    @Test
//    fun `form body blows up if not URL content type`() {
//        val request = get("").copy(
//            headers = listOf("Content-Type" to "unknown"),
//            body = "hello=world&another=planet".toBody())
//        assertThat({ Body.form()(request) }, throws<Invalid>())
//    }
//
//    data class MyCustomBodyType(val value: String)
////
////    @Test
////    fun `can create a custom Body type`() {
////
////        fun Body.toCustomType() = Body.map { String(it.array()) }.map(::MyCustomBodyType)
////
////        val request = get("").copy(
////            body = "hello world!".toBody())
////        assertThat(request[Body.toCustomType()], equalTo(MyCustomBodyType("hello world!")))
////    }
//}

