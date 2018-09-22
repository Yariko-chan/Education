package com.gmail.f.d.ganeeva.easyinvest.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.gmail.f.d.ganeeva.easyinvest.model.analysis.Analysis;
import com.gmail.f.d.ganeeva.easyinvest.model.analysis.AnalysisDao;
import com.gmail.f.d.ganeeva.easyinvest.model.companies.CompaniesDao;
import com.gmail.f.d.ganeeva.easyinvest.model.companies.Company;
import com.gmail.f.d.ganeeva.easyinvest.model.groups.Group;
import com.gmail.f.d.ganeeva.easyinvest.model.groups.GroupDao;
import com.gmail.f.d.ganeeva.easyinvest.model.projects.Project;
import com.gmail.f.d.ganeeva.easyinvest.model.projects.ProjectDao;

@Database(entities = {
        Project.class,
        Analysis.class,
        Company.class,
        Group.class},
        version = 1)
public abstract class InvestmentsRoomDatabase extends RoomDatabase {
    private static final String INVESTMENTS_DATABASE = "investments_db";

    private static InvestmentsRoomDatabase instance;

    public abstract GroupDao groupDao();
    public abstract CompaniesDao companiesDao();
    public abstract ProjectDao projectDao();
    public abstract AnalysisDao analysisDao();

    public static InvestmentsRoomDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (InvestmentsRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            InvestmentsRoomDatabase.class, INVESTMENTS_DATABASE)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsync(instance).execute();
                }
            };



    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ProjectDao projectDao;
        private final CompaniesDao companiesDao;
        private final GroupDao groupDao;
        private final AnalysisDao analysisDao;

        PopulateDbAsync(InvestmentsRoomDatabase db) {
            projectDao = db.projectDao();
            companiesDao = db.companiesDao();
            groupDao = db.groupDao();
            analysisDao = db.analysisDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            long defaultId = groupDao.insert(new Group("Default"));
            groupDao.insert(new Group("Restaurants"));
            groupDao.insert(new Group("Cars"));
            groupDao.insert(new Group("IT"));
            groupDao.insert(new Group("Services"));
            groupDao.insert(new Group("Stores"));

            Company c = new Company("No company", "-", "-", defaultId);
            companiesDao.insert(c);
            return null;
        }
    }
}
