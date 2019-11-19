package com.rickyputrah.currencyrate.helper

import android.view.View
import org.junit.Assert.assertEquals
import org.junit.Test

class VisibilityHelperTest {


    @Test
    fun `should return Visibility GONE when call toVisibility and boolean false`() {
        //Given
        val booleanValue = false
        val expectedVisibility = View.GONE

        //When
        val result = booleanValue.toVisibility()

        //Then
        assertEquals(expectedVisibility, result)
    }


    @Test
    fun `should return Visibility VISIBLE when call toVisibility and boolean true`() {
        //Given
        val booleanValue = true
        val expectedVisibility = View.VISIBLE

        //When
        val result = booleanValue.toVisibility()

        //Then
        assertEquals(expectedVisibility, result)
    }
}