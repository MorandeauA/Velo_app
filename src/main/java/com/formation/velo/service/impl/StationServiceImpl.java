package com.formation.velo.service.impl;

import com.formation.velo.model.Station;
import com.formation.velo.api.OpenDataVeloNantesClient;
import com.formation.velo.api.OpenDataVeloNantes;
import com.formation.velo.repository.StationRepository;
import com.formation.velo.service.StationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.util.Arrays;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.Call;




@Service

public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository repository) {
        this.stationRepository = repository;
    }

    @Override
    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    @Override
    public Optional<Station> findById(Integer id) {
        return stationRepository.findById(id);
    }

    @Override
    public Station save(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public void deleteById(Integer id) {
        stationRepository.deleteById(id);
    }

    @Override
    public void delete(Station station) {
        stationRepository.delete(station);
    }

    @Override
    public void saveRecords() {
        //appel api
        String baseUrl = "https://data.nantesmetropole.fr/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        OpenDataVeloNantesClient client = retrofit.create(OpenDataVeloNantesClient.class);

        Call<OpenDataVeloNantes> openDataVeloNantesCall = client.getRecords();

        try {

            OpenDataVeloNantes openDataVeloNantes = openDataVeloNantesCall.execute().body();
            System.out.printf(openDataVeloNantes.toString());

            //Save records dans station
            
            Arrays.stream(openDataVeloNantes.getRecords()).forEach(record -> {
                Optional<Station> stationToUpdate = findByRecordId(record.getRecordId());
                if(stationToUpdate.isPresent()){
                    // on update la station
                    stationToUpdate.get()
                            .setBikeStands(record.getField().getBikeStands());
                    stationToUpdate.get()
                            .setAddress(record.getField().getAddress());
                    stationToUpdate.get()
                            .setAvailableBikes(record.getField().getAvailableBikes());
                    stationToUpdate.get()
                            .setAvailableBikeStands(record.getField().getAvailableBikeStands());
                    stationToUpdate.get().setStatus(record.getField().getStatus());
                    // on save
                    save(stationToUpdate.get());
                }else {
                    // on crée la station
                    Station newStation = Station.builder()
                            .recordId(record.getRecordId())
                            .name(record.getField().getName())
                            .address(record.getField().getAddress())
                            .availableBikes(record.getField().getAvailableBikes())
                            .bikeStands(record.getField().getBikeStands())
                            .availableBikeStands(record.getField().getAvailableBikeStands())
                            .latitude(record.getField().getPosition()[0])
                            .longitude(record.getField().getPosition()[1])
                            .status(record.getField().getStatus())
                            .build();
                    // on save
                    save(newStation);
                }
            });



        } catch(IOException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public Optional<Station> findByRecordId(String recordId) {
        return stationRepository.findByRecordId(recordId);
    }

}

