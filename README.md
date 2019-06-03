# polyfun11

Refactored version of David Gomprecht's polyfun library.

## Key changes
* Compiled in Java 11
* All variable names updated, e.g. var2 became numericalCoefficient
* Getters and setters updated to reflect new variable names
* Simplified if blocks, e.g. if (cond) return true else return false became return cond
* Simplified object construction, e.g. using default constructor then setting the attributes separately became using the proper constructor
* When a statement appeared in all if-if-else-else blocks, pulled it out of if block.
* Replaced a for-loop to copy arrays with System.arraycopy
* Added toString methods
* Marked methods with old names as deprecated
* Added Junit tests
