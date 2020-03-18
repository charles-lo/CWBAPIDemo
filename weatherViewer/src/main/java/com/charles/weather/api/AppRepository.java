package com.charles.weather.api;

import com.charles.weather.api.model.Parameter;
import com.charles.weather.api.model.Time;
import com.charles.weather.api.model.WeatherElement;
import com.charles.weather.data.BoxManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.objectbox.query.Query;
import io.objectbox.rx.RxQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AppRepository {
    private static final String TAG = AppRepository.class.getSimpleName();
    private static final String AUTHORIZATION = "Authorization";
    private static final String LOCATION_NAME = "locationName";
    private static volatile AppRepository INSTANCE;

    private CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<List<Time>> data = new MutableLiveData<>();
    private MutableLiveData<Boolean> networkStatus = new MutableLiveData<>();

    private AppRepository() {
        // Prevent form the reflection api.
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static AppRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (AppRepository.class) {
                if (INSTANCE == null) INSTANCE = new AppRepository();
            }
        }
        return INSTANCE;
    }

    LiveData<List<Time>> getData() {
        return data;
    }

    private Query<Time> postBox = BoxManager.getStore().boxFor(Time.class).query().build();
    private Query<Parameter> postPBox = BoxManager.getStore().boxFor(Parameter.class).query().build();


    void fetchData() {
        Map<String, Object> params = new HashMap<>();
        params.put(AUTHORIZATION, "CWB-A3702C39-46D9-4C89-90E5-08DE45BE8ACF");
        params.put(LOCATION_NAME, "臺北市");

        AtomicReference<List<Parameter>> parameters = new AtomicReference<>();

        disposables.add(RxQuery.single(postPBox)
                .observeOn(Schedulers.io())
                .flatMap(cacheP -> {
//                    data.setValue(cache);
                    parameters.set(cacheP);
                    return RxQuery.single(postBox);
                }).observeOn(AndroidSchedulers.mainThread())
                .map(cache -> {
                    for (int i=0; i<cache.size(); i++) {
                        cache.get(i).setParameter(parameters.get().get(i));
                    }
                    data.setValue(cache);
                    return 0;
                }).observeOn(Schedulers.io())
                .flatMap(result -> {
                    return ServiceFactory.getInstance().fetchData(params);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            for (WeatherElement weatherElement : result.getRecords().getLocation().get(0).getWeatherElement()) {
                                if (weatherElement.getElementName().toLowerCase().contains("mint")) {
                                    data.postValue(weatherElement.getTime());
                                }
                            }
                        },
                        throwable -> networkStatus.postValue(false)
                ));

//        disposables.add(ServiceFactory.getInstance().fetchData(params)
//                .subscribeOn(Schedulers.io())
//                .subscribe(
//                        result -> {
//                            for (WeatherElement weatherElement : result.getRecords().getLocation().get(0).getWeatherElement()) {
//                                if (weatherElement.getElementName().toLowerCase().contains("mint")) {
//                                    data.postValue(weatherElement.getTime());
//                                }
//                            }
//                        },
//                        throwable -> networkStatus.postValue(false)
//                ));
    }


    LiveData<Boolean> getNetworkStatus() {
        return networkStatus;
    }

    void setNetworkStatus(Boolean status) {
        networkStatus.postValue(status);
    }
}
