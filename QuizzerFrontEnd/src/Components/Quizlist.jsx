import { useState, useEffect } from "react";
import { getAllQuizzes } from "../services/api";
//import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { Box } from "@mui/material";

function QuizList() {
	//States:
	const [quizList, setQuizList] = useState([]);
	const navigate = useNavigate();

	//Functions:
	useEffect(() => {
		fetchQuizzes();
	}, []);

	const fetchQuizzes = () => {
		getAllQuizzes()
			.then((responseData) => {
				console.log("Fetched quizzes:", responseData);
				setQuizList(responseData);
			})
			.catch((err) => console.error("Failed to fetch quizzes:", err));
		//Probably redundant, optionally show an error msg in the UI.
	};

	const handleQuizNameClick = (quiz) => {
		console.log("Navigating with quiz:", quiz);
		navigate("/quizquestions", { state: { quiz } }); // Passing quiz object as a prop to the other component
	};

	const handleQuizResultsClick = (quiz) => {
		navigate("/quizresults", { state: { quiz } }); // Passing quiz object as a prop to the other component
	};

	//Rendering:
	return (
		<Box sx={{ width: "100%", marginTop: 8 }}>
			<Typography
				variant="h4"
				gutterBottom
				sx={{ textAlign: "left", marginBottom: 2 }}
			>
				Quizzes
			</Typography>

			<TableContainer component={Paper}>
				<Table sx={{ minWidth: 650 }} aria-label="simple table">
					<TableHead>
						<TableRow>
							<TableCell sx={{ fontWeight: "bold" }} align="left">
								Name
							</TableCell>
							<TableCell sx={{ fontWeight: "bold" }} align="left">
								Description
							</TableCell>
							<TableCell sx={{ fontWeight: "bold" }} align="left">
								Category
							</TableCell>
							<TableCell sx={{ fontWeight: "bold" }} align="left">
								Added on
							</TableCell>
							<TableCell sx={{ fontWeight: "bold" }} align="right"></TableCell>
						</TableRow>
					</TableHead>
					<TableBody>
						{quizList.map((quiz) => (
							<TableRow
								key={quiz.id}
								sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
							>
								<TableCell
									component="th"
									scope="row"
									style={{ cursor: "pointer", color: "#1976d2" }}
									onClick={() => handleQuizNameClick(quiz)}
								>
									{quiz.name}
								</TableCell>
								<TableCell align="left">{quiz.description}</TableCell>
								<TableCell align="left">{quiz.quizCategory.name}</TableCell>
								<TableCell align="left">{quiz.addedOn}</TableCell>
								<TableCell
									component="th"
									scope="row"
									style={{ cursor: "pointer", color: "#1976d2" }}
									onClick={() => handleQuizResultsClick(quiz)}
									align="left"
								>
									See results
								</TableCell>
							</TableRow>
						))}
					</TableBody>
				</Table>
			</TableContainer>
		</Box>
	);
}

export default QuizList;
