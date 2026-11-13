package com.mukunthan.urlshortener.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class Base62Test {

    @Test
    void encodesKnownValues() {
        assertEquals("0", Base62.encode(0));
        assertEquals("1", Base62.encode(1));
        assertEquals("Z", Base62.encode(61));        // last symbol
        assertEquals("10", Base62.encode(62));       // rolls over, like 9 -> 10 in decimal
        assertEquals("21", Base62.encode(125));      // our worked example
        assertEquals("4c92", Base62.encode(1_000_000));
    }

    @Test
    void decodeReversesEncode() {
        for (long n = 0; n < 100_000; n++) {
            assertEquals(n, Base62.decode(Base62.encode(n)));
        }
        long big = 3_521_614_606_207L; // 62^7 - 1, the largest 7-character code
        assertEquals(big, Base62.decode(Base62.encode(big)));
    }

    @Test
    void sevenCharactersHoldAbout3Trillion() {
        assertEquals(7, Base62.encode(3_521_614_606_207L).length());
        assertEquals(8, Base62.encode(3_521_614_606_208L).length());
    }

    @Test
    void rejectsNegativeNumbers() {
        assertThrows(IllegalArgumentException.class, () -> Base62.encode(-1));
    }

    @Test
    void rejectsInvalidCodes() {
        assertThrows(IllegalArgumentException.class, () -> Base62.decode("ab!c"));
        assertThrows(IllegalArgumentException.class, () -> Base62.decode(""));
    }
}