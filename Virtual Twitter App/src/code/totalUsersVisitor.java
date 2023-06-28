package code;

public class totalUsersVisitor implements AnalyticsVisitor
{
    @Override
    public int visitAdmin(Admin admin) 
    {
        return Admin.getInstance().getUserList().size();
    }
    
}