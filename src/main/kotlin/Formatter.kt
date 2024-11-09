import dev.inmo.tgbotapi.Trace
import io.github.mivek.model.*
import io.github.mivek.service.MetarService
import io.github.mivek.service.TAFService
import kotlin.math.roundToInt

object Formatter {

    private val metarService = MetarService.getInstance()
    private val tafService = TAFService.getInstance()

    fun getMetar(icao: Icao): Pair<String, String> {
        Trace.save("getMetar", mapOf("code" to icao.code))
        val metar = metarService.retrieveFromAirport(icao.code)
        return Pair(getCommon(metar), metar.message)
    }

    fun getTaf(icao: Icao): Pair<String, String> {
        Trace.save("getTaf", mapOf("code" to icao.code))
        val taf = tafService.retrieveFromAirport(icao.code)
        return Pair(getCommon(taf), taf.message)
    }

    private fun getCommon(container: AbstractWeatherCode): String {
        val specific =
            when (container) {
                is Metar ->
                    """
                ${container.day} ${container.time}
                temp: ${container.temperature}, dew point: ${container.dewPoint}, ${if (container.isNosig) "nosig" else ""}
                """
                        .trimIndent()
                        .trimMargin()
                is TAF ->
                    container.validity.let {
                        "${it.startDay}d ${it.startHour}h - ${it.endDay}d ${it.endHour}"
                    }
                else -> throw IllegalArgumentException()
            }

        val sb = StringBuilder()
        sb.append(getAirport(container.airport))
        sb.append("\n").append(specific)
        getWind(container.wind).ifNotEmpty { sb.append("\n").append(it) }
        sb.append("\n").append(getVisibility(container.visibility))
        getVerticalVisibility(container.verticalVisibility).ifNotEmpty {
            sb.append("\n").append(it)
        }
        getWeatherConditions(container.weatherConditions).ifNotEmpty { sb.append("\n").append(it) }
        getClouds(container.clouds).ifNotEmpty { sb.append("\n").append(it) }
        getRemark(container.remark).ifNotEmpty { sb.append("\n").append(it) }
        return sb.toString().replace("null", "")
    }

    private fun convertSpeed(speed: Int, unit: String): Int =
        when (unit.lowercase()) {
            "kt" -> (speed * 1.852).roundToInt()
            "mps" -> (speed * 3.6).roundToInt()
            else -> throw IllegalArgumentException()
        }

    private fun getAirport(airport: Airport): String =
        "${airport.name} ${airport.icao}(${airport.iata}) ${airport.altitude}"

    private fun getWind(wind: Wind?): String =
        if (wind == null) ""
        else {
            "wind: ${convertSpeed(wind.speed, wind.unit)} km/h ${wind.directionDegrees}(${wind.direction})"
        }

    private fun getVisibility(visibility: Visibility): String =
        "visibility: ${visibility.mainVisibility}"

    private fun getVerticalVisibility(visibility: Int?): String =
        if (visibility == null) ""
        else {
            "vertical visibility: $visibility"
        }

    private fun getRemark(remark: String?): String =
        if (remark.isNullOrBlank()) ""
        else {
            "remark: $remark"
        }

    private fun getWeatherConditions(weatherCondition: List<WeatherCondition>): String =
        weatherCondition
            .joinToString(",") {
                "${it.intensity?.name?.lowercase()} ${it.descriptive} ${it.phenomenons.joinToString(",")}"
            }
            .trim()

    private fun getClouds(clouds: List<Cloud>): String =
        clouds
            .sortedBy { it.height }
            .joinToString(",") { "${it.type} ${it.quantity} ${it.height}" }
            .trim()
}
