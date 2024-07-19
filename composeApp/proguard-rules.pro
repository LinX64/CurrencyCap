-dontwarn org.slf4j.impl.StaticLoggerBinder
-keep class * {
 @kotlinx.serialization.SerialName <fields>;
}
-dontwarn java.lang.management.**
-dontwarn org.slf4j.impl.**