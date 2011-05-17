package org.codinjutsu.tools.jenkins.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import org.apache.log4j.Logger;
import org.codinjutsu.tools.jenkins.logic.IdeaJenkinsBrowserLogic;
import org.codinjutsu.tools.jenkins.util.GuiUtil;

public class RefreshJobAction extends AnAction {

    private static final Logger LOG = Logger.getLogger(RefreshJobAction.class.getName());
    private IdeaJenkinsBrowserLogic jenkinsBrowserLogic;


    public RefreshJobAction(IdeaJenkinsBrowserLogic jenkinsBrowserLogic) {
        super("Refresh", "Refresh current job", GuiUtil.loadIcon("loadingTree.png"));
        this.jenkinsBrowserLogic = jenkinsBrowserLogic;
    }


    @Override
    public void actionPerformed(AnActionEvent event) {
        jenkinsBrowserLogic.loadSelectedJob();
    }


    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setVisible(jenkinsBrowserLogic.getSelectedJob() != null);
    }

}
