package csense.kotlin


//TODO FloorMod from csense 0.0.60 instead
public infix fun Int.floorMod(to: Int): Int = ((this % to) + to) % to
