package org.example.cm.exer_3


fun main() {
    val heightsSequence = generateSequence(10000.0) { if ((it*0.6)>1) it * 0.6 else null }

    for (height in heightsSequence.toList().subList(0,15)) {
        print("%.2f ".format(height))
    }
}