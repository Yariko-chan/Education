package com.example.diana.easyinvest.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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
//                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(instance).execute();
                }
            };



    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ProjectDao mDao;

        PopulateDbAsync(InvestmentsRoomDatabase db) {
            mDao = db.projectDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            mDao.deleteAll(); // why crashes without this?
//            Project project = new Project("Hello", "", 0f, 1);
//            mDao.insert(project);
//            project = new Project("World", "", 0f, 1);
//            mDao.insert(project);
            return null;
        }
    }
}
