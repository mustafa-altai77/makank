
import com.google.gson.annotations.SerializedName

data class DiseaseResponse2(
    @SerializedName("current_page")
    val currentPage: Int,
    val `data`: List<Data>,
    @SerializedName("first_page_url")
    val firstPageUrl: String,
    val from: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("last_page_url")
    val lastPageUrl: String,
    @SerializedName("next_page_url")
    val nextPageUrl: Any,
    val path: String,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("prev_page_url")
    val prevPageUrl: Any,
    val to: Int,
    val total: Int
)