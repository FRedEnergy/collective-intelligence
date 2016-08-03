import java.lang.Math.*
import java.util.*

/**
 * Implementation of Euclidean and Pearson algorithms based on Collective Intelligence book by Toby Segaran
 */


/**
 * Example data on which algorithms are being proofed
 */
val ratings = mapOf(
        "Lisa" to mapOf(
                "Lady in the Water" to 2.5,
                "Snakes on a Plane" to 3.5,
                "Just My Luck" to 3.0,
                "Superman Returns" to 3.5,
                "You, Me and Dupree" to 2.5,
                "The Night Listener" to 3.0
        ),
        "Gene" to mapOf(
                "Lady in the Water" to 3.0,
                "Snakes on a Plane" to 3.5,
                "Just My Luck" to 1.5,
                "Superman Returns" to 5.0,
                "The Night Listener" to 3.0,
                "You, Me and Dupree" to 3.5
        ),
        "Michael" to mapOf(
                "Lady in the Water" to 2.5,
                "Snakes on a Plane" to 3.0,
                "Superman Returns" to 3.5,
                "The Night Listener" to 4.0
        ),
        "Claudia" to mapOf(
                "Snakes on a Plane" to 3.5,
                "Just My Luck" to 3.0,
                "The Night Listener" to 4.5,
                "Superman Returns" to 4.0,
                "You, Me and Dupree" to 2.5
        ),
        "Mick" to mapOf(
                "Lady in the Water" to 3.0,
                "Snakes on a Plane" to 4.0,
                "Just My Luck" to 2.0,
                "Superman Returns" to 3.0,
                "The Night Listener" to 3.0,
                "You, Me and Dupree" to 2.0
        ),
        "Jack" to mapOf(
                "Lady in the Water" to 3.0,
                "Snakes on a Plane" to 4.0,
                "The Night Listener" to 3.0,
                "Superman Returns" to 5.0,
                "You, Me and Dupree" to 3.5),
        "Toby" to mapOf(
                "Snakes on a Plane" to 4.5,
                "You, Me and Dupree" to 1.0,
                "Superman Returns" to 4.0))
        
fun main(args: Array<String>){
    Scanner(System.`in`).use {
        while(true){
            val line = readLine() ?: break
            val (personA, personB) = line.split(" ")
            println("Euclidean ${calculateEuclideanSimilarity(personA, personB)}")
            println("Pearson ${calculatePearsonSimilarity(personA, personB)}")
        }
    }
}

/**
 * Calculates similarity based on euclidean distance
 * @return [0; 1]
 */
fun calculateEuclideanSimilarity(personA: String, personB: String): Double {
    val similarItems = ratings[personA]!!.filter { it.key in ratings[personB]!! }.keys

    if(similarItems.isEmpty())
        return 0.0 //no similar items at all

    val sum = similarItems.map { (ratings[personA]!![it]!! - ratings[personB]!![it]!!).pow(2.0) }.sum()

    return 1 / (1 + sum)
}

/**
 * Calculates similarity using Pearson correlation coefficient
 * @return [-1;1]
 */
fun calculatePearsonSimilarity(personA: String, personB: String): Double{
    val similarItems = ratings[personA]!!.filter { it.key in ratings[personB]!! }.keys
    val n = similarItems.size
    if(n == 0)
        return 0.0

    //sum of the ratings
    val sumA = similarItems.map { ratings[personA]!![it]!! }.sum()
    val sumB = similarItems.map { ratings[personB]!![it]!! }.sum()

    //sum of the squares
    val sumASq = similarItems.map { ratings[personA]!![it]!!.pow(2.0) }.sum()
    val sumBSq = similarItems.map { ratings[personB]!![it]!!.pow(2.0) }.sum()

    val sumP = similarItems.map { ratings[personA]!![it]!! * ratings[personB]!![it]!! }.sum()

    val num = sumP - (sumA * sumB / n)
    val den = ((sumASq - sumA.pow(2.0) / n) * (sumBSq - sumB.pow(2.0) / n)).sqrt()
    if(den == 0.0)
        return 0.0
    else
        return num / den

}

fun Double.pow(p: Double) = pow(this, p)

fun Double.sqrt() = sqrt(this)