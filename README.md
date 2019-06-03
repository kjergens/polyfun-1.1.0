# Polyfun for Java 11

Refactored version of David Gomprecht's polyfun library which was compiled in Java 6.

Download [polyfun_v_11.jar](https://github.com/kjergens/polyfun11/raw/master/out/artifacts/polyfun_v_11/polyfun_v_11.jar).

## Change log
These changes were made to be backward compatible. In other words, old XClass code will work with this updated library.
* Compiled in Java 11
* Updated variable names to be descriptive, e.g. `double var2` became `double numericalCoefficient`.
  * Note that attributes are private with access only through getters and setters, therefore changing attribute names does not affect the user interface.
  * Parameter name changes will help users know what to pass in.
* Added getters and setters to reflect new variable names, e.g. `getDouble()` became `getNumericalCoefficient()`.
  * Note that the old ones were not removed, but they were marked as deprecated.
* Added setters where they were missing.
* Simplified `if (<condition>) return true; else return false;` to `return <condition>;`.
  * Note IntelliJ code analyzer recommended this change.
* Where the code instantiated an object by using the default constructor then set the attributes individually using setters, changed to using a constructor that sets all the attributes.
* Where the code had a statement that appeared in all if-if-else-else blocks, that statement was pulled out to below the if-else block.
* Where the code used for-loops to copy arrays, replaced with System.arraycopy.
  * Note IntelliJ code analyzer recommended this change.
* Where the code looked for a non-zero value in an array, updated it from counting all the non-zero elements then returning true if count > 0, to instead return false at the first occurrence of a non-zero element.
* Where the code declared and instantiated an object and then returning the new object, changed to instantiating an anonymous object in the return statement.
* Added toString() methods and put deprecation warning for print() methods.
* Added equals() methods and put deprecation warning on identicalTo() methods.
* Throughout the library, replaced use of deprecated methods with their newer counterparts.
* Auto-reformatted code.
* Added Junit tests
