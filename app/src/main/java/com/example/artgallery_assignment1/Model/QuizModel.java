package com.example.artgallery_assignment1.Model;

import java.util.ArrayList;

public class QuizModel {
    private String artPieceId;
    private Quiz quiz;

    public String getArtPieceId() {
        return artPieceId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public static class Quiz {
        private String question;
        private ArrayList<String> answers;
        private String correctAnswer;

        public String getQuestion() {
            return question;
        }

        public ArrayList<String> getAnswers() {
            return answers;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
