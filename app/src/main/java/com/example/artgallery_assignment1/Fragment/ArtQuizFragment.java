package com.example.artgallery_assignment1.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.artgallery_assignment1.Model.QuizModel;
import com.example.artgallery_assignment1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ArtQuizFragment extends Fragment {

    private TextView questionNumberText, questionText, scoreText;
    private RadioGroup answersGroup;
    private ArrayList<QuizModel> quizzes;
    private int currentQuizIndex = 0;
    private int score = 0;

    private Button nextBtn;
    private Button restartBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_art_quiz, container, false);

        questionNumberText = view.findViewById(R.id.questionNumberText);
        questionText = view.findViewById(R.id.questionText);
        answersGroup = view.findViewById(R.id.answersGroup);
        scoreText = view.findViewById(R.id.scoreText);
        nextBtn = view.findViewById(R.id.nextBtn);
        restartBtn = view.findViewById(R.id.restartBtn);

        String jsonString = loadQuizData();
        if (jsonString == null || jsonString.isEmpty()) {
            Toast.makeText(getActivity(), "Error loading quiz data", Toast.LENGTH_SHORT).show();
            return view;
        }

        Gson gson = new Gson();
        quizzes = gson.fromJson(jsonString, new TypeToken<ArrayList<QuizModel>>() {}.getType());
        if (quizzes == null || quizzes.isEmpty()) {
            Toast.makeText(getActivity(), "No quiz data available", Toast.LENGTH_SHORT).show();
            return view;
        }

        displayQuiz(quizzes.get(currentQuizIndex));
        updateButtons();

        nextBtn.setOnClickListener(v -> checkAnswerAndProceed());
        restartBtn.setOnClickListener(v -> restartQuiz());

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void displayQuiz(QuizModel quiz) {
        questionNumberText.setText("Question " + (currentQuizIndex + 1) + "/" + quizzes.size());
        questionText.setText(quiz.getQuiz().getQuestion());
        answersGroup.removeAllViews();

        for (String answer : quiz.getQuiz().getAnswers()) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(answer);
            answersGroup.addView(radioButton);
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkAnswerAndProceed() {
        int selectedRadioButtonId = answersGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == -1) {
            Toast.makeText(getActivity(), "Please select an answer before proceeding", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = requireView().findViewById(selectedRadioButtonId);
        String selectedAnswer = selectedRadioButton.getText().toString();
        QuizModel currentQuiz = quizzes.get(currentQuizIndex);

        if (selectedAnswer.equals(currentQuiz.getQuiz().getCorrectAnswer())) {
            score++;
        }

        if (currentQuizIndex == quizzes.size() - 1) {
            // Final question; display score and show only the restart button
            showFinalScore();
        } else {
            loadNextQuiz();
            updateButtons(); // Update button text after loading the next quiz
        }
    }

    @SuppressLint("SetTextI18n")
    private void showFinalScore() {
        scoreText.setText("Score: " + score + "/" + quizzes.size());
        scoreText.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.GONE); // Hide the next/submit button
        restartBtn.setVisibility(View.VISIBLE); // Show the restart button
    }

    private void loadNextQuiz() {
        if (currentQuizIndex < quizzes.size() - 1) {
            currentQuizIndex++;
            displayQuiz(quizzes.get(currentQuizIndex));
            answersGroup.clearCheck(); // Clear the selected answer for the new question
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateButtons() {
        if (currentQuizIndex == quizzes.size() - 1) {
            nextBtn.setText("Submit");
        } else {
            nextBtn.setText("Next");
        }
        nextBtn.setVisibility(View.VISIBLE);
        restartBtn.setVisibility(View.GONE); // Ensure only one button is visible at a time
    }

    private void restartQuiz() {
        currentQuizIndex = 0;
        score = 0;
        scoreText.setVisibility(View.GONE);
        restartBtn.setVisibility(View.GONE);
        nextBtn.setVisibility(View.VISIBLE);
        displayQuiz(quizzes.get(currentQuizIndex));
        updateButtons();
    }

    private String loadQuizData() {
        String json = null;
        try {
            InputStream inputStream = requireActivity().getAssets().open("quiz.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
