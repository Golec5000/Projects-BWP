package org.application.bwp.airqualityapp.entity.airCondition.params;

public class Param {
    private String paramName;
    private String paramFormula;
    private String paramCode;
    private int idParam;

    public Param(String paramName, String paramFormula, String paramCode, int idParam) {
        this.paramName = paramName;
        this.paramFormula = paramFormula;
        this.paramCode = paramCode;
        this.idParam = idParam;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamFormula() {
        return paramFormula;
    }

    public void setParamFormula(String paramFormula) {
        this.paramFormula = paramFormula;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public int getIdParam() {
        return idParam;
    }

    public void setIdParam(int idParam) {
        this.idParam = idParam;
    }

    @Override
    public String toString() {
        return "Param{" +
                "paramName='" + paramName + '\'' +
                ", paramFormula='" + paramFormula + '\'' +
                ", paramCode='" + paramCode + '\'' +
                ", idParam=" + idParam +
                '}';
    }
}
