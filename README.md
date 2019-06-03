# polyfun11

Refactored version of David Gomprecht's polyfun library.

## Key changes
* Compiled in Java 11
* Variable names updated, e.g. var2 became numericalCoefficient
* New getters and setters to reflect new variable names. (The old ones were not removed, but marked deprecated.)
* Simplified "if (cond) return true else return false" to "return cond"
* For objects that used default constructor then set the attributes separately, changed to use a constructor that sets all the attributes.
* Statement that appeared in all if-if-else-else blocks were pulled out of if block.
* To copy arrays, replaced a for-loop with System.arraycopy
* Added toString methods
* Marked methods with old names as deprecated
* Added Junit tests
