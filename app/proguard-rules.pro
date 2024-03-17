-dontshrink
-keep class com.android.volley.** { *; }
-keep class org.apache.commons.logging.**
-keep class org.apache.http.** {
    *;
}
-keepattributes *Annotation*
-dontwarn org.apache.**

-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from     TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keep class com.v5foradnroid.userapp.BuildConfig {
    *;
}
-keep class com.v5foradnroid.userapp.Develop{
    *;
}

# AppCompat and Material Components
-keep class androidx.appcompat.** { *; }
-keep class com.google.android.material.** { *; }

# ConstraintLayout
-keep class androidx.constraintlayout.** { *; }

# Google Play Services
-keep class com.google.android.gms.** { *; }

# Firebase
-keep class com.google.firebase.** { *; }

# Biometric
-keep class androidx.biometric.** { *; }

# SimpleCropView
-keep class com.isseiaoki.** { *; }

# LoadingButton
-keep class br.com.simplepass.** { *; }

# Picasso
-keep class com.squareup.picasso.** { *; }

# CircleImageView
-keep class de.hdodenhof.** { *; }

# Multidex
-keep class androidx.multidex.** { *; }

# SpinKit
-keep class com.github.ybq.** { *; }

# Volley
-keep class com.android.volley.** { *; }

# AutoLinkTextView
-keep class com.github.armcha.** { *; }

# PatternLock
-keep class com.reginald.** { *; }

# Shimmer
-keep class com.romainpiel.shimmer.** { *; }

# RoundedImageView
-keep class com.makeramen.** { *; }

# Adhan
-keep class com.batoulapps.adhan.** { *; }

# Dexter
-keep class com.karumi.dexter.** { *; }

# Material Ripple
-keep class com.balysv.** { *; }

# Floating Action Button
-keep class com.github.clans.** { *; }

# Gson
-keep class com.google.gson.** { *; }

# AutoImageSlider
-keep class com.github.smarteist.** { *; }

# Glide
-keep class com.bumptech.glide.** { *; }

# Lottie
-keep class com.airbnb.android.** { *; }

# SwipeRefreshLayout
-keep class androidx.swiperefreshlayout.** { *; }

# Play Services Location
-keep class com.google.android.gms.** { *; }

-keep class org.sqlite.** { *; }
-keep class org.sqlite.database.** { *; }
-keep class com.v5foradnroid.userapp.models.**
