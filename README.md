# Polyfun for Java 11

Refactored version of David Gomprecht's polyfun library. You can download the JAR in out/artifacts/polyfun_v_11/polyfun_v_11.jar

## Key changes
* Compiled in Java 11
* Updated variable names to be descriptive, e.g. double var2 became double numericalCoefficient. (Note that because attributes are private and access is only through the getters and setters, the interface for the user does not change.)
* Added getters and setters to reflect new variable names, e.g. getDouble became getNumericalCoeffienct. (The old ones were not removed, but marked as deprecated.)
* Added setters where they were missing.
* Simplified "if (cond) return true else return false" to "return cond"
* For objects that used default constructor then set the attributes separately, changed to use a constructor that sets all the attributes.
* Statement that appeared in all if-if-else-else blocks were pulled out of if block.
* To copy arrays, replaced a for-loop with System.arraycopy
* Added toString() methods and put deprecation warning for print() methods.
* Added equals() methods and put deprecation warning on identicalTo() methods.
* Replaced use of deprecated methods with their newer counterparts.
* Added Junit tests
