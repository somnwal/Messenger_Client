# Messenger_Client π³

<br />

## Rest API μλ² μ€μ 

<br />
<br />

### BASE_URL μ€μ 
###### com.messenger.main.retrofit.RetrofitInstance

```kotlin
package com.messenger.main.retrofit

import android.annotation.SuppressLint
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        @SuppressLint("AuthLeak")
        
        // μλ² μ£Όμ μ€μ !!!
        private const val BASE_URL = "http://<μλ²μ£Όμ>:<ν¬νΈ>/"
        var retrofit: Retrofit =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}

```
