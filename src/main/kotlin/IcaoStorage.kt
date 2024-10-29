import arrow.core.Either
import dev.inmo.kslog.common.KSLog
import dev.inmo.kslog.common.info

object IcaoStorage {

    suspend fun get(code: String): Either<String, Icao> =
        when {
            Iata.isValid(code) -> load(Iata(code))
            Icao.isValid(code) -> Either.Right(Icao(code))
            else -> Either.Left("No IATA or ICAO found: $code")
        }

    private suspend fun load(iata: Iata): Either<String, Icao> {
        if (redisClient.hexists("iata2icao", iata.code) == 1L) {
            KSLog.info("get $iata from redis")
            return Either.Right(Icao(redisClient.hget("iata2icao", iata.code)!!))
        }
        return iata.asIcao()?.let { icao ->
            redisClient.hset("iata2icao", Pair(iata.code, icao.code))
            KSLog.info("add $iata:$icao to cache")
            Either.Right(icao)
        } ?: Either.Left("ICAO not found for IATA: $iata")
    }
}
