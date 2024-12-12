import { useState, useEffect } from "react";
import { getQuizById, getQuizQuestions, getUserResults } from "../services/api";

import Typography from "@mui/material/Typography";
import { Box } from "@mui/material";
import { useLocation } from "react-router-dom";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

function QuizResults() {
	//States:
	const [quiz, setQuiz] = useState(null);
	const location = useLocation();
	const { quizId } = location.state;
	const [questions, setQuestions] = useState([]);
	const [results, setResults] = useState([]);

	//Functions:
	useEffect(() => {
		if (quizId) {
			fetchQuiz();
			fetchQuizQuestions();
			fetchUserAnswers();
		}
	}, [quizId]);

	const fetchQuiz = () => {
		getQuizById(quizId)
			.then((responseData) => setQuiz(responseData))
			.catch((err) => {
				console.error("Failed to fetch quiz:", err);
				setQuiz(null);
			});
	};

	const fetchQuizQuestions = () => {
		getQuizQuestions(quizId)
			.then((responseData) => {
				setQuestions(responseData);
			})
			.catch((err) => console.error("Failed to fetch questions:", err));
	};

	const fetchUserAnswers = async () => {
		try {
			const responseData = await getUserResults(quizId);
			setResults(responseData);
			console.log("results:", responseData);
		} catch (err) {
			console.error("Failed to fetch user answers:", err);
		}
	};

	const calculateAnswerStats = (questionId) => {
		// Get question
		const question = questions.find((q) => q.questionId === questionId);

		if (!question) {
			console.log("Question not found:", questionId);
			return {
				totalAnswers: 0,
				correctAnswers: 0,
				wrongAnswers: 0,
				correctPercentage: 0,
			};
		}

		// Get possible answers for the question
		const possibleAnswers = question.answers;

		// Filter to select user submitted answers that match current question's possible answers
		const relevantAnswers = results.filter((result) => {
			return possibleAnswers.some(
				(answer) => answer.answerId === result.answer.answerId
			);
		});

		const totalAnswers = relevantAnswers.length;
		const correctAnswers = relevantAnswers.filter(
			(result) => result.answer.isCorrect
		).length;
		const wrongAnswers = totalAnswers - correctAnswers;
		const correctPercentage =
			totalAnswers > 0 ? ((correctAnswers / totalAnswers) * 100).toFixed(2) : 0;

		return { totalAnswers, correctAnswers, wrongAnswers, correctPercentage };
	};

	const totalAnswersAcrossAllQuestions = results.length;

	// Rendering:
	return (
		<Box sx={{ width: "100%", marginTop: 8 }}>
			{!quiz ? (
				<Typography
					variant="h5"
					color="textSecondary"
					sx={{ textAlign: "center" }}
				>
					Error: Unable to fetch quiz results.
				</Typography>
			) : (
				<>
					<Typography
						variant="h4"
						gutterBottom
						sx={{ textAlign: "left", marginBottom: 2 }}
					>
						Results of {quiz.name}
					</Typography>

					<Typography
						variant="body1"
						color="textSecondary"
						gutterBottom
						sx={{ textAlign: "left", marginBottom: 2 }}
					>
						{totalAnswersAcrossAllQuestions} answers to {questions.length}{" "}
						questions
					</Typography>
					<TableContainer component={Paper}>
						<Table sx={{ minWidth: 650 }} aria-label="simple table">
							<TableHead>
								<TableRow>
									<TableCell sx={{ fontWeight: "bold" }} align="left">
										Question
									</TableCell>
									<TableCell sx={{ fontWeight: "bold" }} align="left">
										Difficulty
									</TableCell>
									<TableCell sx={{ fontWeight: "bold" }} align="left">
										Total Answers
									</TableCell>
									<TableCell sx={{ fontWeight: "bold" }} align="left">
										Correct Answer %
									</TableCell>
									<TableCell sx={{ fontWeight: "bold" }} align="left">
										Correct Answers
									</TableCell>
									<TableCell sx={{ fontWeight: "bold" }} align="left">
										Wrong Answers
									</TableCell>
								</TableRow>
							</TableHead>
							<TableBody>
								{questions.map((question) => {
									const stats = calculateAnswerStats(question.questionId);
									return (
										<TableRow
											key={question.questionId}
											sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
										>
											<TableCell align="left">
												{question.questionText}
											</TableCell>
											<TableCell align="left">{question.difficulty}</TableCell>
											<TableCell align="left">{stats.totalAnswers}</TableCell>
											<TableCell align="left">
												{stats.correctPercentage}%
											</TableCell>
											<TableCell align="left">{stats.correctAnswers}</TableCell>
											<TableCell align="left">{stats.wrongAnswers}</TableCell>
										</TableRow>
									);
								})}
							</TableBody>
						</Table>
					</TableContainer>
				</>
			)}
		</Box>
	);
}

export default QuizResults;
