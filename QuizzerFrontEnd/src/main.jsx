import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";

import { createBrowserRouter, RouterProvider } from "react-router-dom";

import QuizList from "./Components/Quizlist.jsx";
import CategoryList from "./Components/CategoryList.jsx";
import QuizQuestions from "./Components/QuizQuestions.jsx";
import QuizResults from "./Components/QuizResults.jsx";
import Error from "./Components/Error.jsx";
import QuizCategoryList from "./Components/QuizCategoryList.jsx";
import QuizReviewList from "./Components/QuizReviewList.jsx"
import SubmitReview from "./Components/submitreview.jsx";

const router = createBrowserRouter([
	{
		path: "/",
		element: <App />,
		errorElement: <Error />,
		children: [
			{
				element: <QuizList />,
				index: true,
			},
			{
				path: "categorylist",
				element: <CategoryList />,
			},
			{
				path: "quizcategorylist", 
				element: <QuizCategoryList />,
			},
			{ path: "quizquestions", element: <QuizQuestions /> },
			{ path: "quizresults", element: <QuizResults /> },
			{ path: "quizreviewlist", element: <QuizReviewList />},
			{ path: "submitreview", element: <SubmitReview />}
		],
	},
]);

ReactDOM.createRoot(document.getElementById("root")).render(
	<React.StrictMode>
		<RouterProvider router={router} />
	</React.StrictMode>
);
