import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import {
	getAnswerOptions,
	getQuizById,
	getQuizQuestions,
	submitAnswer,
} from "../services/api";
import Typography from "@mui/material/Typography";
import {
	Box,
	List,
	ListItem,
	ListItemText,
	Paper,
	Radio,
	RadioGroup,
	FormControlLabel,
	Button,
	Snackbar,
	CircularProgress,
} from "@mui/material";

function QuizQuestions() {
	// States:
	const [quiz, setQuiz] = useState(null);
	const [questions, setQuestions] = useState([]);
	const location = useLocation();
	const { quizId } = location.state;
	const [answers, setAnswers] = useState([]);
	const [selectedAnswers, setSelectedAnswers] = useState({});
	const [submittedQuestions, setSubmittedQuestions] = useState({});
	const [snackbar, setSnackbar] = useState({ open: false, message: "" });

	// Functions:
	useEffect(() => {
		if (quizId) {
			fetchQuiz();
			fetchQuizQuestions();
			fetchQuestionAnswers();
		}
	}, [quizId]);

	const fetchQuiz = () => {
		getQuizById(quizId)
			.then((responseData) => setQuiz(responseData))
			.catch((err) => console.error("Failed to fetch quiz:", err));
	};

	const fetchQuizQuestions = () => {
		getQuizQuestions(quizId)
			.then((responseData) => {
				console.log("Quiz Questions:", responseData);
				setQuestions(responseData);
			})
			.catch((err) => console.error("Failed to fetch questions:", err));
	};

	const fetchQuestionAnswers = () => {
		getAnswerOptions(quizId)
			.then((responseData) => setAnswers(responseData))
			.catch((err) => console.error("Failed to fetch answers:", err));
	};

	//Submitting Answers
	const handleSubmit = async (questionId) => {
		const selectedAnswerId = selectedAnswers[questionId];
		if (!selectedAnswerId) {
			alert("Please select an answer before submitting.");
			return;
		}

		try {

			const question = questions.find((q) => q.questionId === questionId);

			if (!question) {
				console.error(`Question with ID ${questionId} not found.`);
				setSnackbar({
					open: true,
					message: "Question not found.",
				});
				return;
			}


			const correctAnswer = question.answers.find((answer) => answer.isCorrect);

			if (!correctAnswer) {
				console.error(`Correct answer not found for question ID ${questionId}.`);
				setSnackbar({
					open: true,
					message: "Correct answer data missing.",
				});
				return;
			}


			const isCorrect = selectedAnswerId === correctAnswer.answerId;


			const response = await submitAnswer(quizId, questionId, selectedAnswerId);


			setSnackbar({
				open: true,
				message: isCorrect ? response.message || "Correct" : "Incorrect",
			});


			setSubmittedQuestions((prev) => ({
				...prev,
				[questionId]: true,
			}));
		} catch (error) {
			console.error("Error submitting answer:", error);
			setSnackbar({
				open: true,
				message: "Error submitting answer.",
			});
		}
	};


	const handleAnswerChange = (questionId, answerId) => {
		setSelectedAnswers((prev) => ({
			...prev,
			[questionId]: answerId,
		}));
	};

	const handleCloseSnackbar = () => setSnackbar({ open: false, message: "" });

	//Rendering:
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
			<Box
				sx={{
					width: "100%",
					marginTop: 8,
					display: "flex",
					justifyContent: "center",
					alignItems: "center",
				}}
			>
				<CircularProgress />
			</Box>
		);
	}

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
									<Typography variant="h6">{question.questionText}</Typography>
								}
								secondary={
									<>
										<Typography variant="body2" color="textSecondary">
											Question {index + 1} of {questions.length} | Difficulty:{" "}
											{question.difficulty}
										</Typography>

										<RadioGroup
											value={selectedAnswers[question.questionId] || ""}
											onChange={(e) =>
												handleAnswerChange(question.questionId, parseInt(e.target.value))
											}
										>
											{question.answers.map((answer) => (
												<FormControlLabel
													key={answer.answerId}
													value={answer.answerId}
													control={<Radio />}
													label={answer.text}
												/>
											))}
										</RadioGroup>


										<Button
											onClick={() => handleSubmit(question.questionId)}
											disabled={submittedQuestions[question.questionId]}
											style={{
												paddingTop: "10px",
												border: "none",
												background: submittedQuestions[question.questionId] ? "gray" : "white",
												color: submittedQuestions[question.questionId] ? "darkgray" : "blue",
												fontSize: "13px",
												cursor: submittedQuestions[question.questionId] ? "not-allowed" : "pointer",
											}}
										>
											SUBMIT YOUR ANSWER
										</Button>

										<Snackbar
											open={snackbar.open}
											autoHideDuration={3000}
											onClose={handleCloseSnackbar}
											message={snackbar.message}
										/>
									</>
								}
							/>
						</ListItem>
					))}
				</List>
			)}
		</Box>
	);
}

export default QuizQuestions;