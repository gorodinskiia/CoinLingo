package polymorphia.artifacts;

public class StudentLoan extends Artifact{
    public static double DEFAULT_HEALTH_VALUE = 0.0;
    public static double DEFAULT_MOVING_COST = 1.0;

    public StudentLoan(String name, double debtAmount, double movingCost) {
        super(ArtifactType.StudentLoan, name, DEFAULT_HEALTH_VALUE, debtAmount, movingCost);
    }

    @Override
    public String toString() {
        String formattedDebt = String.format("%.2f", getDefenseValue());
        return getName() + " student loan(debtAmount:" + formattedDebt + ", movingCost:" + getMovingCost() + ")";
    }
    
}
