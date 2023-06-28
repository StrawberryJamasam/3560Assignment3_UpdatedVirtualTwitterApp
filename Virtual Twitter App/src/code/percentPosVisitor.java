package code;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class percentPosVisitor implements AnalyticsVisitor
{
    @Override
    public int visitAdmin(Admin admin) 
    {
        int hasPositive = 0;
        List<String> positiveWords = Arrays.asList("relieved", "pleasant", "great", "fun", "happy", "good", "love", "enjoy", "nice");
        List<User> users = Admin.getInstance().getUserList();
        List<String> allMessages = new ArrayList<>();

        for(User user: users)       // puts all messages in user feeds into a list
        {
            for(String message: user.getFeed())
            {
                allMessages.add(message);
            }
        }

        for(String message: allMessages)
        {
            for(String posWord: positiveWords)
            {
                if(message.contains(posWord))
                {
                    hasPositive++;
                }
            }
        }

        if(allMessages.size() == 0)         // prevents divide by zero
        {
            return 0;
        }
        else
            return (hasPositive * 100) / allMessages.size() ;
    }

}
