package code;

import java.util.List;
import java.util.ArrayList;

/* Class creates a User with a unique ID */
public class User extends Subject implements Members, Observer
{
    private String ID;
    private List<User> followingList = new ArrayList<>();
    private List<User> followersList = new ArrayList<>();
    private List<String> feed = new ArrayList<>();
    private long creationTime;
    private long lastUpdateTime;
    private UserWindow window;

    public User(String userID)
    {
        ID = userID;
        creationTime = System.currentTimeMillis();
        window = new UserWindow(this);
        lastUpdateTime = 0;
    }

    public void follow(Members target)
    {
        followingList.add((User)target);
        ((User)target).attach(this);
    }

    public void postMessage(String message)
    {
        feed.add(message);
        updateLastUpdateTime();
        notifyObservers();
    }

    public void update(Subject subject)
    {
        String posted = ((User)subject).getFeed().get(((User)subject).getFeed().size() - 1);  // most recent message posted
        feed.add(posted);
        updateLastUpdateTime();
        window.updateFeed(posted, ((User)subject).getID());
    }


    public String getID()
    {
        return ID;
    }
 
    public void addFollower(Members follower)
    {
        followersList.add((User)follower);
    }

    @Override
    public String toString()
    {
        return ID;
    }

    public List<User> getFollowers()
    {
        return followersList;
    }

    public List<User> getFollowing()
    {
        return followingList;
    }

    public List<String> getFeed()
    {
        return feed;
    }

    public long getCreationTime()
    {
        return creationTime;
    }

    public void updateLastUpdateTime()
    {
        lastUpdateTime = System.currentTimeMillis();
    }

    public long getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    /* opens window for this user */
    public void openView()
    {
        window.openWindow();
    }

    public void updateFollowers(User follower)
    {
        followersList.add(follower);
        window.updateFollowers(follower.getID());
    }
}
