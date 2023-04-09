import com.example.myprojectapplication.model.Weather
import com.google.gson.annotations.SerializedName

data class HourlyForecast (
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ListElement>,
    val city: City
)

data class City (
    val id: Long,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Long,
    val timezone: Long,
    val sunrise: Long,
    val sunset: Long
)

data class Coord (
    val lat: Double,
    val lon: Double
)

data class ListElement (
    val dt: Long,
    val main: MainClass,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: WindForecast,
    val visibility: Long,
    val pop: Double,
    val sys: SysHourlyForecast,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val rain: Rain? = null
)

data class Clouds (
    val all: Long
)

data class MainClass (
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Long,
    @SerializedName("sea_level")
    val seaLevel: Long,
    @SerializedName("grnd_level")
    val grndLevel: Long,
    val humidity: Long,
    @SerializedName("temp_kf")
    val tempKf: Double
)

data class Rain (
    @SerializedName("1h")
    val the1H: Double
)

data class SysHourlyForecast (
    val pod: Pod
)

enum class Pod {
    D,
    N
}


data class WindForecast (
    val speed: Double,
    val deg: Long,
    val gust: Double
)