/*
 * Copyright (c) 2011 David Boissier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codinjutsu.tools.jenkins.view.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import org.codinjutsu.tools.jenkins.view.JenkinsBrowserPanel;

public class GotoJobPageAction extends AbstractGotoWebPageAction {

    public GotoJobPageAction(JenkinsBrowserPanel jenkinsBrowserPanel) {
        super("Go to the job page", "Open the job page in a web browser", "page_go.png", jenkinsBrowserPanel);
    }


    @Override
    protected String getUrl() {
        return jenkinsBrowserPanel.getSelectedJob().getUrl();
    }


    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setEnabled(jenkinsBrowserPanel.getSelectedJob() != null);
    }
}