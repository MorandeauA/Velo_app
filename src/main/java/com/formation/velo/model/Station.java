package com.formation.velo.model;

import lombok.*;

import javax.persistence.*;


import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "stations")
public class Station implements Serializable {

	private static final long serialVersionUID = -767070904974486421L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String status;
    private Integer bikeStands;
    private Integer availableBikeStands;
    private Integer availableBikes;
    private String recordId;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Station station = (Station) o;
		return Objects.equals(id, station.id) 
        && Objects.equals(name, station.name) 
        && Objects.equals(latitude, station.latitude) 
        && Objects.equals(longitude, station.longitude) 
        && Objects.equals(status, station.status) 
        && Objects.equals(bikeStands, station.bikeStands) 
        && Objects.equals(availableBikeStands, station.availableBikeStands) 
        && Objects.equals(availableBikes, station.availableBikes) 
        && Objects.equals(recordId, station.recordId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, latitude, longitude, status, bikeStands, availableBikeStands, availableBikes, recordId);
	}
}

