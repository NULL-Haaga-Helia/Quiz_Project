const BACKEND_URL = "http://localhost:8080";

//JSON data at:  http://localhost:8080/api/<insert endpoint>

//Fetch all (published) quizzes
export function getAllQuizzes() {
	return fetch(`${BACKEND_URL}/api/quizzes`)
		.then((response) => {
			if (!response.ok)
				throw new Error("Something went wrong: " + response.statusText);
			return response.json();
		})
		.catch((err) => console.error("Error fetching all quizzes:", err));
}

//Note, following fetches still need to be tested.

// Fetch quiz by ID (no questions included?)
// Endpoint: /quizzes/{quizId} ?
export function getQuizById(quizId) {
	return fetch(`${BACKEND_URL}/api/quizzes/${quizId}`)
		.then((response) => {
			if (!response.ok)
				throw new Error("Something went wrong: " + response.statusText);
			return response.json();
		})
		.catch((err) => console.error("Error fetching quiz by ID:", err));
}

// Fetch all questions for a specific quiz
// Endpoint: /quizzes/{quizId}/questions  ?
export function getQuizQuestions(quizId) {
	return fetch(`${BACKEND_URL}/api/quizzes/${quizId}/questions`)
		.then((response) => {
			if (!response.ok)
				throw new Error("Something went wrong: " + response.statusText);
			return response.json();
		})
		.catch((err) => console.error("Error fetching quiz questions:", err));
}
