package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> = this.allDrivers.filter { driver -> !trips.any { it.driver == driver } }.toSet()


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> = allPassengers.filter { passenger -> trips.
        sumBy{trip -> trip.passengers.count { it == passenger } } >= minTrips}.toSet()


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> = allPassengers.filter { passenger -> trips.
        sumBy { trip -> trip.passengers.count { trip.driver == driver && it == passenger}} > 1 }.toSet()


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> = allPassengers.filter { passenger ->
    trips.filter { passenger in it.passengers && it.discount != null }.count() >
            trips.filter { passenger in it.passengers && it.discount == null }.count()
}.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

    return if (trips.isEmpty()) {
        null
    } else {

        val maxDuration = trips.map { it.duration }.max() ?: 0
        val mapNumberTrips = HashMap<Int, IntRange>()
        for (i in 0..maxDuration step 10) {
            val range = IntRange(i, i + 9)
            val noOfTripsInRange = trips.filter { it.duration in range }.count()
            mapNumberTrips[noOfTripsInRange] = range

        }

        mapNumberTrips[mapNumberTrips.toSortedMap().lastKey()]
    }

}

    /*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
    fun TaxiPark.checkParetoPrinciple(): Boolean {

        if (trips.isEmpty()){
            return false
        }else{

            val totalCost = trips.map { it.cost }.sum()
            val mapCostByDriverSorted =  trips
                    .groupBy { it.driver }
                    .mapValues { (_, trips) -> trips.sumByDouble { it.cost }}
                    .toList()
                    .sortedByDescending { (_, value) -> value}.toMap()

           // print(mapCostByDriverSorted)

            var currentSum = 0.0
            var numberDriver = 0

            for (value in mapCostByDriverSorted.values){
                numberDriver++
                currentSum += value

                if (currentSum >= (totalCost * 0.8)) break
            }

            return numberDriver <= allDrivers.size * 0.2

        }
    }

