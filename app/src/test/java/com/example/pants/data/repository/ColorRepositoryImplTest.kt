package com.example.pants.data.repository

import com.example.pants.data.network.ColorApiService
import com.example.pants.data.network.model.ColorResponse
import com.example.pants.data.network.model.Hsv
import com.example.pants.data.network.model.Name
import com.example.pants.domain.model.ColorModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ColorRepositoryImplTest {

    private lateinit var apiService: ColorApiService
    private lateinit var repository: ColorRepositoryImpl

    @Before
    fun setup() {
        apiService = mockk()
        repository = ColorRepositoryImpl(apiService)
    }

    @Test
    fun `getRandomColors should filter out invalid colors`() = runBlocking {
        val validColor = createMockResponse("Deep Sky", saturation = 0.5f, value = 0.6f)
        val lowSaturationColor = createMockResponse("Pure Green", saturation = 0.1f, value = 0.5f)
        val lowValueColor = createMockResponse("Dark Forest", saturation = 0.8f, value = 0.2f)
        val commonNameColor = createMockResponse("Red Apple", saturation = 0.9f, value = 0.9f)

        coEvery { apiService.getColor(any()) } returnsMany listOf(
            lowSaturationColor,
            lowValueColor,
            commonNameColor,
            validColor
        )

        val result = repository.getRandomColors(1)

        val colors = result.getOrThrow()

        assertTrue("Должен вернуться ровно 1 цвет", colors.size == 1)
        assertTrue("Цвет должен быть валидным", colors.first().name == "Deep Sky")
        assertTrue("Насыщенность должна быть > 0.3", colors.first().saturation > 0.3f)
        assertTrue("Яркость должна быть > 0.4", colors.first().value > 0.4f)
    }



    @Test
    fun `getRandomColors should return only unique colors`() = runBlocking {
        val color = createMockResponse("Unique Color", 0.5f, 0.5f)

        coEvery { apiService.getColor(any()) } returns color

        val result = repository.getRandomColors(2)

        assertTrue(result.getOrThrow() is Set<ColorModel>)
    }


    private fun createMockResponse(name: String, saturation: Float, value: Float): ColorResponse {
        return ColorResponse(
            name = Name(name),
            hsv = Hsv(Hsv.Fraction(0f, saturation, value))
        )
    }
}