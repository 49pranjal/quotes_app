package com.example.quotesapp

import com.example.quotesapp.apiservice.QuoteService
import com.example.quotesapp.data.Quotes
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuoteServiceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var quoteService: QuoteService
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        quoteService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(QuoteService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.close()
    }

    @Test
    fun testGetQuotesData() = runTest{
        val mockResponse = MockResponse()
        val quotes = Quotes(
            data = emptyList(),
            source = emptyList()
        )
        val gson = Gson().toJson(quotes)
        mockResponse.setBody(gson)
        mockWebServer.enqueue(mockResponse)

        val response = quoteService.getQuotesData("Nation", "Population", "2013")
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body()!!.data.isEmpty())
        Assert.assertEquals(true, response.body()!!.source.isEmpty())
    }

    @Test
    fun testGetQuotesData_expectedData() = runTest{
        val mockResponse = MockResponse()
        val data = Helper.getDataFromFile("/testQuotes.json")
        //val gson = Gson().toJson(populationYearWise)
        mockResponse.setBody(data)
        mockWebServer.enqueue(mockResponse)

        val response = quoteService.getQuotesData("Nation", "Population", "2013")
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.body()!!.data.isEmpty())
        Assert.assertEquals(true, response.body()!!.source.isEmpty())
        Assert.assertEquals(3, response.body()!!.data.size)
    }

    @Test
    fun testGetQuotesData_expectedError() = runTest{
        val mockResponse = MockResponse()
        //val data = Helper.getDataFromFile("/testQuotes.json")
        //val gson = Gson().toJson(populationYearWise)
        mockResponse.setBody("Something Went Wrong")
        mockResponse.setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        val response = quoteService.getQuotesData("Nation", "Population", "2013")
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }
}