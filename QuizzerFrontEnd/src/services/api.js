const BACKEND_URL = "http://localhost:8080";


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

// Fetch quiz by ID (no questions included?)   // "/quizzes/{id}"
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

// Fetch all questions for a specific quiz    //"/quizzes/{quizId}/questions"
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

// Fetch answer options
export function getAnswerOptions(quizId) {
	return fetch(`${BACKEND_URL}/api/quizzes/${quizId}/answers`)
		.then((response) => {
			if (!response.ok)
				throw new Error("Something went wrong: " + response.statusText);
			return response.json();
		})
		.catch((err) => console.error("Error fetching answers:", err));
}

//Handling user answer
export const submitAnswer = async (quizId, questionId, answerId) => {
	try {
		const answerDTO = {
			answerId: answerId,
			questionId: questionId  
		};

		const response = await fetch(
			`${BACKEND_URL}/api/quizzes/${quizId}/questions/${questionId}/answer`,
			{
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(answerDTO),  
			}
		);

		if (!response.ok) {
			throw new Error(`Something went wrong: ${response.statusText}`);
		}

		const responseData = await response.json();
		return responseData;

	} catch (error) {
		console.error("Error submitting answer:", error);
		return "Error submitting answer.";
	}
};
