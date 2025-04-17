# Airport Weather Bot

A Telegram bot that provides METAR (Meteorological Aerodrome Report) and TAF (Terminal Aerodrome Forecast) information for airports worldwide. The bot allows users to get up-to-date weather information for any airport using either ICAO or IATA codes.

Live bot: [AirportWeatherBot](https://t.me/AirportWeatherBot)

## Features

- Get METAR information for any airport using ICAO or IATA code
- Get TAF information for any airport using ICAO or IATA code
- Repeat the last command with a simple `/r` command
- View raw METAR/TAF messages
- Support for inline queries to get weather information in any chat
- Caching of IATA to ICAO mappings for faster lookups

## Usage

### Commands

- `/metar <code>` or `/m <code>` - Get METAR information for an airport
- `/taf <code>` or `/t <code>` - Get TAF information for an airport
- `/r` - Repeat the last command

### Examples

```
/metar KJFK  # Get METAR for John F. Kennedy International Airport using ICAO code
/m JFK       # Get METAR for John F. Kennedy International Airport using IATA code
/taf EGLL    # Get TAF for London Heathrow Airport using ICAO code
/t LHR       # Get TAF for London Heathrow Airport using IATA code
/r           # Repeat the last command
```

### Inline Queries

You can also use the bot in inline mode by typing `@AirportWeatherBot <code>` in any chat. This will show both METAR and TAF information for the specified airport.

## Installation and Deployment

### Prerequisites

- JDK 21 or higher
- Gradle
- Redis server
- Telegram Bot Token

### Environment Variables

- `BOT_TOKEN` - Your Telegram Bot Token
- `REDIS_URL` - URL for your Redis server

### Building and Running Locally

1. Clone the repository
2. Set the required environment variables
3. Build the project:
   ```
   ./gradlew build
   ```
4. Run the application:
   ```
   ./gradlew run
   ```

### Docker Deployment

The project includes a Dockerfile for easy deployment:

1. Build the Docker image:
   ```
   docker build -t airport-weather-bot .
   ```
2. Run the container:
   ```
   docker run -e BOT_TOKEN=your_bot_token -e REDIS_URL=your_redis_url airport-weather-bot
   ```

## Dependencies

- [TgBotAPI](https://github.com/InsanusMokrassar/TelegramBotAPI) - Kotlin Telegram Bot API
- [MetarParser](https://github.com/mivek/metarParser) - Library for parsing METAR and TAF messages
- [Arrow](https://arrow-kt.io/) - Functional programming library for Kotlin
- [Kreds](https://github.com/crackthecodeabhi/kreds) - Kotlin Redis client
- [Kotlin CSV](https://github.com/doyaaaaaken/kotlin-csv) - CSV processing library for Kotlin
- [Guava](https://github.com/google/guava) - Google core libraries for Java
- [KotliQuery](https://github.com/seratch/kotliquery) - A lightweight SQL library for Kotlin

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

Alexey Fedechkin
