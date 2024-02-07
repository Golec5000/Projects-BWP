package org.application.bwp.airqualityapp.entity.airCondition.molecules;

import java.util.Set;

public class AirMolecules {

    private String key;
    private Set<MoleculeValue> values;

    public AirMolecules(String key, Set<MoleculeValue> values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<MoleculeValue> getValues() {
        return values;
    }

    public void setValues(Set<MoleculeValue> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "AirMolecules{" +
                "key='" + key + '\'' +
                ", values=" + values +
                '}';
    }
}
