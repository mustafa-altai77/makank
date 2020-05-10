package com.example.makank.ui.Test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.makank.ui.Test.QuizContract.MovieEntry.KEY_ANSWER;
import static com.example.makank.ui.Test.QuizContract.MovieEntry.KEY_ID;
import static com.example.makank.ui.Test.QuizContract.MovieEntry.KEY_OPTA;
import static com.example.makank.ui.Test.QuizContract.MovieEntry.KEY_OPTB;
import static com.example.makank.ui.Test.QuizContract.MovieEntry.KEY_OPTC;
import static com.example.makank.ui.Test.QuizContract.MovieEntry.KEY_OPTD;
import static com.example.makank.ui.Test.QuizContract.MovieEntry.KEY_QUES;
import static com.example.makank.ui.Test.QuizContract.MovieEntry.TABLE_QUEST;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "triviaQuiz";
    // tasks table name

    private SQLiteDatabase dbase;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
                +KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT,"+KEY_OPTD+" TEXT)";
        db.execSQL(sql);
        addQuestions();
        //db.close();
    }
    private void addQuestions()
    {
        Question q1=new Question("هل تعاملت مع شخص مصاب بفيروس كورونا في اخر 25 يوم ؟","نعم", "لا", "","", "نعم");
        this.addQuestion(q1);
        Question q2=new Question("هل يوجد لديك ارتفاع في درجة الحرارة ؟", "نعم", "لا", "قليلاً", "أحياناً","نعم");
        this.addQuestion(q2);
        Question q3=new Question("هل تعاني من سعال ؟","نعم", "لا","قليلاً", "","نعم" );
        this.addQuestion(q3);
        Question q4=new Question("هل تعاني من العطس ؟", "نعم", "لا", "قليلاً","","");
        this.addQuestion(q4);
        Question q5=new Question("هل تعاني صعوبة في التنفس ؟","نعم","لا","قليلاً","","نعم");
        this.addQuestion(q5);
        Question q6=new Question("هل تعاني من سيلان الانف ؟","نعم","لا","","","نعم");
        this.addQuestion(q6);
        Question q7=new Question("هل تشعر بإجهاد عام ؟","نعم","لا","قليلاً","","نعم");
        this.addQuestion(q7);
        Question q8=new Question("هل تعاني من أوجاع وآلام ؟","نعم","لا","قليلاً","أحياناً","نعم");
        this.addQuestion(q8);
        Question q9=new Question("هل يوجد لديك صداع ؟","نعم","لا","قليلاً","أحياناً","نعم");
        this.addQuestion(q9);
        Question q10=new Question("هل تعاني من إلتهاب الحلق ؟","نعم","لا","","","نعم");
        this.addQuestion(q10);
        Question q11=new Question("هل يوجد لديك إسهال ؟","نعم","لا","","","نعم");
        this.addQuestion(q11);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }
    // Adding new question
    public void addQuestion(Question quest) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());
        values.put(KEY_OPTD, quest.getOPTD());
        // Inserting Row
        dbase.insert(TABLE_QUEST, null, values);
    }
    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));
                quest.setOPTD(cursor.getString(6));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }
    public int rowcount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }
}
