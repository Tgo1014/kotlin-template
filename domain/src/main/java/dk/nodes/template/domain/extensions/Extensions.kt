package dk.nodes.template.domain.extensions

inline fun <T> T.guard(block: T.() -> Unit): T {
    if (this == null) block(); return this
}

inline fun <reified T: Enum<T>> T.byNameOrFirst(name: String): T {
    val values = enumValues<T>()
    return values.firstOrNull { it.name == name } ?: values.first()
}