package com.example.diana.easyinvest.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;

@Entity(tableName = "projects", indices = {@Index("id")})
public class Project {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    String name;

    String description;

    float r;

    int duration;

    String flows; // float array, serialized to json array

    @Ignore
    float[] rawMoneyFlows;

    private Project() {
    }

    public Project(@NonNull String name, String description, float r, int duration) {
        this.name = name;
        this.description = description;
        this.r = r;
        this.duration = duration;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getR() {
        return r;
    }

    public int getDuration() {
        return duration;
    }

    public float[] getFlows() {
        if (rawMoneyFlows == null && !TextUtils.isEmpty(flows)) {
            // deserialize
        }
        if (rawMoneyFlows == null) {
            rawMoneyFlows = new float[duration];
        }
        return rawMoneyFlows;
    }

    public static class Builder {

        @NonNull private String name;
        private String description;
        private float r;
        private int duration;
        @NonNull private float[] rawMoneyFlows;

        public Builder() {
        }

        public void setName(@NonNull String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setR(@NonNull float r) {
            this.r = r;
        }

        public void setMoneyFlows(float[] flows) {
            this.rawMoneyFlows = flows;
            duration = flows.length - 1; // additional for 0 year
        }

        public Project build() {
            if (TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException("Name should not be null or empty");
            } else if (r <= 0) {
                throw new IllegalArgumentException("R should be > 0");
            } else if (duration <= 0) {
                throw new IllegalArgumentException("Duration must be at least 1 year");
            }
            Project p = new Project();
            p.name = name;
            p.description = description;
            p.r = r;
            p.duration = duration;

            p.rawMoneyFlows = rawMoneyFlows;
            p.flows = "";
            try {
                JSONArray array = new JSONArray();
                for (float f : rawMoneyFlows) {
                    array.put(f);
                }
                p.flows = array.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // todo calculate formulas

            return null;
        }
    }
}
