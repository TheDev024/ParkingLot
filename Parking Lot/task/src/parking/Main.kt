package parking

data class Spot(
    val number: Int, var free: Boolean, var carColor: String?, var craRegNum: String?
) {
    override fun toString(): String = "${this.number} ${this.craRegNum} ${this.carColor}"
}

class ParkingLot(numberOfSpots: Int = 20) {
    private val lot: Array<Spot>

    init {
        this.lot = Array(numberOfSpots) { Spot(it + 1, true, null, null) }
    }

    fun status(): String = if (lot.any { !it.free }) lot.filter { !it.free }.joinToString("\n")
    else "Parking lot is empty."

    fun findByRegNum(regNum: String): String = lot.firstOrNull { it.craRegNum == regNum }?.number?.toString()
        ?: "No cars with registration number $regNum were found."

    fun findCarsByColor(color: String): String =
        if (lot.any { it.carColor == color.lowercase() }) lot.filter { it.carColor == color.lowercase() }
            .map { it.craRegNum }.joinToString(", ") else "No cars with color $color were found."

    fun findSpotsByColor(color: String): String =
        if (lot.any { it.carColor == color.lowercase() }) lot.filter { it.carColor == color.lowercase() }
            .map { it.number }.joinToString(", ") else "No cars with color $color were found."


    fun park(regNum: String, color: String): String {
        for (i in lot.indices) if (lot[i].free) {
            lot[i].free = false
            lot[i].carColor = color.lowercase()
            lot[i].craRegNum = regNum
            return "$color car parked in spot ${i + 1}."
        }
        return "Sorry, the parking lot is full."
    }

    fun leave(spotNumber: Int): String {
        val index = spotNumber - 1
        return if (lot[index].free) "There is no car in spot $spotNumber." else {
            lot[index].free = true
            lot[index].carColor = null
            lot[index].craRegNum = null
            "Spot $spotNumber is free."
        }
    }
}

fun main() {
    var lot: ParkingLot? = null

    while (true) {
        val command = readln()
        val commands = command.split(" ")
        if (commands[0] == "create") {
            lot = ParkingLot(commands[1].toInt())
            println("Created a parking lot with ${commands[1]} spots.")
        } else if (command == "exit") break
        else if (lot != null) println(
            when (commands[0]) {
                "park" -> lot.park(commands[1], commands[2])

                "leave" -> lot.leave(commands[1].toInt())

                "status" -> lot.status()

                "reg_by_color" -> lot.findCarsByColor(commands[1])

                "spot_by_color" -> lot.findSpotsByColor(commands[1])

                "spot_by_reg" -> lot.findByRegNum(commands[1])

                else -> "Invalid command!"
            }
        ) else println("Sorry, a parking lot has not been created.")
    }
}
