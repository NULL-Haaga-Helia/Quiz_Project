import { useState, useEffect } from "react";
//import reactLogo from "./assets/react.svg";
//import viteLogo from "/vite.svg";
import "./App.css";

function App() {
	//Link to JSON data
	//http://localhost:8080/api/quizzes

	//States:
	const [quizList, setQuizList] = useState([]);

	//Functions:
	useEffect(() => {
		fetchQuizzes();
	}, []);

	const fetchQuizzes = () => {
		fetch("http://localhost:8080/api/quizzes", {
			headers: {
				Authorization:
					"Basic " + btoa("user:52232272-04a5-4ed4-9990-761d4f045507"),
				//To get the data  ->
				//"username:password" where x is your specific password in backend terminal, username is user
			},
		})
			.then((response) => {
				if (!response.ok)
					throw new Error("Something went wrong: " + response.statusText);
				return response.json();
			})
			.then((responseData) => {
				console.log("Fetched quizzes:", responseData);
				setQuizList(responseData);
				console.log("Updated state:", quizList);
			})
			.catch((err) => console.error("Fetch error:", err));
	};

	//Rendering:
	return (
		<>
			<h3>Quizlist</h3>
			<table>
				<tbody>
					<tr>
						<th>Name</th>
						<th>Description</th>
						<th>Added on</th>
						<th>Published</th>
					</tr>
					{quizList.map((quiz) => (
						<tr key={quiz.id}>
							<td>{quiz.name}</td>
							<td>{quiz.description}</td>
							<td>{quiz.addedOn}</td>
							<td>{quiz.isPublished ? "Yes" : "No"}</td>
						</tr>
					))}
				</tbody>
			</table>
		</>
	);
}

export default App;
