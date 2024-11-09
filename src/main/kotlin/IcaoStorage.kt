import arrow.core.Either
import dev.inmo.tgbotapi.Trace

object IcaoStorage {

    suspend fun get(code: String): Either<String, Icao> =
        when {
            Iata.isValid(code) -> load(Iata(code))
            Icao.isValid(code) -> Either.Right(Icao(code))
            else -> Either.Left("No IATA or ICAO found: $code")
        }

    private suspend fun load(iata: Iata): Either<String, Icao> {
        if (redisClient.hexists("iata2icao", iata.code) == 1L) {
            val icao = redisClient.hget("iata2icao", iata.code)!!
            Trace.save("loadMapping", mapOf("from" to iata.code, "to" to icao))
            return Either.Right(Icao(icao))
        }
        return iata.asIcao()?.let { icao ->
            redisClient.hset("iata2icao", Pair(iata.code, icao.code))
            Trace.save("computeMapping", mapOf("from" to iata.code, "to" to icao.code))
            Either.Right(icao)
        } ?: Either.Left("ICAO not found for IATA: $iata")
    }
}
