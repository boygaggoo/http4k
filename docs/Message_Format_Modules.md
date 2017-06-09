### Installation (Gradle)
**Argo:**  ```compile group: "org.http4k", name: "http4k-format-argo", version: "X.X.X"```

**Gson:**  ```compile group: "org.http4k", name: "http4k-format-gson", version: "X.X.X"```

**Jackson:** ```compile group: "org.http4k", name: "http4k-format-jackson", version: "X.X.X"```

### About
These modules add the ability to use JSON as a first-class citizen when reading from and to HTTP messages. Each implementation adds a set of 
standard methods and extension methods for converting common types into native JSON objects, including custom Lens methods for each library so that 
JSON node objects can be written and read directly from HTTP messages:

#### Extension method API:
```kotlin
val json = Jackson

val objectUsingExtensionFunctions =
    listOf(
        "thisIsAString" to "stringValue".asJsonValue(),
        "thisIsANumber" to 12345.asJsonValue(),
        "thisIsAList" to listOf(true.asJsonValue()).asJsonArray()
    ).asJsonObject()

println(objectUsingExtensionFunctions.asPrettyJsonString())
```

#### Direct JSON library API:
```kotlin
val objectUsingDirectApi = json.obj(
    "thisIsAString" to json.string("stringValue"),
    "thisIsANumber" to json.number(12345),
    "thisIsAList" to json.array(listOf(json.boolean(true)))
)

println(
    Response(OK).with(
        Body.json().toLens() to json.array(objectUsingDirectApi, objectUsingExtensionFunctions)
    )
)
```