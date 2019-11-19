package com.rickyputrah.currencyrate.helper

import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyFormatterTest {

    @Test
    fun `should return 2 decimal point String when call double call currencyFormatter`() {
        //Given
        val doubleValue = 8.923534
        val expectedDoubleValue = "8.92"

        //When
        val result = doubleValue.currencyFormatter()

        //Then
        assertEquals(expectedDoubleValue, result)
    }

    @Test
    fun `should return doubleValue using , separator for every 3 number String when call double call currencyFormatter`() {
        //Given
        val doubleValue = 923534.23
        val expectedDoubleValue = "923,534.23"

        //When
        val result = doubleValue.currencyFormatter()

        //Then
        assertEquals(expectedDoubleValue, result)
    }

    @Test
    fun `should replace all , and convert string to double when call getCurrencyStringValue`() {
        //Given
        val doubleValue = 923534.23
        val expectedDoubleValue = "923,534.23"

        //When
        val result = expectedDoubleValue.getCurrencyStringValue()

        //Then
        assertEquals(doubleValue, result, 0.0)
    }
}