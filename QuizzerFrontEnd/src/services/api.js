//Fetch all (published) quizzes
export function getAllQuizzes() {
	return fetch(`${import.meta.env.VITE_BACKEND_URL}/api/quizzes`)
		.then((response) => {
			if (!response.ok)
				throw new Error("Something went wrong: " + response.statusText);
			return response.json();
		})
		.catch((err) => console.error("Error fetching all quizzes:", err));
}

// Fetch quiz by ID
export function getQuizById(quizId) {
	return fetch(`${import.meta.env.VITE_BACKEND_URL}/api/quizzes/${quizId}`)
		.then((response) => {
			if (!response.ok)
				throw new Error("Something went wrong: " + response.statusText);
			return response.json();
		})
		.catch((err) => console.error("Error fetching quiz by ID:", err));
}

// Fetch all questions for a specific quiz
export function getQuizQuestions(quizId) {
	return fetch(
		`${import.meta.env.VITE_BACKEND_URL}/api/quizzes/${quizId}/questions`
	)
		.then((response) => {
			if (!response.ok)
				throw new Error("Something went wrong: " + response.statusText);
			return response.json();
		})
		.catch((err) => console.error("Error fetching quiz questions:", err));
}

// Fetch answer options
export function getAnswerOptions(quizId) {
	return fetch(
		`${import.meta.env.VITE_BACKEND_URL}/api/quizzes/${quizId}/answers`
	)
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
			//questionId: questionId,
		};

		const response = await fetch(
			`${
				import.meta.env.VITE_BACKEND_URL
			}/api/quizzes/${quizId}/questions/${questionId}/answers/${answerId}/userAnswers`,
			{
				method: "POST",
				headers: {
					Accept: "application/json",
					"Content-Type": "application/json",
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

//Handling getting quizzes by category
export function getQuizzesByCategory(categoryId) {
	return fetch(
		`${import.meta.env.VITE_BACKEND_URL}/api/categories/${categoryId}/quizzes`
	)
		.then((response) => {
			if (!response.ok) {
				if (response.status === 404) {
					console.error(`Category ${categoryId} not found`);
					return { error: "Category not found" };
				} else {
					console.error(
						`Error fetching quizzes for category ${categoryId}: ${response.statusText}`
					);
					return { error: "Something went wrong: " + response.statusText };
				}
			}
			return response.json();
		})
		.catch((err) => {
			console.error(
				`Error fetching quizzes for category ${categoryId}: ${err.message}`
			);
			return { error: "An error occurred while fetching quizzes" };
		});
}

//handles getting all categories
export function getAllCategories() {
	return fetch(`${import.meta.env.VITE_BACKEND_URL}/api/categories`)
		.then((response) => {
			if (!response.ok) {
				throw new Error("Something went wrong: " + response.statusText);
			}
			return response.json();
		})
		.catch((err) => console.error("Error fetching all categories:", err));
}

//handles getting all reviews
export function getAllQuizReviews(quizId) {
	return fetch(`${import.meta.env.VITE_BACKEND_URL}/api/quizzes/${quizId}/reviews`)
	  .then((response) => {
		if (!response.ok) {
		  throw new Error("Something went wrong: " + response.statusText);
		}
		return response.json();  
	  })
	  .catch((err) => console.error("Error fetching reviews:", err));
  }

  //handles deleting reviews
  export function deleteReview(quizId, reviewId) {
	return fetch(`${import.meta.env.VITE_BACKEND_URL}/api/${quizId}/reviews/${reviewId}`, {
	  method: 'DELETE',
	  headers: {
		'Content-Type': 'application/json',
	  },
	})
	  .then((response) => {
		if (!response.ok) {
		  throw new Error(`Failed to delete review: ${response.status} ${response.statusText}`);
		}
		return response.ok;
	  })
	  .catch((err) => {
		console.error("Error deleting review:", err);
		throw err; 
	  });
  }
  
  

    //handles editing reviews
	export function editReview(quizId, reviewId, updatedReviewData) {
		console.log('quizId:', quizId, 'reviewId:', reviewId, 'updatedReviewData:', updatedReviewData);

		return fetch(`${import.meta.env.VITE_BACKEND_URL}/api/${quizId}/reviews/${reviewId}/edit`, {
		  method: 'PUT', 
		  headers: {
			'Content-Type': 'application/json',
		  },
		  body: JSON.stringify(updatedReviewData),
		})
		  .then((response) => {
			if (!response.ok) {
			  throw new Error("Something went wrong: " + response.statusText);
			}
			return response.json();  
		  })
		  .catch((err) => console.error("Error editing review:", err));
	  }
	  