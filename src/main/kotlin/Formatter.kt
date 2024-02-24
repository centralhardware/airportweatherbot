import io.github.mivek.model.*
import io.github.mivek.service.MetarService
import io.github.mivek.service.TAFService
import kotlin.math.roundToInt

class Formatter {

    val metarService = MetarService.getInstance()
    val tafService = TAFService.getInstance()

    public fun getMetar(icao: String): String {
        log.info("get metar for $icao")
        val metar = metarService.retrieveFromAirport(icao)
        var inline = """
        ${metar.day} ${metar.time}
        temp: ${metar.temperature}, dew point: ${metar.dewPoint}, ${if (metar.isNosig == true) "nosig" else ""}
    """.trimIndent().trimMargin();
        return getCommon(metar, inline).replace("null", "")
    }

    public fun getTaf(icao: String): String {
        log.info("get taf for $icao")
        val taf = tafService.retrieveFromAirport(icao)
        val validity = taf.validity
        var inline = "${validity.startDay}d ${validity.startHour}h - ${validity.endDay}d ${validity.endHour}"
        return getCommon(taf, inline).replace("null", "")
    }

    fun getCommon(container: AbstractWeatherCode, inline: String): String {
        val sb = StringBuilder()
        sb.append(getAirport(container.airport))
        sb.append("\n").append(inline)
        getWind(container.wind).ifNotEmpty { sb.append("\n").append(it) }
        sb.append("\n").append(getVisibility(container.visibility))
        getVerticalVisibility(container.verticalVisibility).ifNotEmpty { sb.append("\n").append(it) }
        getWeatherConditions(container.weatherConditions).ifNotEmpty { sb.append("\n").append(it) }
        getClouds(container.clouds).ifNotEmpty { sb.append("\n").append(it) }
        getRemark(container.remark).ifNotEmpty { sb.append("\n").append(it) }
        sb.append("\n\n").append(container.message)
        return sb.toString()
    }

    fun convertSpeed(speed: Int, unit: String): Int {
        return when (unit.lowercase()) {
            "kt" -> (speed * 1.852).roundToInt()
            "mps" -> (speed * 3.6).roundToInt()
            else -> throw IllegalArgumentException()
        }
    }

    fun getAirport(airport: Airport): String =
        "${airport.name} ${airport.icao}(${airport.iata}) ${airport.altitude}"

    fun getWind(wind: Wind?): String {
        return if (wind == null) {
            ""
        } else {
            "wind: ${convertSpeed(wind.speed, wind.unit)} km/h ${wind.directionDegrees}(${wind.direction})"
        }
    }

    fun getVisibility(visibility: Visibility): String =
        "visibility: ${visibility.mainVisibility}"

    fun getVerticalVisibility(visibility: Int?): String {
        return if (visibility == null) {
            ""
        } else {
            "vertical visibility: ${visibility.toString()}"
        }
    }

    fun getRemark(remark: String?): String {
        return if (remark.isNullOrBlank()) {
            ""
        } else {
            "remark: $remark"
        }
    }

    fun getWeatherConditions(weatherCondition: List<WeatherCondition>): String {
        return weatherCondition.map {
            "${it.intensity?.let { it.name.lowercase() }} ${it.descriptive} ${it.phenomenons.joinToString(",")}"
        }.joinToString(",").trim()
    }

    fun getClouds(clouds: List<Cloud>): String {
        return clouds
            .sortedBy { it.height }
            .map {
                "${it.type} ${it.quantity} ${it.height}"
            }.joinToString(",").trim()
    }


}