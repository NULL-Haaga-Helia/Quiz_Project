import { useState, useEffect } from "react";
import { getAllQuizzes, getQuizById, getQuizQuestions } from "../services/api";
import { useNavigate } from "react-router-dom";
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
	const [quiz, setQuiz] = useState(null);
	const location = useLocation();
	const { quizId } = location.state;
	const [questions, setQuestions] = useState([]);
	const [totalAnswers, setTotalAnswers] = useState();
	const [ correctAnswers, setCorrectAnswers ] = useState();
	const [ wrongAnswers, setWrongAnswers ] = useState();

	useEffect(() => {
		if (quizId) {
			fetchQuiz();
			fetchQuizQuestions();
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
			.then((responseData) => setQuestions(responseData))
			.catch((err) => console.error("Failed to fetch questions:", err));
	};

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
						Results of "{quiz.name}"
					</Typography>

					<Typography
						variant="body1"
						color="textSecondary"
						gutterBottom
						sx={{ textAlign: "left", marginBottom: 2 }}
					>
						answers to questions
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
										Correct answer %
									</TableCell>
									<TableCell sx={{ fontWeight: "bold" }} align="left">
										Correct answers
									</TableCell>
									<TableCell sx={{ fontWeight: "bold" }} align="left">
										Wrong answers
									</TableCell>
								</TableRow>
							</TableHead>
							<TableBody>
								{questions.map((question) => (
									<TableRow
										key={question.id}
										sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
									>
										<TableCell align="left">{question.questionText}</TableCell>
										<TableCell align="left">{question.difficulty}</TableCell>
										<TableCell align="left">total answers</TableCell>
										<TableCell align="left">correct %</TableCell>
										<TableCell align="left">correct</TableCell>
										<TableCell align="left">wrong</TableCell>
									</TableRow>
								))}
							</TableBody>
						</Table>
					</TableContainer>
				</>
			)}
		</Box>
	);
}

export default QuizResults;
