package com.formation.velo.api.velo;


import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Field {
    @SerializedName("available_bike_stands")
    private Integer availableBikeStands;
    @SerializedName("available_bikes")
    private Integer availableBikes;
    private Integer number;
    @SerializedName("bike_stands")
    private Integer bikeStands;
    private String status;
    private String name;
    private String address;
    private Double[] position;

    @SerializedName("grp_disponible")
    private Integer grpDisponible;
    @SerializedName("grp_nom")
    private String grpNom;
    private Double[] location;
}
