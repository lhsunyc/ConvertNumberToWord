import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the Converter class
 */
public class ConverterTest {

    private Converter converter;

    @Before
    public void setUp() {
        converter = new Converter();
    }

    @Test
    public void convertToWordsForZeroNumber() {
        assertEquals("Zero", converter.convertToWords("0"));
    }

    @Test
    public void convertToWordsForIntegerNumber() {
        assertEquals("One Dollar", converter.convertToWords("1"));
    }

    @Test
    public void convertToWordsForIntegerNumberWithPluralUnit() {
        assertEquals("Two Dollars", converter.convertToWords("2"));
    }

    @Test
    public void convertToWordsForDecimalNumber() {
        assertEquals("One Cent", converter.convertToWords("0.01"));
    }

    @Test
    public void convertToWordsForDecimalNumberWithPluralUnit2() {
        assertEquals("Two Cents", converter.convertToWords("0.02"));
    }

    @Test
    public void testBoundaryValue() {
        assertEquals("Ninety Nine Cents", converter.convertToWords("0.99"));
    }

    @Test
    public void testBoundaryValue2() {
        assertEquals("One Dollar and One Cent", converter.convertToWords("1.01"));
    }

    @Test
    public void testBoundaryValue3() {
        assertEquals("Nine Hundred Ninety Nine Trillion Nine Hundred Ninety Nine Billion Nine Hundred Ninety Nine Million Nine Hundred Ninety Nine Thousand Nine Hundred Ninety Nine Dollars and Ninety Nine Cents", converter.convertToWords("999999999999999.99"));
    }

    @Test
    public void testIntegerWithZeros() {
        assertEquals("One Million Two Hundred Thirty Dollars", converter.convertToWords("1000230"));
    }

    @Test
    public void testIntegerWithZeros2() {
        assertEquals("One Thousand Dollars", converter.convertToWords("1000"));
    }

    @Test
    public void testIntegerWithZeros3() {
        assertEquals("One Trillion Dollars", converter.convertToWords("1000000000000"));
    }

    @Test
    public void testIntegerWithZeros4() {
        assertEquals("One Trillion One Billion One Million One Thousand One Dollars", converter.convertToWords("1001001001001"));
    }

    @Test
    public void testTooLargeInteger() {
        assertEquals("too large", converter.convertToWords("9999999999999999.99"));
    }
}