package me.frenz.day04;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


class Day04Test {

    private final List<String> exampleInput = List.of(
            "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd",
            "byr:1937 iyr:2017 cid:147 hgt:183cm",
            "",
            "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884",
            "hcl:#cfa07d byr:1929",
            "",
            "hcl:#ae17e1 iyr:2013",
            "eyr:2024",
            "ecl:brn pid:760753108 byr:1931",
            "hgt:179cm",
            "",
            "hcl:#cfa07d eyr:2025 pid:166559648",
            "iyr:2011 ecl:brn hgt:59in",
            ""
    );

    @Test
    void testGetPassports_GivenExample() {
        final Iterator<String> passports = new Day04(exampleInput).getPassports().iterator();
        assertEquals("ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm", passports.next());
        assertEquals("iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884 hcl:#cfa07d byr:1929", passports.next());
        assertEquals("hcl:#ae17e1 iyr:2013 eyr:2024 ecl:brn pid:760753108 byr:1931 hgt:179cm", passports.next());
        assertEquals("hcl:#cfa07d eyr:2025 pid:166559648 iyr:2011 ecl:brn hgt:59in", passports.next());
        assertFalse(passports.hasNext());
    }

    @Test
    void testCountValidPassports_givenExample() {
        final Day04 dut = new Day04(exampleInput);
        assertEquals(2, dut.countCompletePassports());
    }
}
