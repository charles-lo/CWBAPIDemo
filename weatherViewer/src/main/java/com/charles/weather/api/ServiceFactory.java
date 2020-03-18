package com.charles.weather.api;

import com.charles.autocachingconveter.GsonCacheableConverter;
import com.charles.weather.api.model.Parameter;
import com.charles.weather.api.model.Response;
import com.charles.weather.api.model.Time;
import com.charles.weather.api.model.WeatherElement;
import com.charles.weather.data.BoxManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

class ServiceFactory {

    static APIService getInstance() {

        String baseUrl = "https://opendata.cwb.gov.tw/api/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonCacheableConverter.create((type, responseBody) -> {
                    if (responseBody instanceof Response) {
                        for (WeatherElement weatherElement : ((Response) responseBody).getRecords().getLocation().get(0).getWeatherElement()) {
                            if (weatherElement.getElementName().toLowerCase().contains("mint")) {
                                List<Time> times = weatherElement.getTime();
                                List<Parameter> parameters = new ArrayList<>();
                                for (int i=0; i<times.size(); i++) {
                                    times.get(i).setId((long) i+1);
                                    Parameter parameter = times.get(i).getParameter();
                                    parameter.setId((long) i+1);
                                    parameters.add(parameter);
                                }
                                if(weatherElement.getTime().size()>0) {
                                    BoxManager.getStore().boxFor(Time.class).removeAll();
                                    BoxManager.getStore().boxFor(Time.class).put(weatherElement.getTime());
                                    BoxManager.getStore().boxFor(Parameter.class).removeAll();
                                    BoxManager.getStore().boxFor(Parameter.class).put(parameters);
                                }
                            }
                        }
                    }
                }))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideHttpClient())
                .build();

        return retrofit.create(APIService.class);
    }

    private static OkHttpClient provideHttpClient() {
        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        return builder.build();
    }
}