package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class UserWindow extends JFrame implements ActionListener
{
    private JButton followButton;
    private JButton postButton;
    private JTextField searchUserText;
    private JScrollPane followersPane;
    private JScrollPane followingsPane;
    private JScrollPane feedPane;
    private JScrollPane messagePane;
    private JTextArea followersText;
    private JTextArea followingText;
    private JTextArea feedText;
    private JTextArea messageText;
    private JLabel notUser;
    private JLabel timeUpdated;

    private User currentUser;

    public UserWindow(User user)
    {
        currentUser = user;

        this.setTitle(" " + currentUser.getID() + "'s view");     // frame appearance
        this.setSize(800, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0x40c5a4));
        this.setLayout(null);

        Border border = BorderFactory.createLineBorder(Color.BLACK);

        // buttons
        followButton = new JButton("Follow");
        followButton.setBounds(10, 60, 220, 30);
        followButton.setFocusable(false);
        followButton.addActionListener(this);  
        followButton.setBackground(new Color(0xcf9be7));
        followButton.setBorder(border);

        postButton = new JButton("Post");
        postButton.setBounds(10, 420, 220, 30);
        postButton.setFocusable(false);
        postButton.addActionListener(this);
        postButton.setBackground(new Color(0xcf9be7));
        postButton.setBorder(border);

        // Textfields
        searchUserText = new JTextField();
        searchUserText.setBounds(10, 10, 220, 50);
        searchUserText.setFont(new Font("Arial", Font.PLAIN, 18));
        searchUserText.setForeground(Color.BLACK);
        searchUserText.setText("follow user");

        // Text areas
        followersText = new JTextArea();
        followersText.setEditable(false);
        followersText.setLineWrap(true);
        followersText.setWrapStyleWord(true);
        followersText.setFont(new Font("Arial", Font.PLAIN, 18));

        followingText = new JTextArea();
        followingText.setEditable(false);
        followingText.setLineWrap(true);
        followingText.setWrapStyleWord(true);
        followingText.setFont(new Font("Arial", Font.PLAIN, 18));

        feedText = new JTextArea();
        feedText.setEditable(false);
        feedText.setLineWrap(true);
        feedText.setWrapStyleWord(true);
        feedText.setFont(new Font("Arial", Font.PLAIN, 18));

        messageText = new JTextArea();
        messageText.setEditable(true);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(new Font("Arial", Font.PLAIN, 18));


        // scroll panes
        followersPane = new JScrollPane(followersText);
        followersPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        followersPane.setBounds(590, 280, 180, 170);

        followingsPane = new JScrollPane(followingText);
        followingsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        followingsPane.setBounds(590, 20, 180, 240);

        feedPane = new JScrollPane(feedText);
        feedPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        feedPane.setBounds(250, 20, 320, 430);

        messagePane = new JScrollPane(messageText);
        messagePane.setBounds(10, 200, 220, 220);

        // labels
        notUser = new JLabel();
        notUser.setBounds(30, 80, 220, 50);
        notUser.setFont(new Font("Consolas", Font.BOLD, 15));
        notUser.setForeground(Color.YELLOW);
        notUser.setText("User ID doesn't exist");
        notUser.setVisible(false);

        JLabel feedLabel = new JLabel();
        feedLabel.setBounds(250, 5, 220, 20);
        feedLabel.setFont(new Font("Consolas", Font.BOLD, 12));
        feedLabel.setText("Messages Feed");

        timeUpdated = new JLabel();            // label displays posted time of most recent message
        timeUpdated.setBounds(350, 5, 220, 20);
        timeUpdated.setFont(new Font("Consolas", Font.BOLD, 12));
        timeUpdated.setText("Last Updated: " + currentUser.getLastUpdateTime());

        JLabel followingLabel = new JLabel();
        followingLabel.setBounds(590, 5, 220, 20);
        followingLabel.setFont(new Font("Consolas", Font.BOLD, 12));
        followingLabel.setText("Following");

        JLabel followersLabel = new JLabel();
        followersLabel.setBounds(590, 265, 220, 20);
        followersLabel.setFont(new Font("Consolas", Font.BOLD, 12));
        followersLabel.setText("Followers");

        JLabel userNameLabel = new JLabel();
        userNameLabel.setBounds(12, 160, 220, 20);
        userNameLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        userNameLabel.setText("User: " + currentUser.getID());

        JLabel creationTimeLabel = new JLabel();         // label displays creationTime
        creationTimeLabel.setBounds(12, 180, 220, 20);
        creationTimeLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        creationTimeLabel.setText("Created: " + currentUser.getCreationTime());

        this.add(followButton);
        this.add(postButton);
        this.add(searchUserText);
        this.add(followersPane);
        this.add(followingsPane);
        this.add(feedPane);
        this.add(messagePane);
        this.add(notUser);
        this.add(feedLabel);
        this.add(followingLabel);
        this.add(followersLabel);
        this.add(userNameLabel);
        this.add(creationTimeLabel);
        this.add(timeUpdated);

        this.setVisible(false);
    }

    public void openWindow()
    {
        this.setVisible(true);
    }

    /* sets behavior/actions for window components */
    @Override
    public void actionPerformed(ActionEvent e)          
    {
        if(e.getSource() == followButton)               // follow button
        {
            String target = searchUserText.getText();
            int index = -1;

            for(User user: Admin.getInstance().getUserList())       // checks/gets object corresponding to ID
            {
                if(user.getID().equals(target))
                {
                    index = Admin.getInstance().getUserList().indexOf(user);
                }
            }
            if(index != -1 && !target.equals(currentUser.getID()))      // attachs user/updates followings list
            {
                notUser.setVisible(false);
                currentUser.follow(Admin.getInstance().getUserList().get(index));
                updateFollowing(target);
                Admin.getInstance().getUserList().get(index).updateFollowers(currentUser);  // updates the followed's followers
            }
            else
            {
                notUser.setVisible(true);
            }
        }

        if(e.getSource() == postButton);                // post button
        {
            String message = messageText.getText();
            messageText.setText("");
            
            if(message.equals(""))  // checks for empty message box
            {
                return;
            }
            else
            {
                currentUser.postMessage(message);
                updateFeed(message, currentUser.getID());
            }
        }
    }
    
    public void updateFeed(String message, String poster)      // updates feed
    {
        feedText.append("" + poster + ":\n" + message + "\n\n");
        timeUpdated.setText("Last Updated: " + currentUser.getLastUpdateTime());
    }

    public void updateFollowers(String follower)       // adds follower to display
    {
        followersText.append(" " + follower + "\n");
    }

    public void updateFollowing(String following)       // adds following to display
    {
        followingText.append(" " + following + "\n");
    }
}
