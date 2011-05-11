package org.codinjustu.tools.jenkins.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.codinjustu.tools.jenkins.logic.IdeaJenkinsBrowserLogic;
import org.codinjustu.tools.jenkins.util.GuiUtil;

public class RefreshJobAction extends AnAction {

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
