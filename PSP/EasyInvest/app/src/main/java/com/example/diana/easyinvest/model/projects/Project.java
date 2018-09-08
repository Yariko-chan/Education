package com.example.diana.easyinvest.model.projects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.diana.easyinvest.model.companies.Company;

import org.json.JSONArray;
import org.json.JSONException;

@Entity(tableName = "projects",
        indices = {@Index("company_id")},
        foreignKeys = {
        @ForeignKey(
                entity = Company.class,
                parentColumns = "id",
                childColumns = "company_id")
        }
)
public class Project {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    public String name;

    public String description;

    public float r;

    public int duration;

    public String flows; // float array, serialized to json array

    @Ignore
    double[] rawMoneyFlows;

    @ColumnInfo(name = "company_id")
    public long companyId;

    Project() {}

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

    public double[] getFlows() {
        if (rawMoneyFlows == null && !TextUtils.isEmpty(flows)) {
            try {
                JSONArray array = new JSONArray(flows);
                rawMoneyFlows = new double[array.length()];
                for (int i = 0; i < array.length(); i++) {
                    rawMoneyFlows[i] = (float) array.getDouble(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                rawMoneyFlows = new double[duration];
            }
        } else if (rawMoneyFlows == null) {
            rawMoneyFlows = new double[duration];
        }
        return rawMoneyFlows;
    }

    public long getCompanyId() {
        return companyId;
    }

    public static class Builder {

        @NonNull private String name;
        private String description;
        private float r;
        private int duration;
        @NonNull private double[] rawMoneyFlows;
        long companyId = -1;

        public Builder() {
        }

        public Builder setName(@NonNull String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setR(@NonNull float r) {
            this.r = r;
            return this;
        }

        public Builder setMoneyFlows(double[] flows) {
            this.rawMoneyFlows = flows;
            duration = flows.length - 1; // additional for 0 year
            return this;
        }

        public Builder setCompanyId(@NonNull long companyId) {
            this.companyId = companyId;
            return this;
        }

        public Project build() {
            if (TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException("Name should not be null or empty");
            } else if (r <= 0) {
                throw new IllegalArgumentException("R should be > 0");
            } else if (duration <= 0) {
                throw new IllegalArgumentException("Duration must be at least 1 year");
            } else if (rawMoneyFlows.length < 1) {
                throw new IllegalArgumentException("Wrong money flows");
//            } else if (companyId < 1) {
//                throw new IllegalArgumentException("Wrong company id");
            }

            String flows = "";
            try {
                JSONArray array = new JSONArray();
                for (double f : rawMoneyFlows) {
                    array.put(f);
                }
                flows = array.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Project p = new Project();
            p.name = name;
            p.description = description;
            p.r = r;
            p.duration = duration;
            p.flows = flows;
            p.companyId = companyId;
            p.rawMoneyFlows = rawMoneyFlows;
            return p;
        }
    }
}
