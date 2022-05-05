package model;

public class Material {
    private String materialId;
    private String materialType;

    public Material() {
    }

    public Material(String materialId, String materialType) {
        this.materialId = materialId;
        this.materialType = materialType;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    @Override
    public String toString() {
        return "Material{" +
                "materialId='" + materialId + '\'' +
                ", materialType='" + materialType + '\'' +
                '}';
    }
}
