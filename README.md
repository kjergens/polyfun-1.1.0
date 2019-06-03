# Polyfun for Java 11

Refactored version of David Gomprecht's polyfun library.

Download [polyfun_v_11.jar](out/artifacts/polyfun_v_11/polyfun_v_11.jar)

## Change log
Note these changes were made to be backward compatible with code that uses the previous version. In other words, no methods were deleted and no method signatures were changed.
* Compiled in Java 11
* Updated variable names to be descriptive, e.g. `double var2` became `double numericalCoefficient`.
..*Note that because attributes are private with access only through getters and setters, the user interface does not change.
* Added getters and setters to reflect new variable names, e.g. `getDouble()` became `getNumericalCoefficient()`.
.*Note that the old ones were not removed, but they were marked as deprecated.
* Added setters where they were missing.
* Simplified `if (<condition>) return true; else return false;` to `return <condition>;`.
..*Note IntelliJ code analyzer recommended this change.
* Where the code instantiated an object by using the default constructor then set the attributes individually using setters, changed to using a constructor that sets all the attributes.
* Where the code had a statement that appeared in all if-if-else-else blocks, that statement was pulled out to below the if-else block.
* Where the code used for-loops to copy arrays, replaced with System.arraycopy.
..*Note: IntelliJ code analyzer recommended this change.
* Where the code looked for a non-zero value in an array, updated it from counting all the non-zero elements then returning true if count > 0, to instead return false at the first occurence of a non-zero element.
* Where the code declared and instantiated an object and then returning the new object, changed to instantiating an anonymous object in the return statement.
* Added toString() methods and put deprecation warning for print() methods.
* Added equals() methods and put deprecation warning on identicalTo() methods.
* Replaced use of deprecated methods with their newer counterparts.
* Auto-reformatted code.
* Added Junit tests
