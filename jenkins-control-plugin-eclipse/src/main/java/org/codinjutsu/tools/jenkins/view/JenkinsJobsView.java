package org.codinjutsu.tools.jenkins.view;

import javax.print.attribute.standard.Severity;

import org.codinjutsu.tools.jenkins.JenkinsControlActivator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class JenkinsJobsView extends ViewPart {

	private Action actionRefresh;
	private Action actionBuildOnJenkins;
	private Action actionGoToJobPage;
	private Action actionGoToLatestBuildPage;
	private Action actionFilterAll;
	private Action actionFilterAllFailed;
	private Action actionFilterAllUnstable;
	private TableViewer viewer;
	
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
		actionFilterAll = new Action("All") {};
		actionFilterAllFailed = new Action("All failed") {};
		actionFilterAllUnstable = new Action("All unstable") {};
		final IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		toolBarManager.add(actionRefresh);
		toolBarManager.add(new Separator());
		toolBarManager.add(actionBuildOnJenkins);
		toolBarManager.add(new Separator());
		toolBarManager.add(actionGoToJobPage);
		toolBarManager.add(actionGoToLatestBuildPage);
		
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(actionFilterAll);
				manager.add(actionFilterAllFailed);
				manager.add(actionFilterAllUnstable);
			}
		});
		viewer = new TableViewer(parent);
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		getSite().registerContextMenu(menuMgr, viewer);
		viewer.getControl().setMenu(menu);
		getViewSite().getActionBars().getMenuManager().add(new Action("MenuManager") {
			
		});
		
	}

	@Override
	public void setFocus() {
	}

}
