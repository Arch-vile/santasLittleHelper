package utils

import model.Child
import java.util.*

fun factorial(number: Int): Long {
    var result: Long = 1

    for (factor in 2..number) {
        result *= factor.toLong()
    }

    return result
}


fun permutations(items: List<Child>, handler: (Stack<Child>) -> Unit) {
    println("Finding ${factorial(items.size)} permutations")
    return permutations(items.toMutableList(), Stack(), items.size, handler)
}


fun permutations(items: MutableList<Child>, permutation: Stack<Child>, size: Int, handler: (Stack<Child>) -> Unit) {

    /* permutation stack has become equal to size that we require */
    if (permutation.size == size) {
        /* print the permutation */
        //println(Arrays.toString(permutation.toTypedArray()))
        handler(permutation)
    }

    /* items available for permutation */
    val availableItems = items.toTypedArray()
    for (i in availableItems) {
        /* add current item */
        permutation.push(i)

        /* remove item from available item set */
        items.remove(i)

        /* pass it on for next permutation */
        permutations(items, permutation, size, handler)

        /* pop and put the removed item back */
        items.add(permutation.pop())
    }
}
