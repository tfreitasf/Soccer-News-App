package me.dio.soccernews.data;

import androidx.room.Room;

import java.util.Spliterator;

import me.dio.soccernews.data.local.AppDatabase;
import me.dio.soccernews.data.remote.SoccerNewsApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerNewsRepositiry {

    //region Constantes
    private static final String REMOTE_API_URL = "https://tfreitasf.github.io/Soccer-news-api/";
    private static final String LOCAL_DB_NAME = "soccer-news";
    //endregion

    //region Atributos : encapsulam o acesso a nossa API (Retrofit) e bando de dados local (Room)
    private SoccerNewsApi remoteApi;
    private AppDatabase localDb;

    public SoccerNewsApi getRemoteApi(){
        return remoteApi;
    }

    public SoccerNewsApi getLocalDb(){
        return localDb;
    }

    //endregion

    //region Singleton: Garante uma instância única dos atributos relacionados ao Retrofit e Room
    private SoccerNewsRepositiry(){
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SoccerNewsApi.class);

        localDb = Room.databaseBuilder(App.getInstance(), AppDatabase.class, LOCAL_DB_NAME)
                .allowMainThreadQueries() //FIXME remover gambiarra
                .build();
    }
    public static SoccerNewsRepository getInstance() {
        return LazyHolder.INSTANCE;

    }
    private static class LazyHolder {
        private static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
    }

    //endregion


}
