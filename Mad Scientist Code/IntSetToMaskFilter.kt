// Used when you have a set of numbers, and want a viable filter based on those numbers using a bit mask pass filter.
// Only loners pairs quads and octas are shown here.

// In other words. It's a solution to an optimization problem.

// Ex.... We want to make a filter for 0x7E0 and 0x7E1.
// This algorithim will a 2nd order filter scheme of 0x7E0 with a mask of 2.

// Ex2.... We want to make a filter for 0x7E0...0x7E3
// This algorithim will return a 3rd order filter scheme of 0x7E0 with a mask of 4.

// Very useful for Automotive CAN BUS Networks where you are only interested in some data, and want to remove bad results early.


// This is really a prototype algorithim. "It Works". I believe it can be reduced to a simpler form, where you spply an argument
// which determines the max 'order' of filter you want, or even go infinitely (or as many bits as you have).

// Additionally some systems allow "negative filters" which could optimize things further. This is not implemented.
// Ex... a 2nd order 0x7E0, and a 1st  order 0x7E2 is the same as a 3rd order 0x7E0 with a NEGATIVE 0x7E3 first order filter.

val listFilters = ArrayList<filterScheme>()
        // Return if there isn't any numbers
        if (original.isEmpty())
            return listFilters
        // Step one is to turn the string set into a list of ints, since its easy to sort

        val intArray = original.map { it.toInt(16)}.toIntArray()

        // Sort those keys
        Arrays.sort(intArray)

        // Make list for first list, and place the first number in it

        val octas = ArrayList<Int>()

        val quads = ArrayList<Int>()

        val pairs = ArrayList<Int>()

        val loners = ArrayList<Int>()

        var i = 0

        // Check for one loner
        if (i == intArray.size-1)
        {
            // Add that to loner, increment i.
            loners.add(intArray[i])
            i++
        }

        while (i < intArray.size-1)
        {
            if (intArray[i] xor intArray[i+1] == 1) {
                // This is a pair
                // Add one of the numbers to the pair list. It doesn't matter which one as long
                // As you always choose the first or last.
                pairs.add(intArray[i])

                // Increment i by 2
                i += 2
            }
            else
            {
                // This is not a pair, so add the current position to the loner list
                loners.add(intArray[i])
                i++
            }

            // We should always check if there is only 1 int left.
            // If that is the case, i will be equal to size -1
            if (i == intArray.size-1)
            {
                // Add that to loner, increment i.
                loners.add(intArray[i])
                i++
            }
        }

        /**
         * So now we have loners and pairs.
         * Next up is to see which pairs can be quads
         * In this case the xor will equal 2
         */

        i = 0
        while (i < pairs.size-1)
        {
            if (pairs[i] xor pairs[i+1] == 2)
            {
                // Remove this from pairs and add it to quads
                quads.add(pairs[i])
                pairs.removeAt(i+1)
                pairs.removeAt(i)
            }
            else
                i++
        }


        // octas
        i = 0
        while (i < quads.size-1)
        {
            if (quads[i] xor quads[i+1] == 4)
            {
                // Remove this from pairs and add it to quads
                octas.add(quads[i])
                quads.removeAt(i+1)
                quads.removeAt(i)
            }
            else
                i++
        }



        /**
         * Generate filter scheme
         */
        loners.forEach {
            listFilters.add(filterScheme(it.toString(16).toUpperCase(), "7FF"))
        }

        pairs.forEach {
            listFilters.add(filterScheme(it.toString(16).toUpperCase(), "7FE"))
        }
        quads.forEach {
            listFilters.add(filterScheme(it.toString(16).toUpperCase(), "7F8"))
        }
        octas.forEach {
            listFilters.add(filterScheme(it.toString(16).toUpperCase(), "7F0"))
        }



        HogLog.v(TAG, "List reduced from " + original.size + " to " + listFilters.size)

        return listFilters
