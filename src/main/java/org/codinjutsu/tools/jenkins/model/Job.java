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

package org.codinjutsu.tools.jenkins.model;

import org.apache.commons.lang.StringUtils;
import org.codinjutsu.tools.jenkins.util.GuiUtil;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Job {

    private static final Map<String, Icon> ICON_BY_JOB_HEALTH_MAP = new HashMap<String, Icon>();

    private final String name;
    private final String url;

    private String color;
    private boolean inQueue;

    private Health health;

    private Build lastBuild;

    private final List<JobParameter> parameters = new LinkedList<JobParameter>();

    static {
        ICON_BY_JOB_HEALTH_MAP.put("health-00to19", GuiUtil.loadIcon("health-00to19.png"));
        ICON_BY_JOB_HEALTH_MAP.put("health-20to39", GuiUtil.loadIcon("health-20to39.png"));
        ICON_BY_JOB_HEALTH_MAP.put("health-40to59", GuiUtil.loadIcon("health-40to59.png"));
        ICON_BY_JOB_HEALTH_MAP.put("health-60to79", GuiUtil.loadIcon("health-60to79.png"));
        ICON_BY_JOB_HEALTH_MAP.put("health-80plus", GuiUtil.loadIcon("health-80plus.png"));
        ICON_BY_JOB_HEALTH_MAP.put("null", GuiUtil.loadIcon("null.png"));
    }


    private Job(String name, String color, String url, Boolean inQueue) {
        this.name = name;
        this.color = color;
        this.url = url;
        this.inQueue = inQueue;
    }


    public static Job createJob(String jobName, String jobColor, String jobUrl, String inQueue) {
        return new Job(jobName, jobColor, jobUrl, Boolean.valueOf(inQueue));
    }

    public Icon getStateIcon() {
        return Build.getStateIcon(color);
    }

    public Icon getHealthIcon() {
        if (health == null) {
            return ICON_BY_JOB_HEALTH_MAP.get("null");
        }
        return ICON_BY_JOB_HEALTH_MAP.get(health.getLevel());
    }

    public String findHealthDescription() {
        if (health == null) {
            return "";
        }
        return health.getDescription();
    }


    public boolean updateContentWith(Job updatedJob) {
        boolean statusChanged = false;
        if (!StringUtils.equals(color, updatedJob.getColor())) {
            this.color = updatedJob.getColor();
            statusChanged = true;
        }

        this.health = updatedJob.getHealth();
        this.inQueue = updatedJob.isInQueue();

        if (lastBuild != null) { //TODO Crappy!! Need to refactor and Unit tests
            if (updatedJob.getLastBuild() != null) {
                if (updatedJob.getLastBuild().getNumber() != lastBuild.getNumber()) {
                    statusChanged = true;
                } else {
                    if (!updatedJob.isInQueue() || !updatedJob.getLastBuild().isBuilding()) {
                        statusChanged = true;
                    }
                }
            }
        } else {
            if (updatedJob.getLastBuild() != null && (!updatedJob.getLastBuild().isBuilding() || !updatedJob.isInQueue())) {
                statusChanged = true;
            }
        }
        this.lastBuild = updatedJob.getLastBuild();

        return statusChanged;
    }


    public void addParameter(String paramName, String paramType, String defaultValue, String... choices) {
        parameters.add(JobParameter.create(paramName, paramType, defaultValue, choices));
    }

    public String getName() {
        return name;
    }


    String getColor() {
        return color;
    }


    public String getUrl() {
        return url;
    }


    public boolean isInQueue() {
        return inQueue;
    }


    public Build getLastBuild() {
        return lastBuild;
    }


    public void setLastBuild(Build lastBuild) {
        this.lastBuild = lastBuild;
    }

    Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public boolean hasParameters() {
        return !parameters.isEmpty();
    }


    public List<JobParameter> getParameters() {
        return parameters;
    }


    public static class Health {

        private final String healthLevel;
        private final String description;

        private Health(String healthLevel, String description) {
            this.healthLevel = healthLevel;
            this.description = description;
        }

        public String getLevel() {
            return healthLevel;
        }

        public String getDescription() {
            return description;
        }

        public static Health createHealth(String healthLevel, String healthDescription) {
            return new Health(healthLevel, healthDescription);
        }
    }
}
