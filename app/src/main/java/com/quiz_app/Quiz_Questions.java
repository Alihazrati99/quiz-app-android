package com.quiz_app;

public class Quiz_Questions {
    private int questionID;
    private boolean answer;
    int color;

    public Quiz_Questions(int questionID, boolean answer, int color) {
        this.questionID = questionID;
        this.answer = answer;
        this.color = color;
    }

    public Quiz_Questions(int questionID, boolean answer) {
        this.questionID = questionID;
        this.answer = answer;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
