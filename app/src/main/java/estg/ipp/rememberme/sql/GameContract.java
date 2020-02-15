package estg.ipp.rememberme.sql;

import android.provider.BaseColumns;

//esta classe armazena diferentes constantes para as operações do sql
//é como um contrato
public final class GameContract {

    private GameContract(){

    }

    public static class QuestionsTable implements BaseColumns {
        public static  final String TABLE_NAME = "game_questions";
        public static  final String COLUMN_QUESTION = "question";
        public static  final String COLUMN_OPTION1 = "option1";
        public static  final String COLUMN_OPTION2 = "option2";
        public static  final String COLUMN_OPTION3 = "option3";
        public static  final String COLUMN_ANSWER_NR = "answer_nr";
        public static  final String COLUMN_DIFICULTY = "dificulty";

    }

}
