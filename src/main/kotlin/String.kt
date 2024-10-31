fun String?.ifNotEmpty(block: (String) -> Unit) {
    if (!this.isNullOrEmpty()) {
        block(this)
    }
}

fun String.asIcao(): Icao {
    return Icao(this)
}
