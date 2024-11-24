import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { getQuizQuestions } from "../services/api";

import Typography from "@mui/material/Typography";
import { Box, List, ListItem, ListItemText, Paper } from "@mui/material";

function QuizQuestions() {
	//States:
	const [questions, setQuestions] = useState([]);
	const location = useLocation();
	// Access passed state / quiz object
	const { quiz } = location.state;
	//const { state } = useLocation();
	//const quiz = state?.quiz;

	//Functions:
	useEffect(() => {
		if (quiz) {
			fetchQuizQuestions();
		}
	}, []);

	const fetchQuizQuestions = () => {
		getQuizQuestions(quiz.id)
			.then((responseData) => {
				console.log("Fetched questions:", responseData);
				setQuestions(responseData);
			})
			.catch((err) => console.error("Failed to fetch questions:", err));
		//Probably redundant, optionally show an error msg in the UI.
	};

	// Rendering:
	if (!quiz) {
		console.warn("Quiz data unavailable");
		return (
			<Box sx={{ width: "100%", marginTop: 8 }}>
				<Typography variant="h5">Quiz data unavailable</Typography>
			</Box>
		);
	} else {
		return (
			<Box sx={{ width: "100%", marginTop: 8, padding: 2 }}>
				{/* Quiz Name */}
				<Typography variant="h4" gutterBottom>
					{quiz.name}
				</Typography>

				{/* Quiz Description */}
				<Typography variant="body1" color="textSecondary" gutterBottom>
					{quiz.description}
				</Typography>

				{/* Additional Information */}
				<Typography variant="body2" color="textSecondary" gutterBottom>
					Added on: {quiz.addedOn} | Questions: {questions.length} | Category:{" "}
					{quiz.quizCategory.name}
				</Typography>

				{/* Questions List */}
				<List sx={{ marginTop: 2 }}>
					{questions.map((question, index) => (
						<ListItem
							key={question.questionId}
							component={Paper}
							elevation={1}
							sx={{ marginBottom: 2, padding: 2 }}
						>
							<ListItemText
								primary={
									<Typography variant="h6">{question.questionText}</Typography>
								}
								secondary={
									<>
										<Typography variant="body2" color="textSecondary">
											Question {index + 1} of {questions.length}
										</Typography>
										<Typography variant="body2" color="textSecondary">
											Difficulty: {question.difficulty}
										</Typography>
									</>
								}
							/>
						</ListItem>
					))}
				</List>
			</Box>
		);
	}
}

export default QuizQuestions;
