package com.gmail.f.d.ganeeva.easyinvest.model.analysis;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.gmail.f.d.ganeeva.easyinvest.model.projects.Project;
import com.gmail.f.d.ganeeva.easyinvest.utils.Calculations;

@Entity(tableName = "analysis",
        indices = {@Index("project_id")},
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "id",
                childColumns = "project_id"))
public class Analysis {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "project_id")
    long projectId;

    int pp;
    int dpp;
    double arr;
    double npv;
    double irr;
    double mirr;
    double pi;

    Analysis() {}

    @Ignore
    public Analysis(Project p) {
        this.pp = Calculations.getPP(p.getFlows());
        dpp = Calculations.getDPP(p.getFlows(), p.getR());
        arr = Calculations.getARR(p.getFlows());
        npv = Calculations.getNPV(p.getFlows(), p.getR());
        irr = Calculations.getIRR(p.getFlows(), Calculations.DEFAULT_IRR_PRECISION);
        mirr = Calculations.getMIRR(p.getFlows(), p.getR());
        pi = Calculations.getPI(p.getFlows(), p.getR());
        projectId = p.id;
    }

    @Ignore
    public int getId() {
        return id;
    }

    @Ignore
    public long getProjectId() {
        return projectId;
    }

    @Ignore
    public int getPp() {
        return pp;
    }

    @Ignore
    public int getDpp() {
        return dpp;
    }

    @Ignore
    public double getArr() {
        return arr;
    }

    @Ignore
    public double getNpv() {
        return npv;
    }

    @Ignore
    public double getIrr() {
        return irr;
    }

    @Ignore
    public double getMirr() {
        return mirr;
    }

    @Ignore
    public double getPi() {
        return pi;
    }
}

