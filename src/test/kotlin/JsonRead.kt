import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.io.path.Path
import kotlin.io.path.inputStream

fun main() {
    val readTree = ObjectMapper().readTree(Path("id.json").inputStream())
    for (node in readTree) {
        println("implementation(${node["name"]})")
    }
}