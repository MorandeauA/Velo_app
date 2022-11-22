package com.formation.velo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "parks")

public class Park implements Serializable {
    
    private static final long serialVersionUID = -7670709104974486420L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String grpNom;
    
    private int grpDisponible;

    private Double latitude;

    private Double longitude;

    private String recordId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((grpNom == null) ? 0 : grpNom.hashCode());
        result = prime * result + grpDisponible;
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        result = prime * result + ((recordId == null) ? 0 : recordId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Park other = (Park) obj;
        if (grpNom == null) {
            if (other.grpNom != null)
                return false;
        } else if (!grpNom.equals(other.grpNom))
            return false;
        if (grpDisponible != other.grpDisponible)
            return false;
        if (latitude == null) {
            if (other.latitude != null)
                return false;
        } else if (!latitude.equals(other.latitude))
            return false;
        if (longitude == null) {
            if (other.longitude != null)
                return false;
        } else if (!longitude.equals(other.longitude))
            return false;
        if (recordId == null) {
            if (other.recordId != null)
                return false;
        } else if (!recordId.equals(other.recordId))
            return false;
        return true;
    }
    
}
    
