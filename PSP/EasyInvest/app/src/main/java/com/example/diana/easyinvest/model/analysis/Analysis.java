package com.example.diana.easyinvest.model.analysis;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.example.diana.easyinvest.model.projects.Project;
import com.example.diana.easyinvest.utils.Calculations;

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
}

