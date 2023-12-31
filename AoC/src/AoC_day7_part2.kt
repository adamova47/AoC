//import java.io.File
//
//fun main() {
//    val hands = File("day7.txt").readLines().map { parseLine(it) }
//    val sortedHands = hands.sortedWith(HandComparator())
//    val totalWinnings = sortedHands.mapIndexed { index, hand -> hand.bid * (index + 1) }.sum()
//    println(totalWinnings)
//}
//
//data class Hand(val cards: String, val bid: Int, val handType: Int, val highCardOrder: List<Int>)
//
//fun parseLine(line: String): Hand {
//    val (cards, bid) = line.split(" ")
//    val handType = determineHandType(cards)
//    val highCardOrder = cards.map { cardRank(it) }
//
//    return Hand(cards, bid.toInt(), handType, highCardOrder)
//}
//
//
//fun determineHandType(cards: String): Int {
//    val cardCounts = cards.groupingBy { it }.eachCount().filterKeys { it != 'J' }
//    val jokerCount = cards.count { it == 'J' }
//    val sortedCounts = cardCounts.values.sorted()
//    val highest = (sortedCounts.lastOrNull() ?: 0) + jokerCount
//    val secondHighest = sortedCounts.dropLast(1).lastOrNull() ?: 0
//
//    return when {
//        highest >= 5 -> 7
//        highest == 4 -> 6
//        highest == 3 && secondHighest >= 2 -> 5
//        highest == 3 -> 4
//        highest == 2 && secondHighest == 2 -> 3
//        highest == 2 -> 2
//        else -> 1
//    }
//}
//
//fun cardRank(card: Char): Int {
//    return when (card) {
//        'A' -> 14
//        'K' -> 13
//        'Q' -> 12
//        'T' -> 10
//        'J' -> 1
//        else -> card.toString().toInt()
//    }
//}
//
//class HandComparator : Comparator<Hand> {
//    override fun compare(h1: Hand, h2: Hand): Int {
//        if (h1.handType == h2.handType) {
//            return h1.highCardOrder.zip(h2.highCardOrder)
//                .firstOrNull { it.first != it.second }?.let { it.first - it.second } ?: 0
//        }
//        return h1.handType - h2.handType
//    }
//}
