package code;

public class totalMessagesVisitor implements AnalyticsVisitor
{
    @Override
    public int visitAdmin(Admin admin) 
    {
        int messageCount = 0;

        for(User user: Admin.getInstance().getUserList())
        {
            messageCount += user.getFeed().size();
        }

        return messageCount;
    }
    
}
