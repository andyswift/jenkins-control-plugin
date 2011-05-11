package org.codinjustu.tools.jenkins.view;

import org.codinjustu.tools.jenkins.model.Jenkins;
import org.codinjustu.tools.jenkins.model.Job;
import org.codinjustu.tools.jenkins.model.View;
import org.codinjustu.tools.jenkins.util.GuiUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.List;

public class JenkinsBrowserPanel extends JPanel implements JenkinsBrowserView {

    private static final long serialVersionUID = -9004598954453193352L;


    private JComboBox viewCombo;
    private JTree jobTree;
    private JPanel rootPanel;
    private RssLatestJobPanel rssLatestJobPanel;
    private JPanel rssActionPanel;
    private JPanel actionPanel;


    public JenkinsBrowserPanel() {
        jobTree.setCellRenderer(new JenkinsTreeRenderer());
        jobTree.setName("jobTree");
        viewCombo.setRenderer(new JenkinsViewComboRenderer());
        viewCombo.setName("viewCombo");


        setLayout(new BorderLayout());
        add(rootPanel, BorderLayout.CENTER);
    }


    public void initModel(Jenkins jenkins) {
        fillJobTree(jenkins);
        initViewList(jenkins.getViews());
    }


    public void fillJobTree(Jenkins jenkins) {
        List<Job> jobList = jenkins.getJobs();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(jenkins);
        if (!jobList.isEmpty()) {
            for (Job job : jobList) {
                DefaultMutableTreeNode jobNode = new DefaultMutableTreeNode(job);
                rootNode.add(jobNode);
            }
        }
        jobTree.setModel(new DefaultTreeModel(rootNode));
    }


    private void initViewList(List<View> views) {
        viewCombo.setModel(new DefaultComboBoxModel(views.toArray()));
        viewCombo.setSelectedIndex(-1);
    }


    public void setSelectedView(View view) {
        viewCombo.setSelectedItem(view);
    }


    public Job getSelectedJob() {
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) jobTree.getLastSelectedPathComponent();
        if (treeNode != null) {
            Object userObject = treeNode.getUserObject();
            if (userObject instanceof Job) {
                return (Job) userObject;
            }
        }
        return null;
    }


    public View getSelectedJenkinsView() {
        return (View) viewCombo.getSelectedItem();
    }


    public RssLatestJobPanel getRssLatestJobPanel() {
        return rssLatestJobPanel;
    }


    public void showErrorDialog(String errorMessage, String content) {
        GuiUtil.showErrorDialog(errorMessage, content);
    }

    public Jenkins getJenkins() {
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) jobTree.getLastSelectedPathComponent();
        if (treeNode != null) {
            Object userObject = treeNode.getUserObject();
            if (userObject instanceof Jenkins) {
                return (Jenkins) userObject;
            }
        }
        return null;
    }


    public JTree getJobTree() {
        return jobTree;
    }


    public JComboBox getViewCombo() {
        return viewCombo;
    }


    public JPanel getRssActionPanel() {
        return rssActionPanel;
    }


    public void setErrorMsg() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new Jenkins(
                "(Unable to connect. See Settings -> Jenkins Control Plugin)"));
        jobTree.setModel(new DefaultTreeModel(rootNode));
    }

    public JPanel getActionPanel() {
        return actionPanel;
    }
}
