import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import com.example.myapplication.data.model.CurrenciesEntity
import com.example.myapplication.data.model.SymbolsResponse
import com.example.myapplication.domain.repository.CurrenciesRepository
import com.example.myapplication.domain.repository.DatabaseRepository
import com.example.myapplication.ui.CurrenciesViewModel
import com.example.myapplication.ui.filter.FilterOption
import com.example.myapplication.ui.models.FavouritesUiModel
import com.example.myapplication.ui.models.toUiModel
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

@OptIn(ExperimentalCoroutinesApi::class)
class CurrenciesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val currenciesRepository: CurrenciesRepository = mock(CurrenciesRepository::class.java)
    private val sharedPreferences: SharedPreferences = mock(SharedPreferences::class.java)
    private val databaseRepository: DatabaseRepository = mock(DatabaseRepository::class.java)
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CurrenciesViewModel

    @Before
    fun setup() {
        viewModel = CurrenciesViewModel(
            currenciesRepository,
            sharedPreferences,
            databaseRepository,
            testDispatcher
        )
    }

    @Test
    fun `test loadInitialData indirectly via init`() = runTest(testDispatcher) {
        // Mocking response for the repository and SharedPreferences
        val mockCurrencies = mapOf("USD" to "United States Dollar", "EUR" to "Euro")
        val mockDefaultCurrency = "USD"
        val symbolsResponse = SymbolsResponse(
            success = true,
            symbols = mockCurrencies
        )
        val mockEntities = listOf(
            CurrenciesEntity(0, "USD", "EUR", 1.0),
            CurrenciesEntity(1, "EUR", "USD", 0.9)
        )

        // Mocking repository methods
        `when`(currenciesRepository.fetchCurrencies()).thenReturn(symbolsResponse)
        `when`(sharedPreferences.getString(anyString(), anyString())).thenReturn(mockDefaultCurrency)
        `when`(databaseRepository.getFavourites()).thenReturn(mockEntities)

        // Instantiating the ViewModel
        val viewModel = CurrenciesViewModel(currenciesRepository, sharedPreferences, databaseRepository, testDispatcher)

        // Use reflection to call the private method 'loadInitialData()' if needed
        val loadInitialDataMethod = CurrenciesViewModel::class.declaredFunctions
            .first { it.name == "loadInitialData" }
        loadInitialDataMethod.isAccessible = true
        loadInitialDataMethod.call(viewModel)  // Call the private method

        // Collect currenciesUiState flow to check the emitted states
        viewModel.currenciesUiState.test {
            // Initially, before the ViewModel is initialized, the list should be empty
            val initialState = awaitItem()
            assertEquals(emptyList<String>(), initialState.currenciesList)

            // Simulate ViewModel initialization
            // After initialization, check if the currencies list and default currency are updated
            val updatedState = awaitItem()  // Wait for the state to be emitted after ViewModel initialization
            assertEquals(listOf("USD", "EUR"), updatedState.currenciesList)  // The updated list of currencies
            assertEquals("USD", updatedState.selectedCurrency)  // The default selected currency

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `test onFilterChanged updates filter state`() = runTest {
        // Collect filter state flow
        viewModel.filterUiState.test {
            // Trigger filter change
            viewModel.onFilterChanged(FilterOption.CodeAZ)

            // Verify the updated state
            val updatedState = awaitItem()
            assertEquals(FilterOption.CodeAZ, updatedState.selectedFilter)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test mapping from CurrenciesEntity to FavouritesUiModel`() {
        val mockEntities = listOf(
            CurrenciesEntity(0, "USD", "EUR", 1.0),
            CurrenciesEntity(1, "EUR", "USD", 0.9)
        )

        val expectedModels = listOf(
            FavouritesUiModel(base = "USD", title = "EUR", value = 1.0, isFavourite = true),
            FavouritesUiModel(base = "EUR", title = "USD", value = 0.9, isFavourite = true)
        )

        val result = mockEntities.toUiModel()

        assertEquals(expectedModels, result)
    }
}