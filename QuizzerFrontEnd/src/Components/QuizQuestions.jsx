import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { getQuizById, getQuizQuestions } from "../services/api";

import Typography from "@mui/material/Typography";
import { Box, List, ListItem, ListItemText, Paper } from "@mui/material";

function QuizQuestions() {
	//States:
	const [quiz, setQuiz] = useState(null);
	const [questions, setQuestions] = useState([]);
	const location = useLocation();
	const { quizId } = location.state;
	//const quizId = location.state?.quizId;

	// Tests with whole obj state.
	//const { quiz } = location.state;
	//const { state } = useLocation();
	//const quiz = state?.quiz;

	//Functions:
	useEffect(() => {
		if (quizId) {
			fetchQuiz();
			fetchQuizQuestions();
		}
	}, []); //or  [quizId] to run when quizId changes?

	const fetchQuiz = () => {
		getQuizById(quizId)
			.then((responseData) => {
				console.log("Fetched quiz:", responseData);
				setQuiz(responseData);
			})
			.catch((err) => console.error("Failed to fetch quiz:", err));
		//Probably redundant, optionally show an error msg in the UI.
	};

	const fetchQuizQuestions = () => {
		getQuizQuestions(quizId)
			.then((responseData) => {
				console.log("Fetched questions:", responseData);
				setQuestions(responseData);
			})
			.catch((err) => console.error("Failed to fetch questions:", err));
		//Probably redundant, optionally show an error msg in the UI.
	};

	// Rendering:
	if (!quizId) {
		console.warn("Quiz ID is unavailable");
		return (
			<Box sx={{ width: "100%", marginTop: 8 }}>
				<Typography variant="h5">Quiz ID is unavailable</Typography>
			</Box>
		);
	}
	if (!quiz) {
		return (
			<Box sx={{ width: "100%", marginTop: 8 }}>
				<Typography variant="h5">Loading quiz ...</Typography>
			</Box>
		);
	} else {
		return (
			<Box sx={{ width: "100%", marginTop: 8, padding: 2 }}>
				{/* Quiz Name */}
				<Typography
					variant="h4"
					gutterBottom
					sx={{ textAlign: "left", marginBottom: 2 }}
				>
					{quiz.name}
				</Typography>

				{/* Quiz Description */}
				<Typography
					variant="body1"
					color="textSecondary"
					gutterBottom
					sx={{ textAlign: "left", marginBottom: 2 }}
				>
					{quiz.description}
				</Typography>

				{/* Additional Details */}
				<Typography
					variant="body2"
					color="textSecondary"
					gutterBottom
					sx={{ textAlign: "left", marginBottom: 2 }}
				>
					Added on: {quiz.addedOn} | Questions: {questions.length} | Category:{" "}
					{quiz.quizCategory.name}
				</Typography>

				{/* Questions List */}
				{questions.length === 0 ? (
					<Typography
						variant="body1"
						color="textSecondary"
						gutterBottom
						sx={{ textAlign: "center", marginTop: 2 }}
					>
						No questions available for this quiz.
					</Typography>
				) : (
					<List sx={{ marginTop: 2 }}>
						{questions.map((question, index) => (
							<ListItem
								key={question.questionId}
								component={Paper}
								elevation={3}
								sx={{ marginBottom: 2, padding: 2 }}
							>
								<ListItemText
									primary={
										<Typography variant="h6">
											{question.questionText}
										</Typography>
									}
									secondary={
										<Typography variant="body2" color="textSecondary">
											Question {index + 1} of {questions.length} | Difficulty:{" "}
											{question.difficulty}
										</Typography>
									}
								/>
							</ListItem>
						))}
					</List>
				)}
			</Box>
		);
	}
}

export default QuizQuestions;
