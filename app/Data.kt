
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: Any,
    val id: Int,
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: Any
)