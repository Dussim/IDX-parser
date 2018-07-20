package by.bsu.idxparser

import java.io.Closeable
import java.io.DataInputStream
import java.io.File

/**
 * @author Dmitry Pranchuk
 * @since 3/7/16.
 */
class IDX(val dataFile: InputStream, val labelFile: InputStream) : Iterator<Pair<Int, Array<Int>>>, Closeable {

    constructor(dataPath: String, labelPath: String) : this(FileInputStream(dataPath), FileInputStream(labelPath))

    private val numberBytes = DataInputStream(BufferedInputStream(dataFile))
    private val labelBytes = DataInputStream(BufferedInputStream(labelFile))
    val numberOfCols: Int
    val numberOfRows: Int
    val numbersCount: Int
    val labelsCount: Int
    init {
        val NUMBERS_MAGIC_NUMBER = 2051
        val LABEL_MAGIC_NUMBER = 2049
        checkMagicNumber(numberBytes, NUMBERS_MAGIC_NUMBER)
        checkMagicNumber(labelBytes, LABEL_MAGIC_NUMBER)
        numbersCount = numberBytes.readInt()
        labelsCount = labelBytes.readInt()
        numberOfRows = numberBytes.readInt()
        numberOfCols = numberBytes.readInt()
    }

    override fun close() {
        numberBytes.close()
        labelBytes.close()
    }

    override fun hasNext() = numberBytes.available() > 0


    override fun next(): Pair<Int, Array<Int>> {
        val label = ubyte(labelBytes.readByte())
        val pixels = Array(numberOfCols * numberOfRows, { ubyte(numberBytes.readByte()) })
        return Pair(label, pixels)
    }

    private fun ubyte(byte: Byte) = byte.toInt() and 0xFF

    private fun checkMagicNumber(dataInputStream: DataInputStream, magicNumber: Int) {
        if (dataInputStream.readInt() != magicNumber) throw RuntimeException("Incorrect magic number")
    }
}
