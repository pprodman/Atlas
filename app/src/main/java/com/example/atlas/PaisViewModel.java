package com.example.atlas;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atlas.models.Pais;
import com.example.atlas.services.AtlasAPI;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaisViewModel extends ViewModel {

    private final MutableLiveData<List<Pais>> paisListLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private List<Pais> allPaisList = new ArrayList<>();

    private final AtlasAPI service;

    // Constructor de la clase ViewModel con Retrofit configurado para la API Atlas
    public PaisViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AtlasAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(AtlasAPI.class);
    }

    // Devuelve la lista de país
    public LiveData<List<Pais>> getPaisListLiveData() {
        return paisListLiveData;
    }

    // Devuelve el error
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    // Captura la lista de país
    public void fetchPaisesList(int limit, int offset) {
        service.getPaises().enqueue(new Callback<List<Pais>>() { // Llamada a la API
            @Override
            public void onResponse(@NonNull Call<List<Pais>> call, @NonNull Response<List<Pais>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allPaisList = response.body();
                    paisListLiveData.setValue(allPaisList);
                } else {
                    errorLiveData.setValue("Error: No se pudo obtener la lista de países.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Pais>> call, @NonNull Throwable t) {
                errorLiveData.setValue("Error: " + t.getMessage());
            }
        });
    }

    // Filtra la lista de pais
    public void searchPais(String query) {
        if (query.isEmpty()) {
            paisListLiveData.setValue(allPaisList);
        } else {
            List<Pais> filteredList = new ArrayList<>();
            for (Pais pais : allPaisList) {
                if (pais.getName().getCommon().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(pais);
                }
            }
            paisListLiveData.setValue(filteredList);
        }
    }

    // Actualiza los detalles del pais
    public void fetchPaisDetails(String name) {
        service.getPaisByName(name).enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(@NonNull Call<List<Pais>> call, @NonNull Response<List<Pais>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    updatePaisDetails(response.body().get(0));
                } else {
                    errorLiveData.setValue("Error: No se pudo obtener los detalles del país.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Pais>> call, @NonNull Throwable t) {
                errorLiveData.setValue("Error: " + t.getMessage());
            }
        });
    }

    // Actualiza los detalles del país
    private void updatePaisDetails(Pais updatedPais) {
        List<Pais> currentList = paisListLiveData.getValue();
        if (currentList != null) {
            for (int i = 0; i < currentList.size(); i++) {
                if (currentList.get(i).getName().getCommon().equals(updatedPais.getName().getCommon())) {
                    currentList.set(i, updatedPais);
                    break;
                }
            }
            paisListLiveData.setValue(currentList);
        }
    }
}

