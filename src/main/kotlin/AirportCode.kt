import io.github.mivek.provider.airport.AirportProvider
import java.util.*

interface AirportCode {
    val code: String
}

@JvmInline
value class Iata(override val code: String) : AirportCode {

    init {
        require(isValid(code))
    }

    companion object {
        fun isValid(value: String): Boolean {
            return value.length == 3
        }
    }
}

@JvmInline
value class Icao(override val code: String) : AirportCode {

    init {
        require(isValid(code))
    }

    companion object {
        fun isValid(value: String): Boolean {
            return value.length == 4
        }
    }
}

private val airportProvider = ServiceLoader.load(AirportProvider::class.java).iterator().next()

fun Iata.asIcao(): Icao? {
    return airportProvider.airports
        .filter { it.value.iata.lowercase() == this.code }
        .map { it.value.icao }
        .map { Icao(it) }
        .firstOrNull()
}
