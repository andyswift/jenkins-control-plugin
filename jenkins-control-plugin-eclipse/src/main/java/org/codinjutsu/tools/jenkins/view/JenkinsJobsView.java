package org.codinjutsu.tools.jenkins.view;

import javax.print.attribute.standard.Severity;

import org.codinjutsu.tools.jenkins.JenkinsControlActivator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class JenkinsJobsView extends ViewPart {

	private Action actionRefresh;
	
	private Action actionBuildOnJenkins;
	
	private Action actionGoToJobPage;
	
	private Action actionGoToLatestBuildPage;
	
	@Override
	public void createPartControl(Composite parent) {
		actionRefresh = new Action("Refresh", AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui", "icons/full/dlcl16/refresh_nav.gif")) {
			@Override
			public void run() {
				Platform.getLog(Platform.getBundle(JenkinsControlActivator.PLUGIN_ID)).log(
						new Status(Severity.REPORT.getValue(), JenkinsControlActivator.PLUGIN_ID, "Refresh"));
			};
		};
		actionBuildOnJenkins = new Action("Build on Jenkins", AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui", "icons/full/elcl16/refresh_nav.gif")) {
			@Override
			public void run() {
				Platform.getLog(Platform.getBundle(JenkinsControlActivator.PLUGIN_ID)).log(
						new Status(Severity.REPORT.getValue(), JenkinsControlActivator.PLUGIN_ID, "Build on Jenkins"));
			}
		};
		actionGoToJobPage = new Action("Go to the job page", JenkinsControlActivator.getImageDescriptor("icons/page_go.png")) {
			@Override
			public void run() {
				Platform.getLog(Platform.getBundle(JenkinsControlActivator.PLUGIN_ID)).log(
						new Status(Severity.REPORT.getValue(), JenkinsControlActivator.PLUGIN_ID, "Go to the job page"));
			}
		};
		actionGoToLatestBuildPage = new Action("Go to the latest build page", JenkinsControlActivator.getImageDescriptor("icons/page_gear.png")) {
			@Override
			public void run() {
				Platform.getLog(Platform.getBundle(JenkinsControlActivator.PLUGIN_ID)).log(
						new Status(Severity.REPORT.getValue(), JenkinsControlActivator.PLUGIN_ID, "Go to the latest build page"));
			}
		};
		
		final IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		toolBarManager.add(actionRefresh);
		toolBarManager.add(new Separator());
		toolBarManager.add(actionBuildOnJenkins);
		toolBarManager.add(new Separator());
		toolBarManager.add(actionGoToJobPage);
		toolBarManager.add(actionGoToLatestBuildPage);
	}

	@Override
	public void setFocus() {
	}

}
