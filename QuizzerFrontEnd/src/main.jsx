//import { StrictMode } from "react";
//import { createRoot } from "react-dom/client";
import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
//import "./index.css";

import { createBrowserRouter, RouterProvider } from "react-router-dom";

import QuizList from "./components/QuizList";
import CategoryList from "./components/CategoryList";
import QuizQuestions from "./components/QuizQuestions";
import QuizResults from "./components/QuizResults";
import Error from "./components/Error";

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
			{ path: "quizquestions", element: <QuizQuestions /> },
			{ path: "quizresults", element: <QuizResults /> },
		],
	},
]);

ReactDOM.createRoot(document.getElementById("root")).render(
	<React.StrictMode>
		<RouterProvider router={router} />
	</React.StrictMode>
);
