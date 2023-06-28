package code;

import java.util.List;
import java.util.ArrayList;

/* This is a singleton class that creates and opens one admin window */

public class Admin implements Analytics
{
   private static Admin instance;
   private static List<User> userList;
   private static List<Group> groupList;
   
   public static Admin getInstance()
   {
    if(instance == null)
    {
        instance = new Admin();
        userList = new ArrayList<>();
        groupList = new ArrayList<>();
    }
    return instance;
   }


   /* Creates admin window  */
   private Admin()
   {
     AdminWindow.getAdminWindow();
   }


   /* Method creates a new user within the group selectedGroup */
   public void createUser(Members selectedGroup, String ID)
   {
     User user = new User(ID);
     userList.add(user);
     ((Group)selectedGroup).addMember(user);
   }
 
   /* Method creates a new Group within the group selectedGroup */
   public void createGroup(Members selectedGroup, String ID)
   {
     Group group = new Group(ID);
     groupList.add(group);
     ((Group)selectedGroup).addMember(group);
   }


   public void openUserView()
   {
     System.out.println("Opening user view");
   }

   public void viewAnalytics()
   {
     System.out.println("Viewing analytics");
   }


   /* method checks if entered user ID is unique */
   public boolean uniqueUserID(String ID)
   {
     boolean isUnique = true;
     for(User user: userList)
     {
          if(user.getID().equals(ID))
          {
               isUnique = false;
          }
     }
     return isUnique;
   }

      /* method checks if entered group ID is unique */
   public boolean uniqueGroupID(String ID)
   {
     boolean isUnique = true;
     for(Group group: groupList)
     {
          if(group.getID().equals(ID))
          {
               isUnique = false;
          }
     }
     return isUnique;
   }

   public List<User> getUserList()
   {
     return userList;
   }

   public List<Group> getGroupList()
   {
     return groupList;
   }


   /* method allows for extra analytic features to be added */
   @Override
   public int accept(AnalyticsVisitor visitor)       
   {
     return visitor.visitAdmin(this);
   }

}
