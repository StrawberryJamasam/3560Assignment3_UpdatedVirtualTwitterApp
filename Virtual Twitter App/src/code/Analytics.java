package code;

/* Interface for allowing analytics visitors */

public interface Analytics 
{
    public int accept(AnalyticsVisitor visitor);
}
