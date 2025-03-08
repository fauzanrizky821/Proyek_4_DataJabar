import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.proyek4.datajabar.data.api.ApiService

object ApiClient {
    private const val BASE_URL = "https://data.jabarprov.go.id/api-backend/bigdata/bps/"

    // Menyiapkan logging interceptor untuk mencatat request dan response
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Bisa juga LEVEL.HEADERS jika ingin log header saja
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // Menambahkan interceptor ke OkHttpClient
        .build()

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Gunakan OkHttpClient yang sudah dikonfigurasi dengan logging
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}
