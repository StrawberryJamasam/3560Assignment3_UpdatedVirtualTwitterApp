package code;

public class totalGroupsVisitor implements AnalyticsVisitor
{
    @Override
    public int visitAdmin(Admin admin) 
    {
        return Admin.getInstance().getGroupList().size();
    }
    
}
