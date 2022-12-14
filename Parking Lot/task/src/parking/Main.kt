package parking

data class Spot(
    val number: Int, var free: Boolean, var carColor: String?, var craRegNum: String?
) {
    override fun toString(): String = "${this.number} ${this.craRegNum} ${this.carColor}"
}

class ParkingLot(numberOfSpots: Int = 20) {
    private val lot: Array<Spot> = Array(numberOfSpots) { Spot(it + 1, true, null, null) }

    fun status(): String = if (lot.any { !it.free }) lot.filter { !it.free }.joinToString("\n")
    else "Parking lot is empty."

    fun park(info: String): String {
        val carInfo = info.split(" ")
        val regNum = carInfo[0]
        val color = carInfo[1]
        for (i in lot.indices) if (lot[i].free) {
            lot[i].free = false
            lot[i].carColor = color
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
                "park" -> lot.park(command.removePrefix("park "))

                "leave" -> lot.leave(command.removePrefix("leave ").toInt())

                "status" -> lot.status()

                else -> "Invalid command!"
            }
        ) else println("Sorry, a parking lot has not been created.")
    }
}
