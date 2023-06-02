import Constants.EMPTY_STRING
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class SecretValues(
    private val context: Context
) {
    fun getValue(key: String): String {
        val ai: ApplicationInfo = context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        return ai.metaData.getString(key, EMPTY_STRING)
    }
}