import java.io.File

fun main() {
    val hands = File("day7.txt").readLines().map { parseLine(it) }
    val sortedHands = hands.sortedWith(HandComparator())
    val totalWinnings = sortedHands.mapIndexed { index, hand -> hand.bid * (index + 1) }.sum()
    println(totalWinnings)
}

data class Hand(val cards: String, val bid: Int, val handType: Int, val highCardOrder: List<Int>)

fun parseLine(line: String): Hand {
    val (cards, bid) = line.split(" ")
    val handType = determineHandType(cards)
    val highCardOrder = cards.map { cardRank(it) }

    return Hand(cards, bid.toInt(), handType, highCardOrder)
}

fun determineHandType(cards: String): Int {
    val cardCounts = cards.groupingBy { it }.eachCount()

    return when {
        cardCounts.values.maxOrNull() == 5 -> 7 // Five of a kind
        cardCounts.values.maxOrNull() == 4 -> 6 // Four of a kind
        cardCounts.values.contains(3) && cardCounts.values.contains(2) -> 5 // Full house
        cardCounts.values.maxOrNull() == 3 -> 4 // Three of a kind
        cardCounts.values.count { it == 2 } == 2 -> 3 // Two pair
        cardCounts.values.maxOrNull() == 2 -> 2 // One pair
        else -> 1 // High card
    }
}

fun cardRank(card: Char): Int {
    return when (card) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> 11
        'T' -> 10
        else -> card.toString().toInt()
    }
}

class HandComparator : Comparator<Hand> {
    override fun compare(h1: Hand, h2: Hand): Int {
        return if (h1.handType == h2.handType) {
            h1.highCardOrder.zip(h2.highCardOrder)
                .firstOrNull { it.first != it.second }?.let { it.first - it.second } ?: 0
        } else {
            h1.handType - h2.handType
        }
    }
}
