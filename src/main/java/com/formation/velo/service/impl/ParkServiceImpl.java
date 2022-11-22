package com.formation.velo.service.impl;

import com.formation.velo.model.Park;
import com.formation.velo.api.parks.OpenDataParkingNantes;
import com.formation.velo.api.parks.OpenDataParkingNantesClient;
import com.formation.velo.repository.ParkRepository;
import com.formation.velo.service.ParkService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.util.Arrays;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.Call;




@Service

public class ParkServiceImpl implements ParkService {

    private final ParkRepository parkRepository;

    public ParkServiceImpl(ParkRepository repository) {
        this.parkRepository = repository;
    }

    @Override
    public List<Park> findAll() {
        return parkRepository.findAll();
    }

    @Override
    public Optional<Park> findById(Integer id) {
        return parkRepository.findById(id);
    }

    @Override
    public Park save(Park park) {
        return parkRepository.save(park);
    }

    @Override
    public void deleteById(Integer id) {
        parkRepository.deleteById(id);
    }

    @Override
    public void delete(Park park) {
        parkRepository.delete(park);
    }

    @Override
    public void saveParkRecords() {
        //appel api
        String baseUrl = "https://data.nantesmetropole.fr/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        OpenDataParkingNantesClient client = retrofit.create(OpenDataParkingNantesClient.class);

        Call<OpenDataParkingNantes> openDataParkingNantesCall = client.getRecords();

        try {

            OpenDataParkingNantes openDataParkingNantes = openDataParkingNantesCall.execute().body();
            System.out.printf(openDataParkingNantes.toString());

            //Save records dans park
            
            Arrays.stream(openDataParkingNantes.getRecords()).forEach(record -> {
                Optional<Park> parkToUpdate = findByRecordId(record.getRecordId());
                if(parkToUpdate.isPresent()){
                    parkToUpdate.get()
                    .setGrpDisponible(record.getField().getGrpDisponible());
                    parkToUpdate.get()
                    .setGrpNom(record.getField().getGrpNom());
                    save(parkToUpdate.get());
                }else {
                    // on crée la park4
                    double latitude=0;
                    double longitude=0;
                    if (record.getField().getLocation() != null){
                        latitude =  record.getField().getLocation()[0];         
                        longitude = record.getField().getLocation()[1];
                    }
                    Park newPark = Park.builder()
                            .recordId(record.getRecordId())
                            .grpDisponible(record.getField().getGrpDisponible())
                            .grpNom(record.getField().getGrpNom())
                            .latitude(latitude)
                            .longitude(longitude)
                            .build();
                    // on save
                    save(newPark);
                }
            });



        } catch(IOException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public Optional<Park> findByRecordId(String recordId) {
        return parkRepository.findByRecordId(recordId);
    }

}

