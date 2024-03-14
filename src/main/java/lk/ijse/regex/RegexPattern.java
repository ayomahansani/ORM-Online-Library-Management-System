package lk.ijse.regex;

import lombok.Getter;

import java.util.regex.Pattern;

public class RegexPattern {
    @Getter
    private static final Pattern namePattern = Pattern.compile("^[a-zA-Z '.-]{4,}$");

    @Getter
    private static final Pattern idPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");

    @Getter
    private static final Pattern registrationIdPattern = Pattern.compile("^[I][T][0-1]{1,}$");

    @Getter
    private static final Pattern emailPattern = Pattern.compile("(^[a-zA-Z0-9_.-]+)@([a-zA-Z]+)([\\.])([a-zA-Z]+)$");

    @Getter
    private static final Pattern cityPattern = Pattern.compile("[a-zA-Z]{4,}$");

    @Getter
    private static final Pattern mobilePattern = Pattern.compile("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");

    @Getter
    private static final Pattern addressPattern = Pattern.compile("^[A-Za-z0-9'\\/\\.\\,]{5,}$");

    @Getter
    private static final Pattern postalCodePattern = Pattern.compile("^\\d{5}$");

    @Getter
    private static final Pattern oldIDPattern = Pattern.compile("^[0-9]{9}[vVxX]$");

    @Getter
    private static final Pattern doublePattern = Pattern.compile("^[0-9]+\\.?[0-9]*$");

    @Getter
    private static final Pattern intPattern = Pattern.compile("^[1-9][0-9]?$|^100$");

}