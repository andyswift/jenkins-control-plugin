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

package org.codinjutsu.tools.jenkins.logic;


import org.codinjutsu.tools.jenkins.JenkinsConfiguration;
import org.codinjutsu.tools.jenkins.model.BuildStatusEnum;
import org.codinjutsu.tools.jenkins.model.Jenkins;
import org.codinjutsu.tools.jenkins.model.Job;
import org.codinjutsu.tools.jenkins.model.View;
import org.codinjutsu.tools.jenkins.view.JobSearchComponent;
import org.codinjutsu.tools.jenkins.view.action.search.OpenJobSearchPanelAction;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.uispec4j.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JenkinsBrowserLogicTest extends UISpecTestCase {

    @Mock
    private JenkinsRequestManager requestManagerMock;

    private JenkinsBrowserLogic jenkinsBrowserLogic;

    private JenkinsConfiguration configuration;

    private final List<Job> joblist = new ArrayList<Job>();
    private Panel uiSpecPanel;

    public void test_displayInitialTreeAndLoadView() throws Exception {

        ComboBox comboBox = uiSpecPanel.getComboBox("viewCombo");
        comboBox.contains("Vue 1", "All").check();
        comboBox.selectionEquals("All").check();

        Tree jobTree = getJobTree(uiSpecPanel);
        jobTree.contentEquals(
                "Jenkins (master)\n" +
                        "  mint #150\n" +
                        "  capri #15 (running) #(bold)\n").check();

        comboBox.select("Vue 1");

        Thread.sleep(100);//waiting for the swing thread finished

        getJobTree(uiSpecPanel);
        jobTree.contentEquals(
                "Jenkins (master)\n" +
                        "  capri #15 (running) #(bold)\n").check();
    }

    public void test_displaySearchJobPanel() throws Exception {
        Tree jobTree = getJobTree(uiSpecPanel);
        jobTree.selectionIsEmpty().check();

        Thread.sleep(100);//waiting for the swing thread finished

        uiSpecPanel.pressKey(Key.control(Key.F));

        TextBox searchField = uiSpecPanel.getTextBox("searchField");
        searchField.textIsEmpty().check();

        searchField.setText("capri");
        searchField.pressKey(Key.ENTER);

        jobTree.selectionEquals("capri #15 (running)").check();

        //Section below does not work, perhaps KeyEvent is not properly caugth by the CloseJobSearchPanelAction
        searchField.pressKey(Key.ESCAPE);
        uiSpecPanel.getTextBox("searchField").isVisible().check();
    }

    private Tree getJobTree(Panel panel) {
        Tree jobTree = panel.getTree("jobTree");
        jobTree.setCellValueConverter(new DefaultTreeCellValueConverter());
        return jobTree;
    }


    @Override
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        configuration = new JenkinsConfiguration();
        configuration.setJobRefreshPeriod(60);
        configuration.setServerUrl("http://myjenkinsserver/");
        jenkinsBrowserLogic = new JenkinsBrowserLogic(configuration, requestManagerMock, JenkinsBrowserLogic.JobStatusCallback.NULL) {
            @Override
            protected void installRssActions(JPanel rssActionPanel) {}

            @Override
            protected void installBrowserActions(JTree jobTree, JPanel panel) {}

            @Override
            protected void installSearchActions(JobSearchComponent searchComponent) {}
        };

        Job mintJob = new JobBuilder().job("mint", "blue", "http://myjenkinsserver/mint", "false")
                .lastBuild("http://myjenkinsserver/mint/150", "150", BuildStatusEnum.SUCCESS.getStatus(), "false")
                .health("health-80plus", "0 tests en échec sur un total de 89 tests")
                .get();
        Job capriJob = new JobBuilder().job("capri", "red", "http://myjenkinsserver/capri", "false")
                .lastBuild("http://myjenkinsserver/capri/15", "15", BuildStatusEnum.FAILURE.getStatus(), "true")
                .health("health-00to19", "15 tests en échec sur un total de 50 tests")
                .get();


        
        Mockito.when(requestManagerMock.loadJenkinsWorkspace(configuration))
                .thenReturn(createJenkinsWorkspace());

        Mockito.when(requestManagerMock.loadJenkinsView("http://myjenkinsserver/"))
                .thenReturn(Arrays.asList(mintJob, capriJob));

        Mockito.when(requestManagerMock.loadJenkinsView("http://myjenkinsserver/vue1"))
                .thenReturn(Arrays.asList(capriJob));

        jenkinsBrowserLogic.init();
        uiSpecPanel = new Panel(jenkinsBrowserLogic.getBrowserPanel());
    }


    private Jenkins createJenkinsWorkspace() {
        Jenkins jenkins = new Jenkins("(master)");

        jenkins.setViews(Arrays.asList(
                View.createView("Vue 1", "http://myjenkinsserver/vue1"),
                View.createView("All", "http://myjenkinsserver/")
        ));

        jenkins.setPrimaryView(View.createView("All", "http://myjenkinsserver/"));

       
        return jenkins;
    }
}
