package day4

import day1.readLines
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun main() {
    val lines = readLines("/day4/1.txt")
        .map(::parseLine)
        .sortedBy { it.dateTime }

    val asleepByGuard = mutableMapOf<Int, IntArray>()

    var currentGuard = -1
    var fellAsleep = LocalTime.MIN

    fun getAsleep() = asleepByGuard.getOrPut(currentGuard) { IntArray(60) }

    for (event in lines) {
        when (event) {
            is BeginsShift -> {
                currentGuard = event.id
                getAsleep()
            }
            is FallsAsleep -> fellAsleep = event.dateTime.toLocalTime()
            is WakesUp -> {
                val asleep = getAsleep()
                for (minute in fellAsleep.minute until event.dateTime.minute) {
                    asleep[minute]++
                }
            }
        }
    }

    // strategy 1
    val entry1 = asleepByGuard.maxBy { it.value.sum() }!!
    val minute1 = entry1.value.indexOf(entry1.value.max()!!)
    println(minute1 * entry1.key)

    // strategy 2
    val entry2 = asleepByGuard.maxBy { it.value.max()!! }!!
    val minute2 = entry2.value.indexOf(entry2.value.max()!!)
    println(minute2 * entry2.key)
}

private sealed class Event(val dateTime: LocalDateTime)

private class FallsAsleep(dt: LocalDateTime) : Event(dt)
private class WakesUp(dt: LocalDateTime) : Event(dt)
private class BeginsShift(val id: Int, dt: LocalDateTime) : Event(dt)

private val regexBeginsShift = "Guard #([0-9]+) begins shift".toRegex()

private fun parseLine(it: String): Event {
    val (_, dt, message) = it.split("[", "] ")
    val dateTime = parseDateTime(dt)

    regexBeginsShift.matchEntire(message)?.let {
        return BeginsShift(it.groupValues[1].toInt(), dateTime)
    }

    if (message == "wakes up") return WakesUp(dateTime)
    return FallsAsleep(dateTime)
}

private fun parseDateTime(dt: String) =
    LocalDateTime.parse(dt.replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
