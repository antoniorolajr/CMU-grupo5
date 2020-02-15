package estg.ipp.rememberme.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import estg.ipp.rememberme.models.Question;
import estg.ipp.rememberme.sql.GameContract.*;

import java.util.ArrayList;
import java.util.List;

public class GameDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AritmeticGame.db";
    private static  final int DATABASE_VERSION =1;

    private SQLiteDatabase db;

    public GameDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, "+
                QuestionsTable.COLUMN_DIFICULTY + " TEXT" + ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable(){
        //teste
        Question q1 = new Question("Fácil: 2 + 2 = ",
                "A) 4", "B) 6", "C) -4", 1, Question.DIFICULTY_EASY);
        addQuestion(q1);
        Question q2 = new Question("Fácil: 5 + 0 = ",
                "A) 0", "B) 5", "C) -5", 2, Question.DIFICULTY_EASY);
        addQuestion(q2);
        Question q3 = new Question("Fácil: 7 - 4 = ",
                "A) 4", "B) 2", "C) 3", 3, Question.DIFICULTY_EASY);
        addQuestion(q3);
        Question q4 = new Question("Fácil: 7 + 7 = ",
                "A) 13", "B) 14", "C) 12", 1, Question.DIFICULTY_EASY);
        addQuestion(q4);
        Question q5 = new Question("Fácil: 7-6 = ",
                "A) 1", "B) 1", "C) 0", 2, Question.DIFICULTY_EASY);
        addQuestion(q5);
        Question q6 = new Question("Média: 2 - (- 5) = ",
                "A) 6", "B) 7", "C) -7", 2, Question.DIFICULTY_MEDIUM);
        addQuestion(q6);
        Question q7 = new Question("Média: -2 + 4 -2 = ",
                "A) 4", "B) 0", "C) -2", 2, Question.DIFICULTY_MEDIUM);
        addQuestion(q7);
        Question q8 = new Question("Média: 5 * 4 = ",
                "A) 25", "B) 20", "C) 15", 2, Question.DIFICULTY_MEDIUM);
        addQuestion(q8);
        Question q9 = new Question("Média: 10 / 2 = ",
                "A) 5", "B) 2", "C) 10", 1, Question.DIFICULTY_MEDIUM);
        addQuestion(q9);
        Question q10 = new Question("Média: 55 + 45 = ",
                "A) 105", "B) 95", "C) 100", 3, Question.DIFICULTY_MEDIUM);
        addQuestion(q10);
        Question q11 = new Question("Difícil: (-7) x (-3) = ",
                "A) -21", "B) 21", "C) -31", 2, Question.DIFICULTY_HARD);
        addQuestion(q11);
        Question q12 = new Question("Difícil: ((-7) x (-2)) / 2 = ",
                "A) -7", "B) -7.5", "C) 7.5", 3, Question.DIFICULTY_HARD);
        addQuestion(q12);
        Question q13 = new Question("Difícil: 10 / 3 = ",
                "A) 3.333", "B) 3.666", "C) 3.999", 1, Question.DIFICULTY_HARD);
        addQuestion(q13);
        Question q14 = new Question("Difícil: 100 x 2 + 5 = ",
                "A) 205", "B) 105", "C) 55", 1, Question.DIFICULTY_HARD);
        addQuestion(q14);
        Question q15 = new Question("Difícil: 77 + 40 x 2  = ",
                "A) 147", "B) 157", "C) 177", 2, Question.DIFICULTY_HARD);
        addQuestion(q15);
    }

    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFICULTY, question.getDificulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Question> getAllQuestions(){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ QuestionsTable.TABLE_NAME, null);

        if(c.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDificulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFICULTY)));
                questionList.add(question);
            }while (c.moveToNext());
        }

        c.close();
        return questionList;

    }

    public ArrayList<Question> getQuestions(String dificulty){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{dificulty};
        Cursor c = db.rawQuery("SELECT * FROM "+ QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFICULTY + " = ?", selectionArgs);

        if(c.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDificulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFICULTY)));
                questionList.add(question);
            }while (c.moveToNext());
        }

        c.close();
        return questionList;

    }


}
