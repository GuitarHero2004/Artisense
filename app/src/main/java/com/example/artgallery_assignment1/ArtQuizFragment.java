package com.example.artgallery_assignment1;

import android.os.Bundle;
import android.util.Log;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ArtQuizFragment extends Fragment {

    private TextView questionText;
    private RadioGroup answersGroup;
    private Button submitBtn, nextBtn;
    private List<QuizModel> quizzes;
    private int currentQuizIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_art_quiz, container, false);

        questionText = view.findViewById(R.id.questionText);
        answersGroup = view.findViewById(R.id.answersGroup);
        submitBtn = view.findViewById(R.id.submitBtn);
        nextBtn = view.findViewById(R.id.nextBtn);

        // Load quiz data from JSON
        String jsonString = loadQuizData();

        if (jsonString == null || jsonString.isEmpty()) {
            Toast.makeText(getActivity(), "Error loading quiz data", Toast.LENGTH_SHORT).show();
            return view;
        }

        // Parse the JSON data into a list of quizzes
        Gson gson = new Gson();
        quizzes = gson.fromJson(jsonString, new TypeToken<ArrayList<QuizModel>>() {}.getType());

        if (quizzes == null || quizzes.isEmpty()) {
            Toast.makeText(getActivity(), "No quiz data available", Toast.LENGTH_SHORT).show();
            return view;
        }

        // Display the first quiz
        displayQuiz(quizzes.get(currentQuizIndex));

        submitBtn.setOnClickListener(v -> checkAnswer());
        nextBtn.setOnClickListener(v -> loadNextQuiz());

        return view;
    }

    private void displayQuiz(QuizModel quiz) {
        questionText.setText(quiz.getQuiz().getQuestion());

        // Clear previous answers if any
        answersGroup.removeAllViews();

        // Add new radio buttons for each answer
        for (String answer : quiz.getQuiz().getAnswers()) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(answer);
            answersGroup.addView(radioButton);
        }
    }

    private void checkAnswer() {
        int selectedRadioButtonId = answersGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId == -1) {
            Toast.makeText(getActivity(), "Please select an answer", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton selectedRadioButton = requireView().findViewById(selectedRadioButtonId);
            String selectedAnswer = selectedRadioButton.getText().toString();
            QuizModel currentQuiz = quizzes.get(currentQuizIndex);

            Toast.makeText(getActivity(), "Wrong! The correct answer is: " + currentQuiz.getQuiz().getCorrectAnswer(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNextQuiz() {
        if (currentQuizIndex < quizzes.size() - 1) {
            currentQuizIndex++;
            displayQuiz(quizzes.get(currentQuizIndex));
        } else {
            Toast.makeText(getActivity(), "You've completed the quiz!", Toast.LENGTH_SHORT).show();
        }
    }

    private String loadQuizData() {
        String json = null;
        try {
            InputStream inputStream = requireActivity().getAssets().open("quiz.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8); // Convert byte array to String
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("ArtQuizFragment", "Loaded JSON: " + json);  // Add this log
        return json;
    }

}
