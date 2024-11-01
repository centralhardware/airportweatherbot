fun String?.ifNotEmpty(block: (String) -> Unit) {
    if (!this.isNullOrEmpty()) {
        block(this)
    }
}

fun String.asIcao(): Icao? {
    return when (this.length) {
        3 -> Iata(this).asIcao()
        4 -> Icao(this)
        else -> throw IllegalArgumentException()
    }
}
