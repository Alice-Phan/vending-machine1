
package com.sal.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author salajrawi
 */
public enum Coins {
    PENNY(new BigDecimal(0.01)),
    NICKLE(new BigDecimal(0.05)),
    DIME(new BigDecimal(0.1)),
    QUARTER(new BigDecimal(0.25));

    // Declare value variable
    private final BigDecimal value;

    // Modifier private is redundant for enum constructors so we can remove private in this case
    private Coins(BigDecimal value) {

        this.value = value;
    }

    // Return the value of a specific Coins enum (QUARTER, DIME, NICKEL, PENNY)
    public BigDecimal getValue()
    {

        return value.setScale(2, RoundingMode.DOWN);
    }
}
