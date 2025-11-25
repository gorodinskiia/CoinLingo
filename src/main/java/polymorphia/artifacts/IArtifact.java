package polymorphia.artifacts;

public interface IArtifact {
    String getName();

    double getHealthValue();

    double getDefenseValue();

    double getMovingCost();

    boolean isType(ArtifactType type);
}
