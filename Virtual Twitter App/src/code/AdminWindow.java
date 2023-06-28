package code;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashSet;
import java.util.Set;


public class AdminWindow extends JFrame implements ActionListener
{
    private static AdminWindow frame;
    private JTree tree;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode selectedNode;
    private DefaultMutableTreeNode userNode;
    private JLabel selectedLabel, notUniqueWarning;
    private JButton groupButton, userButton, analyticsButton, userViewButton, validIDButton, recentUpdateButton;
    private JTextField groupText, userText;
    private JPanel treePanel;
    private DefaultTreeModel model;

    public static AdminWindow getAdminWindow()
    {
        if(frame == null)
        {
            frame = new AdminWindow();
        }
        return frame;
    }

    private AdminWindow()
    {
        this.setTitle("Admin Panel");    // window title
        this.setSize(800, 700);  // dimensions
        this.setResizable(false);    // prevent frame from being resized
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // stops program
        this.getContentPane().setBackground(new Color(0x377699));
      
 
        Border border = BorderFactory.createLineBorder(Color.BLACK);        // sets a border color

        this.setLayout(null);

        ImageIcon image = new ImageIcon("bird.jpg");    // creats immage icon
        this.setIconImage(image.getImage());     // changes icon of frame
        

                                            // Buttons
        groupButton = new JButton("Add Group");
        groupButton.setBounds(350, 100, 100, 50);
        groupButton.setFocusable(false);
        groupButton.addActionListener(this);  
        groupButton.setBackground(new Color(0xff975d));
        groupButton.setBorder(border);

        userButton = new JButton("Add User");
        userButton.setBounds(350, 200, 100, 50);
        userButton.setFocusable(false);
        userButton.addActionListener(this);  
        userButton.setBackground(new Color(0xff975d));
        userButton.setBorder(border);

        analyticsButton = new JButton("View Analytics");
        analyticsButton.setBounds(630, 570, 130, 50);
        analyticsButton.setFocusable(false);
        analyticsButton.addActionListener(this);  
        analyticsButton.setBackground(new Color(0xff975d));
        analyticsButton.setBorder(border);

        userViewButton = new JButton("Open user view");
        userViewButton.setBounds(350, 400, 100, 50);
        userViewButton.setFocusable(false);
        userViewButton.addActionListener(this);  
        userViewButton.setBackground(new Color(0xff975d));
        userViewButton.setBorder(border);

        validIDButton = new JButton("ID Verification");                 // ID verification button
        validIDButton.setBounds(480, 570, 130, 50);
        validIDButton.setFocusable(false);
        validIDButton.addActionListener(this);  
        validIDButton.setBackground(new Color(0xff975d));
        validIDButton.setBorder(border);

        recentUpdateButton = new JButton("Last Updated User");          // most recent updated User button
        recentUpdateButton.setBounds(330, 570, 130, 50);
        recentUpdateButton.setFocusable(false);
        recentUpdateButton.addActionListener(this);  
        recentUpdateButton.setBackground(new Color(0xff975d));
        recentUpdateButton.setBorder(border);


                                        // Text Fields
        groupText = new JTextField();
        groupText.setBounds(460, 100, 300, 50);
        groupText.setFont(new Font("Consolas", Font.PLAIN, 15));
        groupText.setBackground(Color.WHITE);
        groupText.setForeground(Color.BLACK);
        groupText.setText(" Group name");

        userText = new JTextField();
        userText.setBounds(460, 200, 300, 50);
        userText.setFont(new Font("Consolas", Font.PLAIN, 15));
        userText.setBackground(Color.WHITE);
        userText.setForeground(Color.BLACK);
        userText.setText(" User name");


                                        // Labels
        selectedLabel = new JLabel();
        selectedLabel.setBounds(460, 400, 300, 50);
        selectedLabel.setBorder(border);
        selectedLabel.setBackground(Color.white);
        selectedLabel.setOpaque(true);

        notUniqueWarning = new JLabel();
        notUniqueWarning.setBounds(460, 240, 200, 50);
        notUniqueWarning.setFont(new Font("Consolas", Font.BOLD, 15));
        notUniqueWarning.setForeground(Color.YELLOW);
        notUniqueWarning.setText("User ID must be unique");
        notUniqueWarning.setVisible(false);

        
        this.add(groupButton);
        this.add(userButton);
        this.add(analyticsButton);
        this.add(userViewButton);
        this.add(validIDButton);
        this.add(recentUpdateButton);

        this.add(groupText);
        this.add(userText);

        this.add(selectedLabel);
        this.add(notUniqueWarning);

        Tree();     // displays group and user hierarchy

        this.setVisible(true);
    }

        /* contains condition for what Button actions do */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
         if(e.getSource() == groupButton)               // add group button
        {
            String input = groupText.getText();
            if(input.equals(""))       // nothing is in textbox or no group selected
            {
                return;
            }
            else if(!Admin.getInstance().uniqueGroupID(input))      
            {
                notUniqueWarning.setText("Group ID taken");
                notUniqueWarning.setVisible(true);
            }
            else
            {
                if(selectedNode.getUserObject() instanceof Group)
                {
                    Admin.getInstance().createGroup(((Members)selectedNode.getUserObject()), input);
                    createNodes(selectedNode, input, 0);
                }
                return;
            }
        }

        if(e.getSource() == userButton)                 // add user button
        {
            String input = userText.getText();
            if(input.equals(""))       // nothing is in textbox or no group selected
            {
                return;
            }
            else if(!Admin.getInstance().uniqueUserID(input))   // warning that name isn't available
            {
                notUniqueWarning.setText("User ID taken");
                notUniqueWarning.setVisible(true);
            }
            else
            {
                notUniqueWarning.setVisible(false);
                if(selectedNode.getUserObject() instanceof Group)
                {
                    Admin.getInstance().createUser(((Members)selectedNode.getUserObject()), input);
                    createNodes(selectedNode, input, 1);
                }
                return;
            }

        }


        if(e.getSource() == userViewButton)             // open user view button
        {
            ((User)userNode.getUserObject()).openView();
        }

        
        if(e.getSource() == analyticsButton)            // view analytics button
        {
            totalUsersVisitor uVisitor = new totalUsersVisitor();
            totalGroupsVisitor gVisitor = new totalGroupsVisitor();
            totalMessagesVisitor mVisitor = new totalMessagesVisitor();
            percentPosVisitor pVisitor = new percentPosVisitor();

            String stats = ("Total number of users:   " + Admin.getInstance().accept(uVisitor) + "\n" + 
                            "Total number of groups:  " + Admin.getInstance().accept(gVisitor) + "\n" +
                            "Total messages in feeds: " + Admin.getInstance().accept(mVisitor) + "\n" +
                            "Percent of positive messages: " + Admin.getInstance().accept(pVisitor) + " %");

            JOptionPane.showMessageDialog(null, stats, "Analytics", JOptionPane.PLAIN_MESSAGE, null);
        }

        if(e.getSource() == validIDButton)                      // valid ID button
        {
            boolean badIDFound = false;
            Set<String> duplicateTest = new HashSet<>();

            for(User user: Admin.getInstance().getUserList())
            {
                if(!duplicateTest.add(user.getID()))    // tests if there are duplicate ID's
                {
                    badIDFound = true;
                }
                if(user.getID().contains(" "))      // tests if ID's contain spaces
                {
                    badIDFound = true;
                }
            }
            for(Group group: Admin.getInstance().getGroupList())
            {
                if(!duplicateTest.add(group.getID()))    // tests if there are duplicate ID's
                {
                    badIDFound = true;
                }
                if(group.getID().contains(" "))      // tests if ID's contain spaces
                {
                    badIDFound = true;
                }
            }

            if(badIDFound)
            {
                String message = "Invalid ID's Found";
                JOptionPane.showMessageDialog(null, message, "ID Verification", JOptionPane.PLAIN_MESSAGE, null);
            }
            else
            {
                String message = "All current ID's are valid";
                JOptionPane.showMessageDialog(null, message, "ID Verification", JOptionPane.PLAIN_MESSAGE, null);
            }
        }

        if(e.getSource() == recentUpdateButton)         // last updated User button
        {
            String mostRecent = "";
            long largest = -1;

            for(User user: Admin.getInstance().getUserList())       // looks for largest/most recent time
            {
                if(largest < user.getLastUpdateTime())
                {
                    largest = user.getLastUpdateTime();
                    mostRecent = user.getID();
                }
            }

            String message = "Last Updated User: " + mostRecent;
            JOptionPane.showMessageDialog(null, message, "Last Updated", JOptionPane.PLAIN_MESSAGE, null);
        }
        
    }


    // method creates a tree of groups and users
    private void Tree()
    {
        treePanel = new JPanel();                        // panel to hold tree
        treePanel.setBackground(new Color(0xff975d));
        treePanel.setBounds(10, 10, 300, 630);
        treePanel.setLayout(null);

        root = new DefaultMutableTreeNode(new Group("Root")); // Creates the root

        model = new DefaultTreeModel(root);                           // Creates model to add root to
        tree = new JTree();         
        tree.setModel(model);                       // creates tree with model

        model.addTreeModelListener(null);       // listener for tree modifications

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setEditable(true);
        JScrollPane treeView = new JScrollPane(tree);
        treeView.setBounds(10, 10, 272, 610);

        treePanel.add(treeView);
        this.add(treePanel);

        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {             // listener for what object is picked
            @Override
            public void valueChanged(TreeSelectionEvent e) 
            {
                selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if(selectedNode.getUserObject() instanceof User)
                {
                    selectedLabel.setText(((User)selectedNode.getUserObject()).getID());
                    userNode = selectedNode;                        
                }
            }
        });
    }


    /* adds groups and users to tree */
    private void createNodes(DefaultMutableTreeNode folder, String ID, int type) 
    {
        DefaultMutableTreeNode child = folder;

        if(type == 0)                           // adds group to tree
        {
            // adds most recently created group object to the tree
            child = new DefaultMutableTreeNode(Admin.getInstance().getGroupList().get(Admin.getInstance().getGroupList().size() - 1));
            model.insertNodeInto(child, folder, folder.getChildCount());
        }
        else                                    // adds user to tree
        {
            // adds most recently created user object to the tree
            child = new DefaultMutableTreeNode(Admin.getInstance().getUserList().get(Admin.getInstance().getUserList().size() - 1));
            model.insertNodeInto(child, folder, folder.getChildCount());
        }

        tree.scrollPathToVisible(new TreePath(child.getPath()));    // makes new node visible in tree
    }

}