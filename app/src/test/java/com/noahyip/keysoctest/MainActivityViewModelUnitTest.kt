
import com.noahyip.keysoctest.apiService.ITuneService
import com.noahyip.keysoctest.model.ITuneSearchResponse
import com.noahyip.keysoctest.RetrofitClient
import com.noahyip.keysoctest.viewModel.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mockRetrofitClient: RetrofitClient

    @Before
    fun setUp() {
        mockRetrofitClient = mock(RetrofitClient::class.java)
        viewModel = MainActivityViewModel()
    }

    @Test
    fun `test search failure`() {
        // Arrange
        val keyword = "test"
        val call = mock(Call::class.java) as Call<ITuneSearchResponse>

        `when`(mockRetrofitClient.getImgurService()).thenReturn(mock(ITuneService::class.java))
        `when`(mockRetrofitClient.getImgurService().search(
            "any",
            20,
            ITuneService.LIMIT,
            "any",
            "any",
            "any"
        )).thenReturn(call)

        `when`(call.enqueue(any())).thenAnswer {
            val callback = it.arguments[0] as Callback<ITuneSearchResponse>
            callback.onFailure(call, IOException("Test Exception"))
        }
        // Act
        viewModel.search(keyword)

        // Assert
        assert(viewModel.iTuneSearchResponse.value == null)
    }

    @Test
    fun `test loadMore success`() {
        // Arrange
        val moreResponse = ITuneSearchResponse()
        val call = mock(Call::class.java) as Call<ITuneSearchResponse>

        `when`(mockRetrofitClient.getImgurService()).thenReturn(mock(ITuneService::class.java))
        `when`(mockRetrofitClient.getImgurService().search(
            "any",
            20,
            ITuneService.LIMIT,
            "any",
            "any",
            "any"
        )).thenReturn(call)

        `when`(call.enqueue(any())).thenAnswer {
            val callback = it.arguments[0] as Callback<ITuneSearchResponse>
            callback.onResponse(call, Response.success(moreResponse))
        }

        // Act
        viewModel.loadMore()
        assertTrue(viewModel.iTuneSearchResponse.value?.hasMore ?: true)
    }

    @Test
    fun `test loadMore failure`() {
        // Arrange
        val call = mock(Call::class.java) as Call<ITuneSearchResponse>

        `when`(mockRetrofitClient.getImgurService()).thenReturn(mock(ITuneService::class.java))
        `when`(mockRetrofitClient.getImgurService().search(
            "any",
            20,
            ITuneService.LIMIT,
            "any",
            "any",
            "any"
        )).thenReturn(call)

        `when`(call.enqueue(any())).thenAnswer {
            val callback = it.arguments[0] as Callback<ITuneSearchResponse>
            callback.onFailure(call, IOException("Test Exception"))
        }

        // Act
        viewModel.loadMore()
        assertFalse(viewModel.iTuneSearchResponse.value?.hasMore ?: false)
    }
}
