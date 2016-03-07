package by.bsu.idxparser

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import org.junit.Assert.assertNotNull
import java.io.IOException

/**
 * @author Dmitry Pranchuk
 * @since 3/7/16.
 */
class IDXTest {

    val LABELS_PATH = "resources/labels"

    val NUMBERS_PATH = "resources/numbers"

    @Test
    fun readNumbersAsFile() {
        val idx = IDX(File(NUMBERS_PATH), File(LABELS_PATH))
        assertNotNull(idx.next())
    }

    @Test
    fun readNumbersAsPath() {
        val idx = IDX(NUMBERS_PATH, LABELS_PATH)
        assertNotNull(idx.next())
    }

    @Test
    fun shouldReadAllNumbers() {
        val idx = IDX(NUMBERS_PATH, LABELS_PATH)
        assertEquals(idx.numbersCount, 10000)
        var numbersCount = 0
        idx.forEach { numbersCount += 1 }
        assertEquals(numbersCount, 10000)
    }

    @Test(expected = IOException::class)
    fun shouldCorrectlyClose() {
        val idx = IDX(NUMBERS_PATH, LABELS_PATH)
        idx.use { numbers ->
            numbers.next()
        }
        idx.next()
    }

}